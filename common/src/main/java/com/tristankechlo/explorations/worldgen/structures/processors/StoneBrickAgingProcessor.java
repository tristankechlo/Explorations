package com.tristankechlo.explorations.worldgen.structures.processors;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tristankechlo.explorations.init.ModRegistry;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class StoneBrickAgingProcessor extends StructureProcessor {

    public static final Codec<StoneBrickAgingProcessor> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Chances.CODEC.fieldOf("chances").forGetter(processor -> processor.chances)
    ).apply(instance, StoneBrickAgingProcessor::new));
    private static final List<Block> STONE_BRICKS_REPLACEMENTS = List.of(Blocks.MOSSY_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS);
    private static final Map<Block, Block> BRICKS = Map.of(
            Blocks.CHISELED_STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS,
            Blocks.DEEPSLATE_BRICKS, Blocks.CRACKED_DEEPSLATE_BRICKS,
            Blocks.DEEPSLATE_TILES, Blocks.CRACKED_DEEPSLATE_TILES
    );
    private final Chances chances;

    public StoneBrickAgingProcessor(Chances chances) {
        this.chances = chances;
    }

    @Nullable
    @Override
    public StructureBlockInfo processBlock(LevelReader level, BlockPos var2, BlockPos var3, StructureBlockInfo var4, StructureBlockInfo var5, StructurePlaceSettings var6) {
        BlockState old = var5.state;
        BlockState replacement = null;
        RandomSource random = var6.getRandom(var5.pos);

        if (old.is(Blocks.STONE_BRICKS)) {
            Block block = Util.getRandom(STONE_BRICKS_REPLACEMENTS, random);
            replacement = this.tryReplacing(block, old, random, chances.bricks());
        } else if (old.is(Blocks.STONE_BRICK_STAIRS)) {
            replacement = this.tryReplacing(Blocks.MOSSY_STONE_BRICK_STAIRS, old, random, chances.stairs());
        } else if (old.is(Blocks.STONE_BRICK_SLAB)) {
            replacement = this.tryReplacing(Blocks.MOSSY_STONE_BRICK_SLAB, old, random, chances.slabs());
        } else if (old.is(Blocks.STONE_BRICK_WALL)) {
            replacement = this.tryReplacing(Blocks.MOSSY_STONE_BRICK_WALL, old, random, chances.walls());
        } else if (BRICKS.containsKey(old.getBlock())) {
            Block temp = BRICKS.get(old.getBlock());
            replacement = this.tryReplacing(temp, old, random, chances.bricks());
        }

        if (replacement == null) {
            return var5;
        }
        return new StructureBlockInfo(var5.pos, replacement, var5.nbt);
    }

    private BlockState tryReplacing(Block block, BlockState defaultState, RandomSource random, float chance) {
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
    protected StructureProcessorType<?> getType() {
        return ModRegistry.STONE_BRICK_AGING_PROCESSOR.get();
    }

    private record Chances(float bricks, float walls, float stairs, float slabs) {
        public static final Codec<Chances> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.floatRange(0.0F, 1.0F).fieldOf("bricks").orElse(0.25F).forGetter(Chances::bricks),
                Codec.floatRange(0.0F, 1.0F).fieldOf("walls").orElse(0.15F).forGetter(Chances::walls),
                Codec.floatRange(0.0F, 1.0F).fieldOf("stairs").orElse(0.15F).forGetter(Chances::stairs),
                Codec.floatRange(0.0F, 1.0F).fieldOf("slabs").orElse(0.15F).forGetter(Chances::slabs)
        ).apply(instance, Chances::new));
    }

}
