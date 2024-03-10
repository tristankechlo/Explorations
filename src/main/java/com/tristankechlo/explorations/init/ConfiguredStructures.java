package com.tristankechlo.explorations.init;

import com.tristankechlo.explorations.Explorations;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.FlatGenerationSettings;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class ConfiguredStructures {

    public static StructureFeature<?, ?> CONFIGURED_DESERT_RUIN = ModStructures.DESERT_RUIN.get().configured(IFeatureConfig.NONE);
    public static StructureFeature<?, ?> CONFIGURED_FORGOTTEN_WELL = ModStructures.FORGOTTEN_WELL.get().configured(IFeatureConfig.NONE);
    public static StructureFeature<?, ?> CONFIGURED_JUNGLE_TEMPLE = ModStructures.JUNGLE_TEMPLE.get().configured(IFeatureConfig.NONE);
    public static StructureFeature<?, ?> CONFIGURED_UNDERGROUND_TEMPLE = ModStructures.UNDERGROUND_TEMPLE.get().configured(IFeatureConfig.NONE);
    public static StructureFeature<?, ?> CONFIGURED_FLOATING_ISLAND = ModStructures.FLOATING_ISLAND.get().configured(IFeatureConfig.NONE);
    public static StructureFeature<?, ?> CONFIGURED_LARGE_OAK_TREE = ModStructures.LARGE_OAK_TREE.get().configured(IFeatureConfig.NONE);
    public static StructureFeature<?, ?> CONFIGURED_LOGS = ModStructures.LOGS.get().configured(IFeatureConfig.NONE);
    public static StructureFeature<?, ?> CONFIGURED_SHRINE = ModStructures.SHRINE.get().configured(IFeatureConfig.NONE);

    public static void registerConfiguredStructures() {
        // registers the configured structures, which are added to the biomes
        Registry<StructureFeature<?, ?>> registry = WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE;
        Registry.register(registry, new ResourceLocation(Explorations.MOD_ID, "configured_desert_ruin"), CONFIGURED_DESERT_RUIN);
        Registry.register(registry, new ResourceLocation(Explorations.MOD_ID, "configured_forgotten_well"), CONFIGURED_FORGOTTEN_WELL);
        Registry.register(registry, new ResourceLocation(Explorations.MOD_ID, "configured_jungle_temple"), CONFIGURED_JUNGLE_TEMPLE);
        Registry.register(registry, new ResourceLocation(Explorations.MOD_ID, "configured_underground_temple"), CONFIGURED_UNDERGROUND_TEMPLE);
        Registry.register(registry, new ResourceLocation(Explorations.MOD_ID, "configured_floating_island"), CONFIGURED_FLOATING_ISLAND);
        Registry.register(registry, new ResourceLocation(Explorations.MOD_ID, "configured_large_oak_tree"), CONFIGURED_LARGE_OAK_TREE);
        Registry.register(registry, new ResourceLocation(Explorations.MOD_ID, "configured_logs"), CONFIGURED_LOGS);
        Registry.register(registry, new ResourceLocation(Explorations.MOD_ID, "configured_shrine"), CONFIGURED_SHRINE);

        // prevent crashes, if mod's use a custom ChunkGenerator
        FlatGenerationSettings.STRUCTURE_FEATURES.put(ModStructures.DESERT_RUIN.get(), CONFIGURED_DESERT_RUIN);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(ModStructures.FORGOTTEN_WELL.get(), CONFIGURED_FORGOTTEN_WELL);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(ModStructures.JUNGLE_TEMPLE.get(), CONFIGURED_JUNGLE_TEMPLE);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(ModStructures.UNDERGROUND_TEMPLE.get(), CONFIGURED_UNDERGROUND_TEMPLE);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(ModStructures.FLOATING_ISLAND.get(), CONFIGURED_FLOATING_ISLAND);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(ModStructures.LARGE_OAK_TREE.get(), CONFIGURED_LARGE_OAK_TREE);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(ModStructures.LOGS.get(), CONFIGURED_LOGS);
        FlatGenerationSettings.STRUCTURE_FEATURES.put(ModStructures.SHRINE.get(), CONFIGURED_SHRINE);
    }
}
