package com.tristankechlo.explorations.init;

import com.tristankechlo.explorations.Explorations;
import com.tristankechlo.explorations.worldgen.features.config.ScarecrowFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Features;

public class ConfiguredFeatures {

    /* CONFIGURED FEATURES */
    public static ConfiguredFeature<?, ?> CONFIGURED_SCARECROW_ACACIA;
    public static ConfiguredFeature<?, ?> CONFIGURED_SCARECROW_BIRCH;
    public static ConfiguredFeature<?, ?> CONFIGURED_SCARECROW_DARK_OAK;
    public static ConfiguredFeature<?, ?> CONFIGURED_SCARECROW_JUNGLE;
    public static ConfiguredFeature<?, ?> CONFIGURED_SCARECROW_OAK;
    public static ConfiguredFeature<?, ?> CONFIGURED_SCARECROW_SPRUCE;

    public static void registerConfiguredFeatures() {
        CONFIGURED_SCARECROW_ACACIA = configuredScarecrow(Blocks.ACACIA_FENCE, 20);
        CONFIGURED_SCARECROW_BIRCH = configuredScarecrow(Blocks.BIRCH_FENCE, 20);
        CONFIGURED_SCARECROW_DARK_OAK = configuredScarecrow(Blocks.DARK_OAK_FENCE, 20);
        CONFIGURED_SCARECROW_JUNGLE = configuredScarecrow(Blocks.JUNGLE_FENCE, 20);
        CONFIGURED_SCARECROW_OAK = configuredScarecrow(Blocks.OAK_FENCE, 22);
        CONFIGURED_SCARECROW_SPRUCE = configuredScarecrow(Blocks.SPRUCE_FENCE, 20);

        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, loc("scarecrow_acacia"), CONFIGURED_SCARECROW_ACACIA);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, loc("scarecrow_birch"), CONFIGURED_SCARECROW_BIRCH);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, loc("scarecrow_dark_oak"), CONFIGURED_SCARECROW_DARK_OAK);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, loc("scarecrow_jungle"), CONFIGURED_SCARECROW_JUNGLE);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, loc("scarecrow_oak"), CONFIGURED_SCARECROW_OAK);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, loc("scarecrow_spruce"), CONFIGURED_SCARECROW_SPRUCE);
    }

    private static ConfiguredFeature<?, ?> configuredScarecrow(Block fence, int chance) {
        return ModRegistry.SCARECROW.get()
                .configured(ScarecrowFeatureConfig.simple(fence))
                .decorated(Features.Placements.HEIGHTMAP_SQUARE)
                .chance(chance);
    }

    private static ResourceLocation loc(String name) {
        return new ResourceLocation(Explorations.MOD_ID, name);
    }

}
