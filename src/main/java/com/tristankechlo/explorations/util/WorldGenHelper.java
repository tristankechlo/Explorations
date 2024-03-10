package com.tristankechlo.explorations.util;

import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;

import java.util.ArrayList;
import java.util.List;

public class WorldGenHelper {

    public static List<RegistryKey<Biome>> FOREST_BIOMES = new ArrayList<>();
    public static List<RegistryKey<Biome>> JUNGLE_BIOMES = new ArrayList<>();
    public static List<RegistryKey<Biome>> FOREST_AND_JUNGLE_BIOMES = new ArrayList<>();

    static {
        FOREST_BIOMES.add(Biomes.FOREST);
        FOREST_BIOMES.add(Biomes.FLOWER_FOREST);
        FOREST_BIOMES.add(Biomes.BIRCH_FOREST);
        FOREST_BIOMES.add(Biomes.DARK_FOREST);
        FOREST_BIOMES.add(Biomes.BIRCH_FOREST_HILLS);
        FOREST_BIOMES.add(Biomes.TALL_BIRCH_FOREST);
        FOREST_BIOMES.add(Biomes.DARK_FOREST_HILLS);

        JUNGLE_BIOMES.add(Biomes.JUNGLE);
        JUNGLE_BIOMES.add(Biomes.JUNGLE_HILLS);
        JUNGLE_BIOMES.add(Biomes.JUNGLE_EDGE);
        JUNGLE_BIOMES.add(Biomes.BAMBOO_JUNGLE);
        JUNGLE_BIOMES.add(Biomes.MODIFIED_JUNGLE);
        JUNGLE_BIOMES.add(Biomes.MODIFIED_JUNGLE_EDGE);
        JUNGLE_BIOMES.add(Biomes.BAMBOO_JUNGLE_HILLS);

        FOREST_AND_JUNGLE_BIOMES.addAll(FOREST_BIOMES);
        FOREST_AND_JUNGLE_BIOMES.addAll(JUNGLE_BIOMES);
    }

}
