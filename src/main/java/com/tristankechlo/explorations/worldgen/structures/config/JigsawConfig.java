package com.tristankechlo.explorations.worldgen.structures.config;

import com.tristankechlo.explorations.Explorations;
import net.minecraft.entity.EntityType;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JigsawConfig {

    public static final JigsawConfig DESERT_RUIN = new JigsawConfig.Builder().setStartPool("desert_ruin_start")
            .addSpawnBiome(Biomes.DESERT).addSpawnBiome(Biomes.DESERT_HILLS).addSpawnBiome(Biomes.DESERT_LAKES)
            .setSeparationSettings(12, 5, 1487218662).setSize(1).build();

    public static final JigsawConfig FORGOTTEN_WELL = new JigsawConfig.Builder().setStartPool("forgotten_well_start")
            .addSpawnBiome(Biomes.FOREST).addSpawnBiome(Biomes.BIRCH_FOREST).addSpawnBiome(Biomes.BIRCH_FOREST_HILLS).addSpawnBiome(Biomes.DARK_FOREST).addSpawnBiome(Biomes.DARK_FOREST_HILLS).addSpawnBiome(Biomes.TALL_BIRCH_FOREST).addSpawnBiome(Biomes.FLOWER_FOREST)
            .setSeparationSettings(10, 5, 2147413647).setSize(1).build();

    public static final JigsawConfig JUNGLE_TEMPLE = new JigsawConfig.Builder().setStartPool("jungle_temple/jungle_temple_start")
            .addSpawnBiome(Biomes.JUNGLE).addSpawnBiome(Biomes.JUNGLE_EDGE).addSpawnBiome(Biomes.JUNGLE_HILLS).addSpawnBiome(Biomes.MODIFIED_JUNGLE).addSpawnBiome(Biomes.MODIFIED_JUNGLE_EDGE).addSpawnBiome(Biomes.BAMBOO_JUNGLE).addSpawnBiome(Biomes.BAMBOO_JUNGLE_HILLS)
            .setSeparationSettings(17, 6, 2147413646).setTransformSurroundingLand(false).setSize(3).build();

    public static final JigsawConfig UNDERGROUND_TEMPLE = new JigsawConfig.Builder().setStartPool("underground_temple/start")
            .setSeparationSettings(15, 6, 2147413645).setTransformSurroundingLand(false).setSize(6).setPlaceAtHeightmap(false)
            .addDefaultSpawn(new MobSpawnInfo.Spawners(EntityType.ILLUSIONER, 100, 4, 9))
            .addDefaultSpawn(new MobSpawnInfo.Spawners(EntityType.VINDICATOR, 100, 4, 9))
            .addDefaultSpawn(new MobSpawnInfo.Spawners(EntityType.PILLAGER, 100, 4, 9))
            .setStep(GenerationStage.Decoration.UNDERGROUND_STRUCTURES).build();

    public static final JigsawConfig FLOATING_ISLAND = new JigsawConfig.Builder().setStartPool("floating_island_start")
            .setSeparationSettings(16, 8, 1701811218).setSize(1).setTransformSurroundingLand(false).setYOffset(60)
            .addSpawnBiome(Biomes.OCEAN).addSpawnBiome(Biomes.DEEP_COLD_OCEAN)
            .addSpawnBiome(Biomes.COLD_OCEAN).addSpawnBiome(Biomes.DEEP_COLD_OCEAN)
            .addSpawnBiome(Biomes.FROZEN_OCEAN).addSpawnBiome(Biomes.DEEP_FROZEN_OCEAN)
            .addSpawnBiome(Biomes.LUKEWARM_OCEAN).addSpawnBiome(Biomes.DEEP_LUKEWARM_OCEAN)
            .addSpawnBiome(Biomes.WARM_OCEAN).addSpawnBiome(Biomes.DEEP_WARM_OCEAN)
            .build();


    public final ResourceLocation startPool; // location of the json-file for the start pool
    public final GenerationStage.Decoration step; // in which generation stage to generate the structure
    public final List<MobSpawnInfo.Spawners> defaultSpawnList; // what mobs can spawn inside the structure
    public final int size; // how large the jigsaw structure can generate
    public final boolean placeAtHeightmap; // place the structure at the surface
    public final int yOffset; // move structure up or down
    public final List<String> spawnBiomes; // list of biomes to generate the structure in
    public final StructureSeparationSettings separationSettings; // spacing between structures
    public final boolean transformSurroundingLand; // adjust the land to incorporate this structure

    private JigsawConfig(ResourceLocation startPool, GenerationStage.Decoration step, List<MobSpawnInfo.Spawners> defaultSpawnList, int size, boolean placeAtHeightmap, int yOffset, List<String> spawnBiomes, StructureSeparationSettings settings, boolean transformSurroundingLand) {
        this.startPool = startPool;
        this.step = step;
        this.defaultSpawnList = defaultSpawnList;
        this.size = size;
        this.placeAtHeightmap = placeAtHeightmap;
        this.yOffset = yOffset;
        this.spawnBiomes = spawnBiomes;
        this.separationSettings = settings;
        this.transformSurroundingLand = transformSurroundingLand;
    }

    public static class Builder {

        private ResourceLocation startPool;
        private GenerationStage.Decoration step = GenerationStage.Decoration.SURFACE_STRUCTURES;
        private List<MobSpawnInfo.Spawners> defaultSpawnList = new ArrayList<>();
        private int size = 5;
        private boolean placeAtHeightmap = true;
        private int yOffset = 0;
        private List<RegistryKey<Biome>> spawnBiomes = new ArrayList<>();
        private int salt = 0;
        private int spacing = 0;
        private int separation = 0;
        private boolean transformSurroundingLand = true;

        public Builder() {
            startPool = new ResourceLocation(Explorations.MOD_ID, "start_pool");
        }

        public Builder setStartPool(String path) {
            this.startPool = new ResourceLocation(Explorations.MOD_ID, path);
            return this;
        }

        public Builder setStep(GenerationStage.Decoration step) {
            this.step = step;
            return this;
        }

        public Builder addDefaultSpawn(MobSpawnInfo.Spawners spawners) {
            this.defaultSpawnList.add(spawners);
            return this;
        }

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public Builder setPlaceAtHeightmap(boolean placeAtHeightmap) {
            this.placeAtHeightmap = placeAtHeightmap;
            return this;
        }

        public Builder addSpawnBiome(RegistryKey<Biome> biome) {
            this.spawnBiomes.add(biome);
            return this;
        }

        public Builder setSeparationSettings(int spacing, int separation, int salt) {
            this.spacing = spacing;
            this.separation = separation;
            this.salt = salt;
            return this;
        }

        public Builder setTransformSurroundingLand(boolean transformLand) {
            this.transformSurroundingLand = transformLand;
            return this;
        }

        public Builder setYOffset(int offset) {
            this.yOffset = offset;
            return this;
        }

        public JigsawConfig build() {
            if (salt == 0 || spacing == 0 || separation == 0) {
                throw new IllegalArgumentException();
            }
            StructureSeparationSettings separationSettings = new StructureSeparationSettings(spacing, separation, salt);
            List<String> biomes = spawnBiomes.stream().map((e) -> e.location().toString()).collect(Collectors.toList());
            return new JigsawConfig(startPool, step, defaultSpawnList, size, placeAtHeightmap, yOffset, biomes, separationSettings, transformSurroundingLand);
        }

    }

}
