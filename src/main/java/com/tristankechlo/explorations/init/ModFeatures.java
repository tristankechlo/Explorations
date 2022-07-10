package com.tristankechlo.explorations.init;

import com.tristankechlo.explorations.Explorations;
import com.tristankechlo.explorations.worlgen.features.ScarecrowFeature;
import com.tristankechlo.explorations.worlgen.features.SphereFeature;
import com.tristankechlo.explorations.worlgen.features.config.ScarecrowFeatureConfig;
import com.tristankechlo.explorations.worlgen.features.config.SphereFeatureConfig;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModFeatures {

	public static final DeferredRegister<Feature<? extends FeatureConfiguration>> FEATURES = DeferredRegister.create(Registry.FEATURE_REGISTRY, Explorations.MOD_ID);

	public static final RegistryObject<Feature<SphereFeatureConfig>> SPHERE = FEATURES.register("sphere", () -> new SphereFeature(SphereFeatureConfig.CODEC));
	public static final RegistryObject<Feature<ScarecrowFeatureConfig>> SCARECROW = FEATURES.register("scarecrow", () -> new ScarecrowFeature(ScarecrowFeatureConfig.CODEC));

}
