package com.tristankechlo.explorations.worlgen.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.SimpleStateProvider;

public record ScarecrowFeatureConfig(BlockStateProvider arms, BlockStateProvider legs, BlockStateProvider body, BlockStateProvider head, boolean forcePlace) implements FeatureConfiguration {

	public static final Codec<ScarecrowFeatureConfig> CODEC = RecordCodecBuilder.create((builder) -> {
		return builder.group(
				// the blocks to use for the arms
				BlockStateProvider.CODEC.fieldOf("arm_provider").forGetter(ScarecrowFeatureConfig::arms),
				// the block to use for the legs
				BlockStateProvider.CODEC.fieldOf("leg_provider").forGetter(ScarecrowFeatureConfig::legs),
				// the block to use for the middle part
				BlockStateProvider.CODEC.fieldOf("body_provider").orElse(SimpleStateProvider.simple(Blocks.HAY_BLOCK)).forGetter(ScarecrowFeatureConfig::body),
				// the block to use for the head
				BlockStateProvider.CODEC.fieldOf("head_provider").orElse(SimpleStateProvider.simple(Blocks.CARVED_PUMPKIN)).forGetter(ScarecrowFeatureConfig::head),
				// whether or not the scarecrow can replace other blocks
				Codec.BOOL.optionalFieldOf("force_place", Boolean.FALSE).forGetter(ScarecrowFeatureConfig::forcePlace)
		).apply(builder, ScarecrowFeatureConfig::new);
	});

}
