package com.tristankechlo.explorations.eventhandler;

import com.tristankechlo.explorations.init.ConfiguredFeatures;
import com.tristankechlo.explorations.init.ConfiguredStructures;
import com.tristankechlo.explorations.worldgen.structures.config.JigsawConfig;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashSet;
import java.util.Set;

public class BiomeLoadingHandler {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void biomeModification(final BiomeLoadingEvent event) {
        ResourceLocation identifier = event.getName();
        if (identifier == null) {
            return;
        }

        addScarecrows(identifier.toString(), event);

        // register generation biomes for desert ruins
        if (JigsawConfig.DESERT_RUIN.spawnBiomes.contains(event.getName().toString())) {
            event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_DESERT_RUIN);
        }

        // add forgotten well biomes
        if (JigsawConfig.FORGOTTEN_WELL.spawnBiomes.contains(event.getName().toString())) {
            event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_FORGOTTEN_WELL);
        }

        // add jungle temple biomes
        if (JigsawConfig.JUNGLE_TEMPLE.spawnBiomes.contains(event.getName().toString())) {
            event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_JUNGLE_TEMPLE);
        }

        // add floating island biomes
        if (JigsawConfig.FLOATING_ISLAND.spawnBiomes.contains(event.getName().toString())) {
            event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_FLOATING_ISLAND);
        }

        // add large oak tree biomes
        if (JigsawConfig.LARGE_OAK_TREE.spawnBiomes.contains(event.getName().toString())) {
            event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_LARGE_OAK_TREE);
        }

        // add logs biomes
        if (JigsawConfig.LOGS.spawnBiomes.contains(event.getName().toString())) {
            event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_LOGS);
        }

        // add shrine biomes
        if (JigsawConfig.SHRINE.spawnBiomes.contains(event.getName().toString())) {
            event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_SHRINE);
        }

        // add undergrounde temple to all overworld biomes
        if (event.getCategory() != Category.NETHER && event.getCategory() != Category.THEEND) {
            event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_UNDERGROUND_TEMPLE);
            event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_SLIME_CAVE);
        }
    }

    private static final Set<String> SCARECROW_ACACIA_BIOMES = make(Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.SHATTERED_SAVANNA, Biomes.SHATTERED_SAVANNA_PLATEAU);
    private static final Set<String> SCARECROW_BIRCH_BIOMES = make(Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.TALL_BIRCH_FOREST, Biomes.TALL_BIRCH_HILLS);
    private static final Set<String> SCARECROW_DARK_OAK_BIOMES = make(Biomes.DARK_FOREST, Biomes.DARK_FOREST_HILLS);
    private static final Set<String> SCARECROW_JUNGLE_BIOMES = make(Biomes.JUNGLE, Biomes.JUNGLE_EDGE, Biomes.JUNGLE_HILLS, Biomes.BAMBOO_JUNGLE, Biomes.BAMBOO_JUNGLE_HILLS, Biomes.MODIFIED_JUNGLE, Biomes.MODIFIED_JUNGLE_EDGE);
    private static final Set<String> SCARECROW_OAK_BIOMES = make(Biomes.FOREST, Biomes.FLOWER_FOREST, Biomes.BIRCH_FOREST, Biomes.DARK_FOREST);
    private static final Set<String> SCARECROW_SPRUCE_BIOMES = make(Biomes.TAIGA, Biomes.TAIGA_HILLS, Biomes.TAIGA_MOUNTAINS, Biomes.GIANT_TREE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA, Biomes.GIANT_TREE_TAIGA_HILLS, Biomes.GIANT_SPRUCE_TAIGA_HILLS);

    private static void addScarecrows(String location, BiomeLoadingEvent event) {
        if (SCARECROW_ACACIA_BIOMES.contains(location)) {
            event.getGeneration().addFeature(Decoration.VEGETAL_DECORATION, ConfiguredFeatures.CONFIGURED_SCARECROW_ACACIA);
        }
        if (SCARECROW_BIRCH_BIOMES.contains(location)) {
            event.getGeneration().addFeature(Decoration.VEGETAL_DECORATION, ConfiguredFeatures.CONFIGURED_SCARECROW_BIRCH);
        }
        if (SCARECROW_DARK_OAK_BIOMES.contains(location)) {
            event.getGeneration().addFeature(Decoration.VEGETAL_DECORATION, ConfiguredFeatures.CONFIGURED_SCARECROW_DARK_OAK);
        }
        if (SCARECROW_JUNGLE_BIOMES.contains(location)) {
            event.getGeneration().addFeature(Decoration.VEGETAL_DECORATION, ConfiguredFeatures.CONFIGURED_SCARECROW_JUNGLE);
        }
        if (SCARECROW_OAK_BIOMES.contains(location)) {
            event.getGeneration().addFeature(Decoration.VEGETAL_DECORATION, ConfiguredFeatures.CONFIGURED_SCARECROW_OAK);
        }
        if (SCARECROW_SPRUCE_BIOMES.contains(location)) {
            event.getGeneration().addFeature(Decoration.VEGETAL_DECORATION, ConfiguredFeatures.CONFIGURED_SCARECROW_SPRUCE);
        }
    }

    @SafeVarargs
    private static Set<String> make(RegistryKey<Biome>... types) {
        HashSet<String> set = new HashSet<>();
        for (RegistryKey<Biome> biome : types) {
            set.add(biome.location().toString());
        }
        return set;
    }

}
