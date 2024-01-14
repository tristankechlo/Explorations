package com.tristankechlo.explorations.worldgen.structures.util;

import com.tristankechlo.explorations.Explorations;
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

    public final ResourceLocation startPool;
    public final GenerationStage.Decoration step;
    public final List<MobSpawnInfo.Spawners> defaultSpawnList;
    public final int size;
    public final boolean placeAtHeightmap;
    public final int yOffset;
    public final List<String> spawnBiomes;
    public final StructureSeparationSettings separationSettings;
    public final boolean transformSurroundingLand;

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
        private boolean transformSurroundingland = true;

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

        public Builder setyOffset(int yOffset) {
            this.yOffset = yOffset;
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

        public JigsawConfig build() {
            if (salt == 0 || spacing == 0 || separation == 0) {
                throw new IllegalArgumentException();
            }
            StructureSeparationSettings separationSettings = new StructureSeparationSettings(spacing, separation, salt);
            List<String> biomes = spawnBiomes.stream().map((e) -> e.location().toString()).collect(Collectors.toList());
            return new JigsawConfig(startPool, step, defaultSpawnList, size, placeAtHeightmap, yOffset, biomes, separationSettings, transformSurroundingland);
        }

    }

    public static final JigsawConfig DESERT_RUIN = new JigsawConfig.Builder()
            .setStartPool("desert_ruin_start")
            .addSpawnBiome(Biomes.DESERT).addSpawnBiome(Biomes.DESERT_HILLS).addSpawnBiome(Biomes.DESERT_LAKES)
            .setSeparationSettings(12, 5, 1487218662)
            .build();

}
