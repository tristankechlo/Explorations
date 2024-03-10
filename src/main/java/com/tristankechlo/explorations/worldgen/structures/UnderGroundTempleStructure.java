package com.tristankechlo.explorations.worldgen.structures;

import com.tristankechlo.explorations.worldgen.structures.util.JigsawConfig;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class UnderGroundTempleStructure extends JigsawStructure {

    public UnderGroundTempleStructure() {
        super(JigsawConfig.UNDERGROUND_TEMPLE);
    }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return UnderGroundTempleStructure.Start::new;
    }

    @Override
    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoFeatureConfig featureConfig) {
        return chunkRandom.nextDouble() < 0.6;
    }

    public class Start extends JigsawStructure.Start {

        public Start(Structure<NoFeatureConfig> structure, int chunkX, int chunkZ, MutableBoundingBox boundingBox, int reference, long seed) {
            super(structure, chunkX, chunkZ, boundingBox, reference, seed);
        }

        @Override
        public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biome, NoFeatureConfig config) {
            super.generatePieces(dynamicRegistryManager, chunkGenerator, templateManagerIn, chunkX, chunkZ, biome, config);
            this.moveBelowSeaLevel(chunkGenerator.getSeaLevel(), this.random, 20);
        }

    }

}
