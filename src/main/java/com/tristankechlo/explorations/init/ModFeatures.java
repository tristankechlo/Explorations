package com.tristankechlo.explorations.init;

import com.tristankechlo.explorations.Explorations;
import com.tristankechlo.explorations.worlgen.features.ScarecrowFeature;
import com.tristankechlo.explorations.worlgen.features.config.ScarecrowFeatureConfig;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModFeatures {

    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registry.FEATURE_REGISTRY, Explorations.MOD_ID);

    public static final RegistryObject<Feature<ScarecrowFeatureConfig>> SCARECROW = FEATURES.register("scarecrow", () -> new ScarecrowFeature(ScarecrowFeatureConfig.CODEC));

}
