package com.tristankechlo.explorations.worldgen.structures;

import com.tristankechlo.explorations.worldgen.structures.config.JigsawConfig;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.StructureSeparationSettings;

public class FloatingIslandStructure extends JigsawStructure {

    public FloatingIslandStructure() {
        super(JigsawConfig.FLOATING_ISLAND);
    }

    @Override
    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoFeatureConfig featureConfig) {
        int i = chunkX >> 4;
        int j = chunkZ >> 4;
        chunkRandom.setSeed((long) (i ^ j << 4) ^ seed);
        chunkRandom.nextInt();
        return isNearOceanMonument(chunkGenerator, seed, chunkRandom, chunkX, chunkZ);
    }

    private boolean isNearOceanMonument(ChunkGenerator chunkGenerator, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ) {
        StructureSeparationSettings structureseparationsettings = chunkGenerator.getSettings().getConfig(Structure.OCEAN_MONUMENT);
        int distance = 10; // distance in chunks
        if (structureseparationsettings != null) {
            for (int i = chunkX - distance; i <= chunkX + distance; ++i) {
                for (int j = chunkZ - distance; j <= chunkZ + distance; ++j) {
                    ChunkPos chunkpos = Structure.OCEAN_MONUMENT.getPotentialFeatureChunk(structureseparationsettings, seed, chunkRandom, i, j);
                    if (i == chunkpos.x && j == chunkpos.z) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
