package com.tristankechlo.explorations.worldgen.structures.processors;

import com.mojang.serialization.MapCodec;
import com.tristankechlo.explorations.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class DeepslateProcessor extends StructureProcessor {

    public static final DeepslateProcessor INSTANCE = new DeepslateProcessor();
    public static final MapCodec<DeepslateProcessor> CODEC = MapCodec.unit(() -> INSTANCE);
    private static final Map<Block, Block> REPLACEMENTS = Map.of(Blocks.STONE, Blocks.DEEPSLATE, Blocks.MOSSY_COBBLESTONE, Blocks.TUFF);

    @Nullable
    @Override
    public StructureBlockInfo processBlock(LevelReader level, BlockPos var2, BlockPos var3, StructureBlockInfo var4, StructureBlockInfo var5, StructurePlaceSettings var6) {
        Block block = var5.state().getBlock();
        if (!REPLACEMENTS.containsKey(block)) {
            return var5;
        }
        BlockPos pos = var5.pos();
        if (pos.getY() >= 0) {
            return var5;
        }
        Block replacement = REPLACEMENTS.get(block);
        return new StructureBlockInfo(pos, replacement.defaultBlockState(), null);
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModRegistry.DEEPSLATE_PROCESSOR.get();
    }

}
