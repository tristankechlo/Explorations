package com.tristankechlo.explorations.worlgen.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record SphereFeatureConfig(IntProvider radius, IntProvider offset, BlockStateProvider stateProvider,
		boolean replace) implements FeatureConfiguration {

	public static final Codec<SphereFeatureConfig> CODEC = RecordCodecBuilder.create((builder) -> {
		return builder
				.group(IntProvider.codec(0, 10).fieldOf("radius").orElse(ConstantInt.of(3)).forGetter((config) -> {
					return config.radius; // radius of the sphere
				}), IntProvider.codec(0, 10).fieldOf("offset").orElse(ConstantInt.of(3)).forGetter((config) -> {
					return config.offset; // y offset of the sphere
				}), BlockStateProvider.CODEC.fieldOf("state_provider").forGetter((config) -> {
					return config.stateProvider; // blockstates from which the sphere will be build
				}), Codec.BOOL.optionalFieldOf("replace", Boolean.FALSE).forGetter((config) -> {
					return config.replace; // whether or not the sphere can replace other blocks
				})).apply(builder, SphereFeatureConfig::new);
	});

}
