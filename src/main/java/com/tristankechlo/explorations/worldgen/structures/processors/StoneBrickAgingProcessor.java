package com.tristankechlo.explorations.worldgen.structures.processors;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tristankechlo.explorations.init.ModRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class StoneBrickAgingProcessor extends StructureProcessor {

    public static final Codec<StoneBrickAgingProcessor> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Chances.CODEC.fieldOf("chances").forGetter(processor -> processor.chances)
    ).apply(instance, StoneBrickAgingProcessor::new));

    private static final List<Block> STONE_BRICKS_REPLACEMENTS = Arrays.asList(Blocks.MOSSY_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS);
    private final Chances chances;

    private StoneBrickAgingProcessor(Chances chances) {
        this.chances = chances;
    }

    @Nullable
    @Override
    public Template.BlockInfo process(IWorldReader level, BlockPos var2, BlockPos var3, Template.BlockInfo var4, Template.BlockInfo var5, PlacementSettings var6, @Nullable Template var7) {
        BlockState old = var5.state;
        BlockState replacement = null;
        Random random = var6.getRandom(var5.pos);

        if (old.is(Blocks.STONE_BRICKS)) {
            Block block = STONE_BRICKS_REPLACEMENTS.get(random.nextInt(STONE_BRICKS_REPLACEMENTS.size()));
            replacement = this.tryReplacing(block, old, random, chances.bricks());
        } else if (old.is(Blocks.STONE_BRICK_STAIRS)) {
            replacement = this.tryReplacing(Blocks.MOSSY_STONE_BRICK_STAIRS, old, random, chances.stairs());
        } else if (old.is(Blocks.STONE_BRICK_SLAB)) {
            replacement = this.tryReplacing(Blocks.MOSSY_STONE_BRICK_SLAB, old, random, chances.slabs());
        } else if (old.is(Blocks.STONE_BRICK_WALL)) {
            replacement = this.tryReplacing(Blocks.MOSSY_STONE_BRICK_WALL, old, random, chances.walls());
        } else if (old.is(Blocks.CHISELED_STONE_BRICKS)) {
            replacement = this.tryReplacing(Blocks.CRACKED_STONE_BRICKS, old, random, chances.bricks());
        }

        if (replacement == null) {
            return var5;
        }

        return new Template.BlockInfo(var5.pos, replacement, var5.nbt);
    }

    private BlockState tryReplacing(Block block, BlockState defaultState, Random random, float chance) {
        if (random.nextFloat() < chance) {
            BlockState state = block.defaultBlockState();
            for (Property<? extends Comparable<?>> property : defaultState.getProperties()) {
                if (state.hasProperty(property)) {
                    state = copyProperty(defaultState, state, property);
                }
            }
            return state;
        }
        return defaultState;
    }

    private static <T extends Comparable<T>> BlockState copyProperty(BlockState from, BlockState to, Property<T> property) {
        return to.setValue(property, from.getValue(property));
    }

    @Override
    protected IStructureProcessorType<?> getType() {
        return ModRegistry.STONE_BRICK_AGING_PROCESSOR;
    }

    private static class Chances {

        public static final Codec<Chances> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.floatRange(0.0F, 1.0F).fieldOf("bricks").orElse(0.25F).forGetter(Chances::bricks),
                Codec.floatRange(0.0F, 1.0F).fieldOf("walls").orElse(0.15F).forGetter(Chances::walls),
                Codec.floatRange(0.0F, 1.0F).fieldOf("stairs").orElse(0.15F).forGetter(Chances::stairs),
                Codec.floatRange(0.0F, 1.0F).fieldOf("slabs").orElse(0.15F).forGetter(Chances::slabs)
        ).apply(instance, Chances::new));

        private final float bricks;
        private final float walls;
        private final float stairs;
        private final float slabs;

        public Chances(float bricks, float walls, float stairs, float slabs) {
            this.bricks = bricks;
            this.walls = walls;
            this.stairs = stairs;
            this.slabs = slabs;
        }

        public float bricks() {
            return bricks;
        }

        public float walls() {
            return walls;
        }

        public float stairs() {
            return stairs;
        }

        public float slabs() {
            return slabs;
        }

    }
}
