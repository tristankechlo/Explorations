package com.tristankechlo.explorations.worldgen.treedecorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tristankechlo.explorations.init.ModRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ChainBlock;
import net.minecraft.block.LanternBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.*;
import java.util.stream.Collectors;

public class LanternDecorator extends TreeDecorator {

    public static final Codec<LanternDecorator> CODEC = RecordCodecBuilder.create((builder) -> {
        return builder.group(
                Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter((decorator) -> decorator.probability),
                Codec.intRange(0, 10).fieldOf("min_lantern_count").orElse(2).forGetter((decorator) -> decorator.minLanternCount),
                Codec.intRange(0, 10).fieldOf("max_lantern_count").orElse(3).forGetter((decorator) -> decorator.maxLanternCount),
                Codec.intRange(0, 10).fieldOf("min_chain_length").orElse(1).forGetter((decorator) -> decorator.minChainLength),
                Codec.intRange(0, 10).fieldOf("max_chain_length").orElse(2).forGetter((decorator) -> decorator.maxChainLength)
        ).apply(builder, LanternDecorator::new);
    });

    private final BlockState chain = Blocks.CHAIN.defaultBlockState().setValue(ChainBlock.AXIS, Direction.Axis.Y);
    private final BlockState lantern = Blocks.LANTERN.defaultBlockState().setValue(LanternBlock.HANGING, Boolean.TRUE);
    private final float probability; // chance for the tree to have lanterns
    private final int minLanternCount;
    private final int maxLanternCount;
    private final int minChainLength;
    private final int maxChainLength;

    public LanternDecorator(float probability) {
        this(probability, 2, 3, 1, 2);
    }

    public LanternDecorator(float probability, int minLanternCount, int maxLanternCount, int minChainLength, int maxChainLength) {
        this.probability = probability;
        this.minLanternCount = minLanternCount;
        this.maxLanternCount = maxLanternCount;
        this.minChainLength = minChainLength;
        this.maxChainLength = maxChainLength;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return ModRegistry.LANTERN.get();
    }

    @Override
    public void place(ISeedReader level, Random random, List<BlockPos> trunks, List<BlockPos> leaves, Set<BlockPos> states, MutableBoundingBox boundingBox) {
        TreeDecoratorContext context = new TreeDecoratorContext(level, random, leaves, states, boundingBox);
        place(context);
    }

    private void place(TreeDecoratorContext context) {
        Random random = context.random();
        if (random.nextFloat() > this.probability) {
            return;
        }
        final int count = getRandom(random, minLanternCount, maxLanternCount);
        List<BlockPos> possible = getLeavesShuffled(context, count);
        for (BlockPos blockPos : possible) {
            int length = getRandom(random, minChainLength, maxChainLength);
            // possible lantern position
            BlockPos blockpos;
            // set all chains
            for (blockpos = blockPos; context.isAir(blockpos) && length > 0; --length) {
                this.setBlock(context.level(), blockpos, chain, context.states(), context.boundingBox());
                blockpos = blockpos.below();
            }
            // set the lantern below
            this.setBlock(context.level(), blockpos, lantern, context.states(), context.boundingBox());
        }
    }

    private static List<BlockPos> getLeavesShuffled(TreeDecoratorContext context, final int maxCount) {
        List<BlockPos> all = context.leaves().stream()
                .map(BlockPos::below)
                .filter(context::isAir) // only blocks where the block below is empty
                .distinct() // no duplicate blocks
                .collect(Collectors.toList());
        Collections.shuffle(all, context.random()); // shuffle all elements

        // remove blocks if size > maxCount
        int maxIndex = Math.min(maxCount, all.size());
        all.subList(maxIndex, all.size()).clear();
        return all;
    }

    public int getRandom(Random random, int min, int max) {
        return random.nextInt((max - min) + 1) + min;
    }

}
