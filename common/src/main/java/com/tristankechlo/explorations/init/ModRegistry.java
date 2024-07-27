package com.tristankechlo.explorations.init;

import com.tristankechlo.explorations.Explorations;
import com.tristankechlo.explorations.platform.Services;
import com.tristankechlo.explorations.registration.RegistrationProvider;
import com.tristankechlo.explorations.registration.RegistryObject;
import com.tristankechlo.explorations.worldgen.features.ScarecrowFeature;
import com.tristankechlo.explorations.worldgen.features.config.ScarecrowFeatureConfig;
import com.tristankechlo.explorations.worldgen.structures.FloatingIslandStructure;
import com.tristankechlo.explorations.worldgen.structures.SlimeCaveStructure;
import com.tristankechlo.explorations.worldgen.structures.UndergroundTempleStructure;
import com.tristankechlo.explorations.worldgen.structures.pieces.SlimeCaveStructurePiece;
import com.tristankechlo.explorations.worldgen.structures.processors.DeepslateProcessor;
import com.tristankechlo.explorations.worldgen.structures.processors.StoneBrickAgingProcessor;
import com.tristankechlo.explorations.worldgen.treedecorators.CaveVineDecorator;
import com.tristankechlo.explorations.worldgen.treedecorators.LanternDecorator;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;

public final class ModRegistry {

    public static void loadClass() {} //this is just to load the class

    /* FEATURES */
    public static final RegistrationProvider<Feature<?>> FEATURES = RegistrationProvider.get(Registry.FEATURE_REGISTRY, Explorations.MOD_ID);
    public static final RegistryObject<Feature<ScarecrowFeatureConfig>> SCARECROW = FEATURES.register("scarecrow", () -> new ScarecrowFeature(ScarecrowFeatureConfig.CODEC));

    /* STRUCTURES */
    public static final RegistrationProvider<StructureFeature<?>> STRUCTURES = RegistrationProvider.get(Registry.STRUCTURE_FEATURE, Explorations.MOD_ID);
    public static final RegistryObject<StructureFeature<?>> UNDERGROUND_TEMPLE = STRUCTURES.register("underground_temple", UndergroundTempleStructure::new);
    public static final RegistryObject<StructureFeature<?>> FLOATING_ISLAND = STRUCTURES.register("floating_island", FloatingIslandStructure::new);
    public static final RegistryObject<StructureFeature<?>> SLIME_CAVE_STRUCTURE = STRUCTURES.register("slime_cave", SlimeCaveStructure::new);

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

    /* CONFIGURED FEATURES */
    public static final RegistrationProvider<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = RegistrationProvider.get(BuiltinRegistries.CONFIGURED_FEATURE, Explorations.MOD_ID);
    private static final RegistryObject<ConfiguredFeature<?, ?>> LARGE_MUSHROOM_CONFIGURED = CONFIGURED_FEATURES.register("large_mushroom", ModFeatures::createLargeMushroomConfigured);
    private static final RegistryObject<ConfiguredFeature<?, ?>> SCARECROW_ACACIA_CONFIGURED = CONFIGURED_FEATURES.register("scarecrow_acacia", () -> ModFeatures.createScarecrowConfigured(Blocks.ACACIA_FENCE));
    private static final RegistryObject<ConfiguredFeature<?, ?>> SCARECROW_BIRCH_CONFIGURED = CONFIGURED_FEATURES.register("scarecrow_birch", () -> ModFeatures.createScarecrowConfigured(Blocks.BIRCH_FENCE));
    private static final RegistryObject<ConfiguredFeature<?, ?>> SCARECROW_DARK_OAK_CONFIGURED = CONFIGURED_FEATURES.register("scarecrow_dark_oak", () -> ModFeatures.createScarecrowConfigured(Blocks.DARK_OAK_FENCE));
    private static final RegistryObject<ConfiguredFeature<?, ?>> SCARECROW_JUNGLE_CONFIGURED = CONFIGURED_FEATURES.register("scarecrow_jungle", () -> ModFeatures.createScarecrowConfigured(Blocks.JUNGLE_FENCE));
    private static final RegistryObject<ConfiguredFeature<?, ?>> SCARECROW_OAK_CONFIGURED = CONFIGURED_FEATURES.register("scarecrow_oak", () -> ModFeatures.createScarecrowConfigured(Blocks.OAK_FENCE));
    private static final RegistryObject<ConfiguredFeature<?, ?>> SCARECROW_SPRUCE_CONFIGURED = CONFIGURED_FEATURES.register("scarecrow_spruce", () -> ModFeatures.createScarecrowConfigured(Blocks.SPRUCE_FENCE));

    /* PLACED FEATURES */
    public static final RegistrationProvider<PlacedFeature> PLACED_FEATURES = RegistrationProvider.get(BuiltinRegistries.PLACED_FEATURE, Explorations.MOD_ID);
    public static final RegistryObject<PlacedFeature> LARGE_MUSHROOM_PLACED = PLACED_FEATURES.register("large_mushroom", () -> ModFeatures.createLargeMushroomPlaced(LARGE_MUSHROOM_CONFIGURED.asHolder()));
    public static final RegistryObject<PlacedFeature> SCARECROW_ACACIA_PLACED = PLACED_FEATURES.register("scarecrow_acacia", () -> ModFeatures.createScarecrowPlaced(SCARECROW_ACACIA_CONFIGURED.asHolder(), 100));
    public static final RegistryObject<PlacedFeature> SCARECROW_BIRCH_PLACED = PLACED_FEATURES.register("scarecrow_birch", () -> ModFeatures.createScarecrowPlaced(SCARECROW_BIRCH_CONFIGURED.asHolder(), 100));
    public static final RegistryObject<PlacedFeature> SCARECROW_DARK_OAK_PLACED = PLACED_FEATURES.register("scarecrow_dark_oak", () -> ModFeatures.createScarecrowPlaced(SCARECROW_DARK_OAK_CONFIGURED.asHolder(), 100));
    public static final RegistryObject<PlacedFeature> SCARECROW_JUNGLE_PLACED = PLACED_FEATURES.register("scarecrow_jungle", () -> ModFeatures.createScarecrowPlaced(SCARECROW_JUNGLE_CONFIGURED.asHolder(), 100));
    public static final RegistryObject<PlacedFeature> SCARECROW_OAK_PLACED = PLACED_FEATURES.register("scarecrow_oak", () -> ModFeatures.createScarecrowPlaced(SCARECROW_OAK_CONFIGURED.asHolder(), 101));
    public static final RegistryObject<PlacedFeature> SCARECROW_SPRUCE_PLACED = PLACED_FEATURES.register("scarecrow_spruce", () -> ModFeatures.createScarecrowPlaced(SCARECROW_SPRUCE_CONFIGURED.asHolder(), 100));

}
