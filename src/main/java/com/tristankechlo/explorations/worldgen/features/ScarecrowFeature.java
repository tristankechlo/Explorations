package com.tristankechlo.explorations.worldgen.features;

import com.tristankechlo.explorations.Explorations;
import com.tristankechlo.explorations.worldgen.features.config.ScarecrowFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class ScarecrowFeature extends Feature<ScarecrowFeatureConfig> {

    private static final ITag<Block> FEATURES_CANNOT_REPLACE = BlockTags.getAllTags().getTagOrEmpty(new ResourceLocation("features_cannot_replace"));
    private static final ITag<Block> SCARECROW_SPAWNABLE_ON = BlockTags.createOptional(new ResourceLocation(Explorations.MOD_ID, "scarecrow_spawnable_on"));

    public ScarecrowFeature() {
        super(ScarecrowFeatureConfig.CODEC);
    }

    @Override
    public boolean place(ISeedReader level, ChunkGenerator chunkGenerator, Random random,
                         BlockPos blockPos, ScarecrowFeatureConfig config) {

        Direction facingHead = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        Direction facingArmLeft = facingHead.getClockWise();
        Direction facingArmRight = facingHead.getCounterClockWise();

        BlockPos posLeg = new BlockPos(blockPos);
        BlockPos posBody = posLeg.above(1);
        BlockPos posHead = posLeg.above(2);
        BlockPos posLeftArm = posLeg.above().relative(facingArmLeft);
        BlockPos posRightArm = posLeg.above().relative(facingArmRight);

        // only spawn scarecrows on possible blocks
        if (!level.getBlockState(posLeg.below()).is(SCARECROW_SPAWNABLE_ON)) {
            return false;
        }

        // check if allowed to place scarecrow here
        if (!canBePlaced(level, config.forcePlace(), posLeg, posBody, posHead, posLeftArm, posRightArm)) {
            return false;
        }

        BlockState stateHead = createHead(config.head().getState(random, posHead), facingHead);
        BlockState stateBody = config.body().getState(random, posBody);
        BlockState stateLegs = config.legs().getState(random, posLeg);
        BlockState stateArmLeft = createArm(config.arms().getState(random, posLeftArm), facingArmLeft);
        BlockState stateArmRight = createArm(config.arms().getState(random, posRightArm), facingArmRight);

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
    private static boolean canBePlaced(ISeedReader level, boolean forceReplace, BlockPos... positions) {
        for (BlockPos pos : positions) {
            if (level.getMaxBuildHeight() < pos.getY() || level.getBlockState(pos).is(FEATURES_CANNOT_REPLACE)) {
                return false;
            }
            BlockState state = level.getBlockState(pos);
            boolean replaceable = forceReplace || level.isEmptyBlock(pos) || state.getFluidState().isEmpty() || state.getMaterial().isReplaceable();
            if (!replaceable) {
                return false;
            }
        }
        return true;
    }

}
