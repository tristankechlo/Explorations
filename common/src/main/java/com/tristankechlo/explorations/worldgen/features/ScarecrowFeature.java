package com.tristankechlo.explorations.worldgen.features;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public class ScarecrowFeature implements Feature {

    public static final MapCodec<ScarecrowFeature> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    // the blocks to use for the arms
                    BlockStateProvider.CODEC.fieldOf("arm_provider").forGetter(f -> f.arms),
                    // the block to use for the legs
                    BlockStateProvider.CODEC.fieldOf("leg_provider").forGetter(f -> f.legs),
                    // the block to use for the middle part
                    BlockStateProvider.CODEC.fieldOf("body_provider").orElse(BlockStateProvider.holderOf(Blocks.HAY_BLOCK)).forGetter(f -> f.body),
                    // the block to use for the head
                    BlockStateProvider.CODEC.fieldOf("head_provider").orElse(BlockStateProvider.holderOf(Blocks.CARVED_PUMPKIN)).forGetter(f -> f.head),
                    // whether or not the scarecrow can replace other blocks
                    Codec.BOOL.optionalFieldOf("force_place", Boolean.FALSE).forGetter(f -> f.forcePlace)
            ).apply(instance, ScarecrowFeature::new)
    );

    private final Holder<BlockStateProvider> arms;
    private final Holder<BlockStateProvider> legs;
    private final Holder<BlockStateProvider> body;
    private final Holder<BlockStateProvider> head;
    private final boolean forcePlace;

    public ScarecrowFeature(Holder<BlockStateProvider> arms, Holder<BlockStateProvider> legs, Holder<BlockStateProvider> body, Holder<BlockStateProvider> head, boolean forcePlace) {
        this.arms = arms;
        this.legs = legs;
        this.body = body;
        this.head = head;
        this.forcePlace = forcePlace;
    }

    @Override
    public boolean place(WorldGenLevel level, ChunkGenerator chunkGenerator, RandomSource random, BlockPos posLeg) {
        Direction facingHead = Direction.Plane.HORIZONTAL.getRandomDirection(random);
        Direction facingArmLeft = facingHead.getClockWise(Direction.Axis.Y);
        Direction facingArmRight = facingHead.getCounterClockWise(Direction.Axis.Y);

        BlockPos posBody = posLeg.above(1);
        BlockPos posHead = posLeg.above(2);
        BlockPos posLeftArm = posLeg.above().relative(facingArmLeft);
        BlockPos posRightArm = posLeg.above().relative(facingArmRight);

        // check if allowed to place scarecrow here
        if (!canBePlaced(level, this.forcePlace, posLeg, posBody, posHead, posLeftArm, posRightArm)) {
            return false;
        }

        BlockState stateHead = createHead(this.head.value().getState(level, random, posHead), facingHead);
        BlockState stateBody = this.body.value().getState(level, random, posBody);
        BlockState stateLegs = this.legs.value().getState(level, random, posLeg);
        BlockState stateArmLeft = createArm(this.arms.value().getState(level, random, posLeftArm), facingArmLeft);
        BlockState stateArmRight = createArm(this.arms.value().getState(level, random, posRightArm), facingArmRight);

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
            boolean replaceable = forceReplace || level.isEmptyBlock(pos) || state.getFluidState().isEmpty() || state.canBeReplaced();
            if (!replaceable) {
                return false;
            }
        }
        return true;
    }

    @Override
    public MapCodec<ScarecrowFeature> codec() {
        return CODEC;
    }
}
