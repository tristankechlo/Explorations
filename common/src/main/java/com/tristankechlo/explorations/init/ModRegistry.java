package com.tristankechlo.explorations.init;

import com.tristankechlo.explorations.Explorations;
import com.tristankechlo.explorations.platform.Services;
import com.tristankechlo.explorations.registration.RegistrationProvider;
import com.tristankechlo.explorations.registration.RegistryObject;
import com.tristankechlo.explorations.worlgen.features.ScarecrowFeature;
import com.tristankechlo.explorations.worlgen.features.config.ScarecrowFeatureConfig;
import com.tristankechlo.explorations.worlgen.structures.SlimeCaveStructure;
import com.tristankechlo.explorations.worlgen.structures.UndergroundTempleStructure;
import com.tristankechlo.explorations.worlgen.structures.pieces.SlimeCaveStructurePiece;
import com.tristankechlo.explorations.worlgen.structures.processors.DeepslateProcessor;
import com.tristankechlo.explorations.worlgen.structures.processors.StoneBrickAgingProcessor;
import com.tristankechlo.explorations.worlgen.treedecorators.CaveVineDecorator;
import com.tristankechlo.explorations.worlgen.treedecorators.LanternDecorator;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

public final class ModRegistry {

    public static void loadClass() {} //this is just to load the class

    /* FEATURES */
    public static final RegistrationProvider<Feature<?>> FEATURES = RegistrationProvider.get(Registry.FEATURE_REGISTRY, Explorations.MOD_ID);
    public static final RegistryObject<Feature<ScarecrowFeatureConfig>> SCARECROW = FEATURES.register("scarecrow", () -> new ScarecrowFeature(ScarecrowFeatureConfig.CODEC));

    /* STRUCTURES */
    public static final RegistrationProvider<StructureType<?>> STRUCTURES = RegistrationProvider.get(Registry.STRUCTURE_TYPE_REGISTRY, Explorations.MOD_ID);
    public static final RegistryObject<StructureType<UndergroundTempleStructure>> UNDERGROUND_TEMPLE = STRUCTURES.register("underground_temple", () -> () -> UndergroundTempleStructure.CODEC);
    public static final RegistryObject<StructureType<SlimeCaveStructure>> SLIME_CAVE_STRUCTURE = STRUCTURES.register("slime_cave", () -> () -> SlimeCaveStructure.CODEC);

    /* TREE DECORATORS */
    public static final RegistrationProvider<TreeDecoratorType<?>> TREE_DECORATORS = RegistrationProvider.get(Registry.TREE_DECORATOR_TYPE_REGISTRY, Explorations.MOD_ID);
    public static final RegistryObject<TreeDecoratorType<LanternDecorator>> LANTERN = TREE_DECORATORS.register("lantern", Services.PLATFORM.getTreeDecoratorType(LanternDecorator.CODEC));
    public static final RegistryObject<TreeDecoratorType<CaveVineDecorator>> CAVE_VINES = TREE_DECORATORS.register("cave_vines", Services.PLATFORM.getTreeDecoratorType(CaveVineDecorator.CODEC));

    /* STRUCTURE PIECE TYPE */
    public static final RegistrationProvider<StructurePieceType> STRUCTURE_PIECE_TYPES = RegistrationProvider.get(Registry.STRUCTURE_PIECE, Explorations.MOD_ID);
    public static final RegistryObject<StructurePieceType> SLIME_CAVE_PIECE = STRUCTURE_PIECE_TYPES.register("slime_cave_piece", () -> SlimeCaveStructurePiece::new);

    /* STRUCTURE PROCESSOR */
    public static final RegistrationProvider<StructureProcessorType<?>> STRUCTURE_PROCESSORS = RegistrationProvider.get(Registry.STRUCTURE_PROCESSOR, Explorations.MOD_ID);
    public static final RegistryObject<StructureProcessorType<DeepslateProcessor>> DEEPSLATE_PROCESSOR = STRUCTURE_PROCESSORS.register("deepslate", () -> () -> DeepslateProcessor.CODEC);
    public static final RegistryObject<StructureProcessorType<StoneBrickAgingProcessor>> STONE_BRICK_AGING_PROCESSOR = STRUCTURE_PROCESSORS.register("stone_brick_aging", () -> () -> StoneBrickAgingProcessor.CODEC);

}
