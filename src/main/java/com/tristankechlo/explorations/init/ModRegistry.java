package com.tristankechlo.explorations.init;

import com.tristankechlo.explorations.Explorations;
import com.tristankechlo.explorations.worlgen.features.ScarecrowFeature;
import com.tristankechlo.explorations.worlgen.features.config.ScarecrowFeatureConfig;
import com.tristankechlo.explorations.worlgen.structures.UndergroundTempleStructure;
import com.tristankechlo.explorations.worlgen.treedecorators.CaveVineDecorator;
import com.tristankechlo.explorations.worlgen.treedecorators.LanternDecorator;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public final class ModRegistry {

    /**
     * FEATURES
     */
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registry.FEATURE_REGISTRY, Explorations.MOD_ID);
    public static final RegistryObject<Feature<ScarecrowFeatureConfig>> SCARECROW = FEATURES.register("scarecrow", () -> new ScarecrowFeature(ScarecrowFeatureConfig.CODEC));

    /**
     * STRUCTURES
     */
    public static final DeferredRegister<StructureType<?>> STRUCTURES = DeferredRegister.create(Registry.STRUCTURE_TYPE_REGISTRY, Explorations.MOD_ID);
    public static final RegistryObject<StructureType<UndergroundTempleStructure>> UNDERGROUND_TEMPLE = STRUCTURES.register("underground_temple", () -> () -> UndergroundTempleStructure.CODEC);

    /**
     * TREE DECORATORS
     */
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(Registry.TREE_DECORATOR_TYPE_REGISTRY, Explorations.MOD_ID);
    public static final RegistryObject<TreeDecoratorType<LanternDecorator>> LANTERN = TREE_DECORATORS.register("lantern", () -> new TreeDecoratorType<>(LanternDecorator.CODEC));
    public static final RegistryObject<TreeDecoratorType<CaveVineDecorator>> CAVE_VINES = TREE_DECORATORS.register("cave_vines", () -> new TreeDecoratorType<>(CaveVineDecorator.CODEC));

}
