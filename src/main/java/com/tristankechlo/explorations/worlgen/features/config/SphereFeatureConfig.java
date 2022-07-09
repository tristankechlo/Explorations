package com.tristankechlo.explorations.worlgen.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;

public record SphereFeatureConfig(IntProvider radius, IntProvider offset, BlockStateProvider stateProvider, boolean forceReplace) implements FeatureConfiguration {

	public static final Codec<SphereFeatureConfig> CODEC = RecordCodecBuilder.create((builder) -> {
		return builder.group(
				// radius of the sphere
				IntProvider.codec(0, 10).fieldOf("radius").orElse(ConstantInt.of(3)).forGetter(SphereFeatureConfig::radius),
				// y offset of the sphere
				IntProvider.codec(0, 10).fieldOf("offset").orElse(ConstantInt.of(3)).forGetter(SphereFeatureConfig::offset),
				// blockstates from which the sphere will be build
				BlockStateProvider.CODEC.fieldOf("state_provider").forGetter(SphereFeatureConfig::stateProvider),
				// whether or not the sphere can replace other blocks
				Codec.BOOL.optionalFieldOf("force_place", Boolean.FALSE).forGetter(SphereFeatureConfig::forceReplace)
		).apply(builder, SphereFeatureConfig::new);
	});

}
