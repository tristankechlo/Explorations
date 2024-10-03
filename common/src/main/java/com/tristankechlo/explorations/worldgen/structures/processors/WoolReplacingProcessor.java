package com.tristankechlo.explorations.worldgen.structures.processors;

import com.mojang.serialization.Codec;
import com.tristankechlo.explorations.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class WoolReplacingProcessor extends StructureProcessor {

    private static final List<String> ALL_COLORS = Arrays.stream(DyeColor.values()).map(DyeColor::getName).toList();
    private static final Pattern PATTERN = Pattern.compile("minecraft:[a-z_]+_wool", Pattern.CASE_INSENSITIVE);
    public static final Codec<WoolReplacingProcessor> CODEC = Codec.unit(WoolReplacingProcessor::new);

    private String color = null;

    @Override
    public StructureTemplate.StructureBlockInfo processBlock(LevelReader level, BlockPos var2, BlockPos var3, StructureTemplate.StructureBlockInfo var4, StructureTemplate.StructureBlockInfo var5, StructurePlaceSettings settings) {
        RandomSource random = settings.getRandom(var5.pos);
        this.ensureColor(random);
        if (!isWoolBlock(var5.state)) {
            return var5;
        }

        BlockState replacement = getNewWool(this.color);
        if (replacement == null) {
            return var5;
        }

        return new StructureTemplate.StructureBlockInfo(var5.pos, replacement, var5.nbt);
    }

    private BlockState getNewWool(String color) {
        ResourceLocation location = new ResourceLocation(color + "_wool");
        if (BuiltInRegistries.BLOCK.containsKey(location)) {
            return BuiltInRegistries.BLOCK.get(location).defaultBlockState();
        }
        return null;
    }

    private void ensureColor(RandomSource random) {
        if (this.color == null) {
            this.color = ALL_COLORS.get(random.nextInt(ALL_COLORS.size()));
        }
    }

    private boolean isWoolBlock(BlockState state) {
        String block_id = BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString();
        return PATTERN.matcher(block_id).find();
    }

    @Override
    protected StructureProcessorType<?> getType() {
        return ModRegistry.WOOL_REPLACING_PROCESSOR.get();
    }

}
