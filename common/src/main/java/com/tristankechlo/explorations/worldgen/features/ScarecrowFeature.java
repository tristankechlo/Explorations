package com.tristankechlo.explorations.worldgen.features;

import com.mojang.serialization.Codec;
import com.tristankechlo.explorations.worldgen.features.config.ScarecrowFeatureConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;

public class ScarecrowFeature extends Feature<ScarecrowFeatureConfig> {

    public ScarecrowFeature(Codec<ScarecrowFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<ScarecrowFeatureConfig> context) {
        WorldGenLevel level = context.level();
        RandomSource random = context.random();

        Direction facingHead = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        Direction facingArmLeft = facingHead.getClockWise(Axis.Y);
        Direction facingArmRight = facingHead.getCounterClockWise(Axis.Y);

        BlockPos posLeg = context.origin();
        BlockPos posBody = posLeg.above(1);
        BlockPos posHead = posLeg.above(2);
        BlockPos posLeftArm = posLeg.above().relative(facingArmLeft);
        BlockPos posRightArm = posLeg.above().relative(facingArmRight);

        // check if allowed to place scarecrow here
        if (!canBePlaced(level, context.config().forcePlace(), posLeg, posBody, posHead, posLeftArm, posRightArm)) {
            return false;
        }

        BlockState stateHead = createHead(context.config().head().getState(random, posHead), facingHead);
        BlockState stateBody = context.config().body().getState(random, posBody);
        BlockState stateLegs = context.config().legs().getState(random, posLeg);
        BlockState stateArmLeft = createArm(context.config().arms().getState(random, posLeftArm), facingArmLeft);
        BlockState stateArmRight = createArm(context.config().arms().getState(random, posRightArm), facingArmRight);

        level.setBlock(posLeg, stateLegs, 3);
        level.setBlock(posBody, stateBody, 3);
        level.setBlock(posLeftArm, stateArmLeft, 3);
        level.setBlock(posRightArm, stateArmRight, 3);
        level.setBlock(posHead, stateHead, 3);

        return true;
    }

    /**
     * connect blocks like fences to the center of the scarecrow
     */
    private static BlockState createArm(BlockState armState, Direction facing) {
        if (facing == Direction.NORTH && armState.hasProperty(BlockStateProperties.SOUTH)) {
            armState = armState.setValue(BlockStateProperties.SOUTH, Boolean.TRUE);
        } else if (facing == Direction.EAST && armState.hasProperty(BlockStateProperties.WEST)) {
            armState = armState.setValue(BlockStateProperties.WEST, Boolean.TRUE);
        } else if (facing == Direction.SOUTH && armState.hasProperty(BlockStateProperties.NORTH)) {
            armState = armState.setValue(BlockStateProperties.NORTH, Boolean.TRUE);
        } else if (facing == Direction.WEST && armState.hasProperty(BlockStateProperties.EAST)) {
            armState = armState.setValue(BlockStateProperties.EAST, Boolean.TRUE);
        }
        return armState;
    }

    /**
     * rotate head when possible to look in the correct direction
     */
    private static BlockState createHead(BlockState headState, Direction facing) {
        if (headState.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
            headState = headState.setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
        } else if (headState.hasProperty(BlockStateProperties.FACING)) {
            headState = headState.setValue(BlockStateProperties.FACING, facing);
        }
        return headState;
    }

    /**
     * check if the scarecrow can be placed at the pos
     */
    private static boolean canBePlaced(WorldGenLevel level, boolean forceReplace, BlockPos... positions) {
        for (BlockPos pos : positions) {
            if (level.isOutsideBuildHeight(pos) || level.getBlockState(pos).is(BlockTags.FEATURES_CANNOT_REPLACE)) {
                return false;
            }
            BlockState state = level.getBlockState(pos);
            boolean replaceable = forceReplace || level.isEmptyBlock(pos) || state.getMaterial().isLiquid() || state.getMaterial().isReplaceable();
            if (!replaceable) {
                return false;
            }
        }
        return true;
    }

}
