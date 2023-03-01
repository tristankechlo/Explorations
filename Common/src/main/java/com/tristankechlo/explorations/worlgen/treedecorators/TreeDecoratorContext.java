package com.tristankechlo.explorations.worlgen.treedecorators;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;

import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public record TreeDecoratorContext(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> states, Random random, List<BlockPos> leaves) {

    public void setBlock(BlockPos pos, BlockState state) {
        this.states.accept(pos, state);
    }

    public boolean isAir(BlockPos pos) {
        return Feature.isAir(this.level, pos);
    }

}
