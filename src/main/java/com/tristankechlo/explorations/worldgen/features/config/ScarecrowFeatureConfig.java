package com.tristankechlo.explorations.worldgen.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
import net.minecraft.world.gen.feature.IFeatureConfig;

public class ScarecrowFeatureConfig implements IFeatureConfig {

    public static final Codec<ScarecrowFeatureConfig> CODEC = RecordCodecBuilder.create((builder) -> {
        return builder.group(
                // the blocks to use for the arms
                BlockStateProvider.CODEC.fieldOf("arm_provider").forGetter(ScarecrowFeatureConfig::arms),
                // the block to use for the legs
                BlockStateProvider.CODEC.fieldOf("leg_provider").forGetter(ScarecrowFeatureConfig::legs),
                // the block to use for the middle part
                BlockStateProvider.CODEC.fieldOf("body_provider").orElse(getSimple(Blocks.HAY_BLOCK)).forGetter(ScarecrowFeatureConfig::body),
                // the block to use for the head
                BlockStateProvider.CODEC.fieldOf("head_provider").orElse(getSimple(Blocks.CARVED_PUMPKIN)).forGetter(ScarecrowFeatureConfig::head),
                // whether or not the scarecrow can replace other blocks
                Codec.BOOL.optionalFieldOf("force_place", Boolean.FALSE).forGetter(ScarecrowFeatureConfig::forcePlace)
        ).apply(builder, ScarecrowFeatureConfig::new);
    });

    private final BlockStateProvider arms;
    private final BlockStateProvider legs;
    private final BlockStateProvider body;
    private final BlockStateProvider head;
    private final boolean forcePlace;

    public ScarecrowFeatureConfig(BlockStateProvider arms, BlockStateProvider legs, BlockStateProvider body, BlockStateProvider head, boolean forcePlace) {
        this.arms = arms;
        this.legs = legs;
        this.body = body;
        this.head = head;
        this.forcePlace = forcePlace;
    }

    public static ScarecrowFeatureConfig simple(Block fence) {
        return new ScarecrowFeatureConfig(getSimple(fence), getSimple(fence), getSimple(Blocks.HAY_BLOCK), defaultHead(), false);
    }

    public BlockStateProvider arms() {
        return arms;
    }

    public BlockStateProvider legs() {
        return legs;
    }

    public BlockStateProvider body() {
        return body;
    }

    public BlockStateProvider head() {
        return head;
    }

    public boolean forcePlace() {
        return forcePlace;
    }

    private static BlockStateProvider getSimple(Block block) {
        return new SimpleBlockStateProvider(block.defaultBlockState());
    }

    private static WeightedBlockStateProvider defaultHead() {
        WeightedBlockStateProvider weighted = new WeightedBlockStateProvider();
        weighted.add(Blocks.CARVED_PUMPKIN.defaultBlockState(), 4);
        weighted.add(Blocks.JACK_O_LANTERN.defaultBlockState(), 1);
        return weighted;
    }

}
