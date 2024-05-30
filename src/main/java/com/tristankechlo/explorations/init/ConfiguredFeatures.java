package com.tristankechlo.explorations.init;

import com.google.common.collect.ImmutableList;
import com.tristankechlo.explorations.Explorations;
import com.tristankechlo.explorations.worldgen.features.config.ScarecrowFeatureConfig;
import com.tristankechlo.explorations.worldgen.treedecorators.LanternDecorator;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HugeMushroomBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.JungleFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.GiantTrunkPlacer;

public class ConfiguredFeatures {

    /* CONFIGURED FEATURES */
    public static ConfiguredFeature<?, ?> CONFIGURED_SCARECROW_ACACIA;
    public static ConfiguredFeature<?, ?> CONFIGURED_SCARECROW_BIRCH;
    public static ConfiguredFeature<?, ?> CONFIGURED_SCARECROW_DARK_OAK;
    public static ConfiguredFeature<?, ?> CONFIGURED_SCARECROW_JUNGLE;
    public static ConfiguredFeature<?, ?> CONFIGURED_SCARECROW_OAK;
    public static ConfiguredFeature<?, ?> CONFIGURED_SCARECROW_SPRUCE;
    public static ConfiguredFeature<?, ?> CONFIGURED_LARGE_MUSHROOM;

    public static void registerConfiguredFeatures() {
        CONFIGURED_SCARECROW_ACACIA = configuredScarecrow(Blocks.ACACIA_FENCE, 75);
        CONFIGURED_SCARECROW_BIRCH = configuredScarecrow(Blocks.BIRCH_FENCE, 75);
        CONFIGURED_SCARECROW_DARK_OAK = configuredScarecrow(Blocks.DARK_OAK_FENCE, 75);
        CONFIGURED_SCARECROW_JUNGLE = configuredScarecrow(Blocks.JUNGLE_FENCE, 75);
        CONFIGURED_SCARECROW_OAK = configuredScarecrow(Blocks.OAK_FENCE, 76);
        CONFIGURED_SCARECROW_SPRUCE = configuredScarecrow(Blocks.SPRUCE_FENCE, 75);
        CONFIGURED_LARGE_MUSHROOM = configuredLargeMushroom();

        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, loc("scarecrow_acacia"), CONFIGURED_SCARECROW_ACACIA);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, loc("scarecrow_birch"), CONFIGURED_SCARECROW_BIRCH);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, loc("scarecrow_dark_oak"), CONFIGURED_SCARECROW_DARK_OAK);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, loc("scarecrow_jungle"), CONFIGURED_SCARECROW_JUNGLE);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, loc("scarecrow_oak"), CONFIGURED_SCARECROW_OAK);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, loc("scarecrow_spruce"), CONFIGURED_SCARECROW_SPRUCE);
        Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, loc("large_mushroom"), CONFIGURED_LARGE_MUSHROOM);
    }

    private static ConfiguredFeature<?, ?> configuredScarecrow(Block fence, int chance) {
        return ModRegistry.SCARECROW.get()
                .configured(ScarecrowFeatureConfig.simple(fence))
                .decorated(Features.Placements.HEIGHTMAP_WORLD_SURFACE)
                .squared()
                .chance(chance);
    }

    private static ConfiguredFeature<?, ?> configuredLargeMushroom() {
        BlockState mushroomStem = Blocks.MUSHROOM_STEM.defaultBlockState().setValue(HugeMushroomBlock.UP, false).setValue(HugeMushroomBlock.DOWN, false);
        BlockState brownMushroom = Blocks.BROWN_MUSHROOM_BLOCK.defaultBlockState().setValue(HugeMushroomBlock.DOWN, false);
        return Feature.TREE
                .configured(
                        new BaseTreeFeatureConfig.Builder(
                                new SimpleBlockStateProvider(mushroomStem), // block state for trunks
                                new SimpleBlockStateProvider(brownMushroom), // block state for leaves
                                new JungleFoliagePlacer(FeatureSpread.fixed(1), FeatureSpread.fixed(0), 1), // leaves placer
                                new GiantTrunkPlacer(4, 2, 2), // trunk placer
                                new TwoLayerFeature(1, 0, 1) // foliage size
                        )
                                .decorators(ImmutableList.of(new LanternDecorator(0.9f)))
                                .ignoreVines().heightmap(Heightmap.Type.MOTION_BLOCKING)
                                .maxWaterDepth(Integer.MAX_VALUE).build())
                .decorated(Features.Placements.HEIGHTMAP_WORLD_SURFACE).count(5);
    }

    private static ResourceLocation loc(String name) {
        return new ResourceLocation(Explorations.MOD_ID, name);
    }

}
