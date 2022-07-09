package com.tristankechlo.explorations.worlgen.features;

import com.mojang.serialization.Codec;
import com.tristankechlo.explorations.util.ShapeGenerator;
import com.tristankechlo.explorations.worlgen.features.config.SphereFeatureConfig;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class SphereFeature extends Feature<SphereFeatureConfig> {

	public SphereFeature(Codec<SphereFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public boolean place(FeaturePlaceContext<SphereFeatureConfig> context) {
		WorldGenLevel level = context.level();
		RandomSource random = context.random();
		SphereFeatureConfig config = context.config();

		final double radius = config.radius().sample(random);
		final int offset = config.offset().sample(random);
		BlockPos origin = context.origin().above(offset);

		ShapeGenerator.makeSphere(origin, radius, (pos) -> {
			if (canBeReplaced(level, pos, config.replace())) {
				BlockState state = config.stateProvider().getState(random, origin);
				level.setBlock(pos, state, 3);
			}
		});
		return true;
	}

	private static boolean canBeReplaced(WorldGenLevel level, BlockPos pos, boolean replace) {
		if (level.isOutsideBuildHeight(pos) || level.getBlockState(pos).is(BlockTags.FEATURES_CANNOT_REPLACE)) {
			return false;
		}
		BlockState state = level.getBlockState(pos);
		return replace || level.isEmptyBlock(pos) || state.getMaterial().isLiquid()
				|| state.getMaterial().isReplaceable();
	}

}
