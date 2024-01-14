package com.tristankechlo.explorations.init;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.tristankechlo.explorations.Explorations;
import com.tristankechlo.explorations.worldgen.structures.ForgottenWellStructure;
import com.tristankechlo.explorations.worldgen.structures.JungleTempleStructure;
import com.tristankechlo.explorations.worldgen.structures.UnderGroundTempleStructure;
import com.tristankechlo.explorations.worldgen.structures.util.JigsawConfig;
import com.tristankechlo.explorations.worldgen.structures.util.JigsawStructure;
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
    public static final RegistryObject<Structure<NoFeatureConfig>> FORGOTTEN_WELL = STRUCTURES.register("forgotten_well", ForgottenWellStructure::new);
    public static final RegistryObject<Structure<NoFeatureConfig>> JUNGLE_TEMPLE = STRUCTURES.register("jungle_temple", JungleTempleStructure::new);
    public static final RegistryObject<Structure<NoFeatureConfig>> UNDERGROUND_TEMPLE = STRUCTURES.register("underground_temple", UnderGroundTempleStructure::new);

    public static void setupStructures() {
        setupMapSpacingAndLand(DESERT_RUIN.get(), JigsawConfig.DESERT_RUIN.separationSettings, JigsawConfig.DESERT_RUIN.transformSurroundingLand);
        setupMapSpacingAndLand(FORGOTTEN_WELL.get(), new StructureSeparationSettings(10, 5, 2147413647), true);
        setupMapSpacingAndLand(JUNGLE_TEMPLE.get(), new StructureSeparationSettings(17, 6, 2147413646), false);
        setupMapSpacingAndLand(UNDERGROUND_TEMPLE.get(), new StructureSeparationSettings(15, 6, 2147413645), false);
    }

    private static <F extends Structure<?>> void setupMapSpacingAndLand(F structure, StructureSeparationSettings structureSeparationSettings, boolean transformSurroundingLand) {
        Structure.STRUCTURES_REGISTRY.put(structure.getRegistryName().toString(), structure);

        if (transformSurroundingLand) {
            Structure.NOISE_AFFECTING_FEATURES = ImmutableList.<Structure<?>>builder().addAll(Structure.NOISE_AFFECTING_FEATURES).add(structure).build();
        }

        DimensionStructuresSettings.DEFAULTS = ImmutableMap.<Structure<?>, StructureSeparationSettings>builder().putAll(DimensionStructuresSettings.DEFAULTS).put(structure, structureSeparationSettings).build();

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
