package com.tristankechlo.explorations.worldgen.treedecorators;


import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.feature.Feature;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class TreeDecoratorContext {

    private final ISeedReader level;
    private final Random random;
    private final List<BlockPos> leaves;
    private final Set<BlockPos> states;
    private final MutableBoundingBox boundingBox;

    public TreeDecoratorContext(ISeedReader level, Random random, List<BlockPos> leaves, Set<BlockPos> states, MutableBoundingBox boundingBox) {
        this.level = level;
        this.random = random;
        this.leaves = leaves;
        this.states = states;
        this.boundingBox = boundingBox;
    }

    public boolean isAir(BlockPos pos) {
        return Feature.isAir(this.level, pos);
    }

    public ISeedReader level() {
        return this.level;
    }

    public Random random() {
        return this.random;
    }

    public List<BlockPos> leaves() {
        return this.leaves;
    }

    public Set<BlockPos> states() {
        return this.states;
    }

    public MutableBoundingBox boundingBox() {
        return this.boundingBox;
    }

}

