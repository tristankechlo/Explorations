package com.tristankechlo.explorations.init;

import com.tristankechlo.explorations.Explorations;

import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.data.worldgen.PlainVillagePools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredStructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;

public class ConfiguredStructures {

	public static ConfiguredStructureFeature<?, ?> CONFIGURED_FORGOTTEN_WELL = ModStructures.FORGOTTEN_WELL.get()
			.configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));
	public static ConfiguredStructureFeature<?, ?> CONFIGURED_JUNGLE_TEMPLE = ModStructures.JUNGLE_TEMPLE.get()
			.configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));
	public static ConfiguredStructureFeature<?, ?> CONFIGURED_UNDERGROUND_TEMPLE = ModStructures.UNDERGROUND_TEMPLE
			.get().configured(new JigsawConfiguration(() -> PlainVillagePools.START, 0));

	public static void registerConfiguredStructures() {
		Registry<ConfiguredStructureFeature<?, ?>> registry = BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE;
		Registry.register(registry, new ResourceLocation(Explorations.MOD_ID, "configured_forgotten_well"),
				CONFIGURED_FORGOTTEN_WELL);
		Registry.register(registry, new ResourceLocation(Explorations.MOD_ID, "configured_jungle_temple"),
				CONFIGURED_JUNGLE_TEMPLE);
		Registry.register(registry, new ResourceLocation(Explorations.MOD_ID, "configured_underground_temple"),
				CONFIGURED_UNDERGROUND_TEMPLE);

	}
}