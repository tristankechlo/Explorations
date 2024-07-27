package com.tristankechlo.explorations.worldgen.structures.processors;

import com.mojang.serialization.Codec;
import com.tristankechlo.explorations.init.ModRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.item.DyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.StructureProcessor;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class WoolReplacingProcessor extends StructureProcessor {

    private static final List<String> ALL_COLORS = Arrays.stream(DyeColor.values()).map(DyeColor::getName).collect(Collectors.toList());
    private static final Pattern PATTERN = Pattern.compile("minecraft:[a-z_]+_wool", Pattern.CASE_INSENSITIVE);
    public static final Codec<WoolReplacingProcessor> CODEC = Codec.unit(WoolReplacingProcessor::new);

    private String color = null;

    @Override
    public Template.BlockInfo process(IWorldReader level, BlockPos var2, BlockPos var3, Template.BlockInfo var4, Template.BlockInfo var5, PlacementSettings settings, Template var7) {
        Random random = settings.getRandom(var5.pos);
        this.ensureColor(random);
        if (!isWoolBlock(var5.state)) {
            return var5;
        }

        BlockState replacement = getNewWool(this.color);
        if (replacement == null) {
            return var5;
        }

        return new Template.BlockInfo(var5.pos, replacement, var5.nbt);
    }

    private BlockState getNewWool(String color) {
        ResourceLocation location = new ResourceLocation(color + "_wool");
        if (ForgeRegistries.BLOCKS.containsKey(location)) {
            return ForgeRegistries.BLOCKS.getValue(location).defaultBlockState();
        }
        return null;
    }

    private void ensureColor(Random random) {
        if (this.color == null) {
            this.color = ALL_COLORS.get(random.nextInt(ALL_COLORS.size()));
        }
    }

    private boolean isWoolBlock(BlockState state) {
        String block_id = ForgeRegistries.BLOCKS.getKey(state.getBlock()).toString();
        return PATTERN.matcher(block_id).find();
    }

    @Override
    protected IStructureProcessorType<?> getType() {
        return ModRegistry.WOOL_REPLACING_PROCESSOR;
    }

}
