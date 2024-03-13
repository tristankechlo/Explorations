package com.tristankechlo.explorations.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.tristankechlo.explorations.Explorations;
import com.tristankechlo.explorations.worldgen.structures.*;
import com.tristankechlo.explorations.worldgen.structures.config.JigsawConfig;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class ModStructures {

    public static final DeferredRegister<Structure<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Explorations.MOD_ID);

    public static final RegistryObject<Structure<NoFeatureConfig>> DESERT_RUIN = STRUCTURES.register("desert_ruin", () -> new JigsawStructure(JigsawConfig.DESERT_RUIN));
    public static final RegistryObject<Structure<NoFeatureConfig>> FORGOTTEN_WELL = STRUCTURES.register("forgotten_well", () -> new JigsawStructure(JigsawConfig.FORGOTTEN_WELL));
    public static final RegistryObject<Structure<NoFeatureConfig>> JUNGLE_TEMPLE = STRUCTURES.register("jungle_temple", JungleTempleStructure::new);
    public static final RegistryObject<Structure<NoFeatureConfig>> UNDERGROUND_TEMPLE = STRUCTURES.register("underground_temple", UnderGroundTempleStructure::new);
    public static final RegistryObject<Structure<NoFeatureConfig>> FLOATING_ISLAND = STRUCTURES.register("floating_island", FloatingIslandStructure::new);
    public static final RegistryObject<Structure<NoFeatureConfig>> LARGE_OAK_TREE = STRUCTURES.register("large_oak_tree", () -> new JigsawStructure(JigsawConfig.LARGE_OAK_TREE));
    public static final RegistryObject<Structure<NoFeatureConfig>> LOGS = STRUCTURES.register("logs", () -> new JigsawStructure(JigsawConfig.LOGS));
    public static final RegistryObject<Structure<NoFeatureConfig>> SHRINE = STRUCTURES.register("shrine", () -> new JigsawStructure(JigsawConfig.SHRINE));
    public static final RegistryObject<Structure<NoFeatureConfig>> SLIME_CAVE = STRUCTURES.register("slime_cave", SlimeCaveStructure::new);

    public static void setupStructures() {
        setupMapSpacingAndLand(DESERT_RUIN.get(), JigsawConfig.DESERT_RUIN.separationSettings, JigsawConfig.DESERT_RUIN.transformSurroundingLand);
        setupMapSpacingAndLand(FORGOTTEN_WELL.get(), JigsawConfig.FORGOTTEN_WELL.separationSettings, JigsawConfig.FORGOTTEN_WELL.transformSurroundingLand);
        setupMapSpacingAndLand(JUNGLE_TEMPLE.get(), JigsawConfig.JUNGLE_TEMPLE.separationSettings, JigsawConfig.JUNGLE_TEMPLE.transformSurroundingLand);
        setupMapSpacingAndLand(UNDERGROUND_TEMPLE.get(), JigsawConfig.UNDERGROUND_TEMPLE.separationSettings, JigsawConfig.UNDERGROUND_TEMPLE.transformSurroundingLand);
        setupMapSpacingAndLand(FLOATING_ISLAND.get(), JigsawConfig.FLOATING_ISLAND.separationSettings, JigsawConfig.FLOATING_ISLAND.transformSurroundingLand);
        setupMapSpacingAndLand(LARGE_OAK_TREE.get(), JigsawConfig.LARGE_OAK_TREE.separationSettings, JigsawConfig.LARGE_OAK_TREE.transformSurroundingLand);
        setupMapSpacingAndLand(LOGS.get(), JigsawConfig.LOGS.separationSettings, JigsawConfig.LOGS.transformSurroundingLand);
        setupMapSpacingAndLand(SHRINE.get(), JigsawConfig.SHRINE.separationSettings, JigsawConfig.SHRINE.transformSurroundingLand);
        setupMapSpacingAndLand(SLIME_CAVE.get(), JigsawConfig.SLIME_CAVE.separationSettings, JigsawConfig.SLIME_CAVE.transformSurroundingLand);
    }

    private static <F extends Structure<?>> void setupMapSpacingAndLand(F structure, StructureSeparationSettings structureSeparationSettings, boolean transformSurroundingLand) {

        // add structures into the map in Structure class
        Structure.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);

        // whether surrounding land will be modified automatically to conform to the bottom of the structure
        if (transformSurroundingLand) {
            Structure.NOISE_AFFECTING_FEATURES = ImmutableList.<Structure<?>>builder()
                    .addAll(Structure.NOISE_AFFECTING_FEATURES).add(structure).build();
        }

        // this is the map that holds the default spacing of all structures
        DimensionStructuresSettings.DEFAULTS = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder()
                .putAll(DimensionStructuresSettings.DEFAULTS).put(structure, structureSeparationSettings).build();

        // there are very few mods that rely on seeing the structure in the noise settings registry before the world is made
        WorldGenRegistries.NOISE_GENERATOR_SETTINGS.entrySet().forEach(settings -> {
            Map<Structure<?>, StructureSeparationSettings> structureMap = settings.getValue().structureSettings().structureConfig();

            if (structureMap instanceof ImmutableMap) {
                Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(structureMap);
                tempMap.put(structure, structureSeparationSettings);
                settings.getValue().structureSettings().structureConfig = tempMap;
            } else {
                structureMap.put(structure, structureSeparationSettings);
            }
        });
    }

}
