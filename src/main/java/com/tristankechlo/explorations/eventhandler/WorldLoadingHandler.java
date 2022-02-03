package com.tristankechlo.explorations.eventhandler;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.tristankechlo.explorations.Explorations;
import com.tristankechlo.explorations.init.ConfiguredStructures;
import com.tristankechlo.explorations.init.ModStructures;
import com.tristankechlo.explorations.structures.ForgottenWellStructure;
import com.tristankechlo.explorations.structures.JungleTempleStructure;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.FlatLevelSource;
import net.minecraft.world.level.levelgen.StructureSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.StructureFeatureConfiguration;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

public class WorldLoadingHandler {

	private static Method GETCODEC_METHOD;

	@SubscribeEvent(priority = EventPriority.NORMAL)
	public void addDimensionalSpacing(final WorldEvent.Load event) {
		if (!(event.getWorld() instanceof ServerLevel)) {
			return;
		}
		ServerLevel serverWorld = (ServerLevel) event.getWorld();
		ChunkGenerator chunkGenerator = serverWorld.getChunkSource().getGenerator();
		StructureSettings worldStructureConfig = chunkGenerator.getSettings();

		// do not add when worldtype is SuperFlat
		if (serverWorld.getChunkSource().getGenerator() instanceof FlatLevelSource
				&& serverWorld.dimension().equals(Level.OVERWORLD)) {
			return;
		}

		// add structures to biomes
		HashMap<StructureFeature<?>, HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> STStructureToMultiMap = new HashMap<>();
		addToSomeBiomes(serverWorld, STStructureToMultiMap, ConfiguredStructures.CONFIGURED_FORGOTTEN_WELL,
				ForgottenWellStructure.getDefaultSpawnBiomes());
		addToSomeBiomes(serverWorld, STStructureToMultiMap, ConfiguredStructures.CONFIGURED_JUNGLE_TEMPLE,
				JungleTempleStructure.getDefaultSpawnBiomes());
		addToAllOverworldBiomes(serverWorld, STStructureToMultiMap, ConfiguredStructures.CONFIGURED_UNDERGROUND_TEMPLE);

		ImmutableMap.Builder<StructureFeature<?>, ImmutableMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> tempStructureToMultiMap = ImmutableMap
				.builder();
		worldStructureConfig.configuredStructures.entrySet().stream()
				.filter(entry -> !STStructureToMultiMap.containsKey(entry.getKey()))
				.forEach(tempStructureToMultiMap::put);
		STStructureToMultiMap
				.forEach((key, value) -> tempStructureToMultiMap.put(key, ImmutableMultimap.copyOf(value)));
		worldStructureConfig.configuredStructures = tempStructureToMultiMap.build();

		// do not add when worldtype is terraforged
		if (isTerraForged(serverWorld, chunkGenerator)) {
			return;
		}

		// add structures
		Map<StructureFeature<?>, StructureFeatureConfiguration> tempMap = new HashMap<>(
				worldStructureConfig.structureConfig());
		tempMap.putIfAbsent(ModStructures.FORGOTTEN_WELL.get(), getSettings(ModStructures.FORGOTTEN_WELL.get()));
		tempMap.putIfAbsent(ModStructures.JUNGLE_TEMPLE.get(), getSettings(ModStructures.JUNGLE_TEMPLE.get()));
		tempMap.putIfAbsent(ModStructures.UNDERGROUND_TEMPLE.get(),
				getSettings(ModStructures.UNDERGROUND_TEMPLE.get()));
		worldStructureConfig.structureConfig = tempMap;
	}

	private static void addToAllOverworldBiomes(ServerLevel serverWorld,
			HashMap<StructureFeature<?>, HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> STStructureToMultiMap,
			ConfiguredStructureFeature<?, ?> configuredFeature) {

		for (ResourceKey<Biome> biomeEntry : BiomeDictionary.getBiomes(BiomeDictionary.Type.OVERWORLD)) {
			BiomeCategory biomeCategory = serverWorld.registryAccess().ownedRegistryOrThrow(Registry.BIOME_REGISTRY)
					.get(biomeEntry).getBiomeCategory();
			if (biomeCategory != BiomeCategory.OCEAN && biomeCategory != BiomeCategory.NONE) {
				associateBiomeToConfiguredStructure(STStructureToMultiMap, configuredFeature, biomeEntry);
			}
		}
	}

	private static void addToSomeBiomes(ServerLevel serverWorld,
			HashMap<StructureFeature<?>, HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> STStructureToMultiMap,
			ConfiguredStructureFeature<?, ?> configuredFeature, ImmutableSet<ResourceKey<Biome>> biomes) {
		biomes.forEach(
				biomeKey -> associateBiomeToConfiguredStructure(STStructureToMultiMap, configuredFeature, biomeKey));
	}

	private boolean isTerraForged(ServerLevel serverWorld, ChunkGenerator chunkGenerator) {
		try {
			if (GETCODEC_METHOD == null) {
				GETCODEC_METHOD = ObfuscationReflectionHelper.findMethod(ChunkGenerator.class, "codec"); // func_230347_a_
			}
			@SuppressWarnings("unchecked")
			ResourceLocation cgRL = Registry.CHUNK_GENERATOR
					.getKey((Codec<? extends ChunkGenerator>) GETCODEC_METHOD.invoke(chunkGenerator));
			if (cgRL != null && cgRL.getNamespace().equals("terraforged")) {
				return true;
			}
		} catch (Exception e) {
			Explorations.LOGGER.error("Was unable to check if " + serverWorld.dimension().location()
					+ " is using Terraforged's ChunkGenerator.");
		}
		return false;
	}

	private static void associateBiomeToConfiguredStructure(
			Map<StructureFeature<?>, HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>>> STStructureToMultiMap,
			ConfiguredStructureFeature<?, ?> configuredStructureFeature, ResourceKey<Biome> biomeRegistryKey) {
		STStructureToMultiMap.putIfAbsent(configuredStructureFeature.feature, HashMultimap.create());
		HashMultimap<ConfiguredStructureFeature<?, ?>, ResourceKey<Biome>> configuredStructureToBiomeMultiMap = STStructureToMultiMap
				.get(configuredStructureFeature.feature);
		if (configuredStructureToBiomeMultiMap.containsValue(biomeRegistryKey)) {
			Explorations.LOGGER.error(
					"""
								Detected 2 ConfiguredStructureFeatures that share the same base StructureFeature trying to be added to same biome. One will be prevented from spawning.
							    This issue happens with vanilla too and is why a Snowy Village and Plains Village cannot spawn in the same biome because they both use the Village base structure.
							    The two conflicting ConfiguredStructures are: {}, {}
							    The biome that is attempting to be shared: {}
							""",
					BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(configuredStructureFeature),
					BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getId(configuredStructureToBiomeMultiMap.entries()
							.stream().filter(e -> e.getValue() == biomeRegistryKey).findFirst().get().getKey()),
					biomeRegistryKey);
		} else {
			configuredStructureToBiomeMultiMap.put(configuredStructureFeature, biomeRegistryKey);
		}
	}

	private StructureFeatureConfiguration getSettings(StructureFeature<?> structure) {
		return StructureSettings.DEFAULTS.get(structure);
	}

}
