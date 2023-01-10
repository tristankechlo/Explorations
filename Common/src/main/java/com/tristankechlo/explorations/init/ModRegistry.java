package com.tristankechlo.explorations.init;

import com.tristankechlo.explorations.Explorations;
import com.tristankechlo.explorations.platform.Services;
import com.tristankechlo.explorations.registration.RegistrationProvider;
import com.tristankechlo.explorations.registration.RegistryObject;
import com.tristankechlo.explorations.worlgen.features.ScarecrowFeature;
import com.tristankechlo.explorations.worlgen.features.config.ScarecrowFeatureConfig;
import com.tristankechlo.explorations.worlgen.structures.UndergroundTempleStructure;
import com.tristankechlo.explorations.worlgen.treedecorators.CaveVineDecorator;
import com.tristankechlo.explorations.worlgen.treedecorators.LanternDecorator;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.structure.StructureType;

public final class ModRegistry {

    public static void loadClass() {} //this is just to load the class

    /* FEATURES */
    public static final RegistrationProvider<Feature<?>> FEATURES = RegistrationProvider.get(BuiltInRegistries.FEATURE, Explorations.MOD_ID);
    public static final RegistryObject<Feature<ScarecrowFeatureConfig>> SCARECROW = FEATURES.register("scarecrow", () -> new ScarecrowFeature(ScarecrowFeatureConfig.CODEC));

    /* STRUCTURES */
    public static final RegistrationProvider<StructureType<?>> STRUCTURES = RegistrationProvider.get(BuiltInRegistries.STRUCTURE_TYPE, Explorations.MOD_ID);
    public static final RegistryObject<StructureType<UndergroundTempleStructure>> UNDERGROUND_TEMPLE = STRUCTURES.register("underground_temple", () -> () -> UndergroundTempleStructure.CODEC);

    /* TREE DECORATORS */
    public static final RegistrationProvider<TreeDecoratorType<?>> TREE_DECORATORS = RegistrationProvider.get(BuiltInRegistries.TREE_DECORATOR_TYPE, Explorations.MOD_ID);
    public static final RegistryObject<TreeDecoratorType<LanternDecorator>> LANTERN = TREE_DECORATORS.register("lantern", Services.PLATFORM.getTreeDecoratorType(LanternDecorator.CODEC));
    public static final RegistryObject<TreeDecoratorType<CaveVineDecorator>> CAVE_VINES = TREE_DECORATORS.register("cave_vines", Services.PLATFORM.getTreeDecoratorType(CaveVineDecorator.CODEC));

}
