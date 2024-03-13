package com.tristankechlo.explorations.worldgen.structures;

import com.tristankechlo.explorations.worldgen.structures.pieces.SlimeCaveStructurePiece;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class SlimeCaveStructure extends Structure<NoFeatureConfig> {

    public SlimeCaveStructure() {
        super(NoFeatureConfig.CODEC);
    }

    @Override
    public GenerationStage.Decoration step() {
        return GenerationStage.Decoration.UNDERGROUND_STRUCTURES;
    }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return SlimeCaveStructure.Start::new;
    }

    @Override
    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom,
                                     int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoFeatureConfig featureConfig) {
        int x = chunkX << 4;
        int z = chunkZ << 4;
        int landHeight = chunkGenerator.getFirstOccupiedHeight(x, z, Heightmap.Type.WORLD_SURFACE_WG);
        return (landHeight - 20) > 0;
    }

    public static class Start extends StructureStart<NoFeatureConfig> {

        public Start(Structure<NoFeatureConfig> structure, int chunkX, int chunkZ, MutableBoundingBox boundingBox, int reference, long seed) {
            super(structure, chunkX, chunkZ, boundingBox, reference, seed);
        }

        @Override
        public void generatePieces(DynamicRegistries registries, ChunkGenerator chunkGenerator, TemplateManager templateManager,
                                   int chunkX, int chunkZ, Biome biome, NoFeatureConfig configIn) {
            int x = chunkX << 4;
            int z = chunkZ << 4;

            // determine structure parameters
            int landHeight = chunkGenerator.getFirstOccupiedHeight(x, z, Heightmap.Type.WORLD_SURFACE_WG);
            int yPlacement = this.getRandomNumber(5, landHeight - 20); // random value between 5 and (surface height at this x,y)
            Rotation rotation = Util.getRandom(Rotation.values(), this.random);
            BlockPos pos = new BlockPos(x, yPlacement, z);

            // generate structure piece
            this.pieces.add(new SlimeCaveStructurePiece(pos, rotation, templateManager));
            this.calculateBoundingBox();
        }

        private int getRandomNumber(int min, int max) {
            return random.nextInt(max - min) + min;
        }

    }

}
