package com.tristankechlo.explorations.init;

import com.tristankechlo.explorations.worlgen.features.config.ScarecrowFeatureConfig;
import com.tristankechlo.explorations.worlgen.treedecorators.LanternDecorator;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.MegaJungleFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModFeatures {

    public static ConfiguredFeature<?, ?> createLargeMushroomConfigured() {
        return new ConfiguredFeature<>(Feature.TREE,
                new TreeConfiguration.TreeConfigurationBuilder(
                        SimpleStateProvider.simple(stem()), //trunk provider
                        new GiantTrunkPlacer(4, 2, 2), //trunk placer
                        SimpleStateProvider.simple(cap()), //foliage provider
                        new MegaJungleFoliagePlacer(ConstantInt.of(1), ConstantInt.of(0), 1), //foliage placer
                        new TwoLayersFeatureSize(1, 0, 1) //feature size
                ).decorators(List.of(new LanternDecorator(0.9f, UniformInt.of(2, 3), UniformInt.of(1, 2)))).ignoreVines().build());
    }

    public static PlacedFeature createLargeMushroomPlaced(Holder<ConfiguredFeature<?, ?>> holder) {
        return new PlacedFeature(holder, List.of(
                CountPlacement.of(UniformInt.of(0, 2)),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
                BiomeFilter.biome(),
                BlockPredicateFilter.forPredicate(BlockPredicate.wouldSurvive(Blocks.DARK_OAK_SAPLING.defaultBlockState(), BlockPos.ZERO)),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(BlockTags.DIRT, new Vec3i(0, -1, 0)))
        ));
    }

    private static BlockState stem() {
        return Blocks.MUSHROOM_STEM.defaultBlockState().setValue(HugeMushroomBlock.UP, false).setValue(HugeMushroomBlock.DOWN, false);
    }

    private static BlockState cap() {
        return Blocks.BROWN_MUSHROOM_BLOCK.defaultBlockState().setValue(HugeMushroomBlock.DOWN, false);
    }

    public static ConfiguredFeature<?, ?> createScarecrowConfigured(Block fence) {
        return new ConfiguredFeature<>(ModRegistry.SCARECROW.get(), new ScarecrowFeatureConfig(
                SimpleStateProvider.simple(fence),
                SimpleStateProvider.simple(fence),
                SimpleStateProvider.simple(Blocks.HAY_BLOCK),
                new WeightedStateProvider(head()),
                false
        ));
    }

    private static SimpleWeightedRandomList<BlockState> head() {
        return new SimpleWeightedRandomList.Builder<BlockState>()
                .add(Blocks.CARVED_PUMPKIN.defaultBlockState(), 4)
                .add(Blocks.JACK_O_LANTERN.defaultBlockState(), 1)
                .build();
    }

    public static PlacedFeature createScarecrowPlaced(Holder<ConfiguredFeature<?, ?>> holder, int chance) {
        return new PlacedFeature(holder, List.of(
                RarityFilter.onAverageOnceEvery(chance),
                InSquarePlacement.spread(),
                HeightmapPlacement.onHeightmap(Heightmap.Types.OCEAN_FLOOR),
                BiomeFilter.biome(),
                BlockPredicateFilter.forPredicate(BlockPredicate.matchesTag(ModTags.SCARECROW_SPAWNABLE_ON, new Vec3i(0, -1, 0)))
        ));
    }

}
