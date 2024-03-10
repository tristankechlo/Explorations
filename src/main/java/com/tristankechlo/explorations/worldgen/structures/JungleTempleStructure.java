package com.tristankechlo.explorations.worldgen.structures;

import com.tristankechlo.explorations.worldgen.structures.util.JigsawConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;

public class JungleTempleStructure extends JigsawStructure {

    public JungleTempleStructure() {
        super(JigsawConfig.JUNGLE_TEMPLE);
    }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return JungleTempleStructure.Start::new;
    }

    @Override
    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoFeatureConfig featureConfig) {
        BlockPos centerOfChunk = new BlockPos(chunkX * 16, 0, chunkZ * 16);
        int landHeight = chunkGenerator.getFirstOccupiedHeight(centerOfChunk.getX(), centerOfChunk.getZ(), Heightmap.Type.WORLD_SURFACE_WG);
        IBlockReader columnOfBlocks = chunkGenerator.getBaseColumn(centerOfChunk.getX(), centerOfChunk.getZ());
        BlockState topBlock = columnOfBlocks.getBlockState(centerOfChunk.above(landHeight));
        return topBlock.getFluidState().isEmpty() && landHeight <= 80;
    }

    public class Start extends JigsawStructure.Start {

        public Start(Structure<NoFeatureConfig> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn) {
            super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
        }

        @Override
        public void generatePieces(DynamicRegistries registries, ChunkGenerator chunkGenerator, TemplateManager templateManager,
                                   int chunkX, int chunkZ, Biome biome, NoFeatureConfig configIn) {

            // transform chunk coordinates to block coordinates
            int x = chunkX * 16;
            int z = chunkZ * 16;
            BlockPos centerPos = new BlockPos(x - this.random.nextInt(10), 0, z - this.random.nextInt(10));

            // generate jigsaw structure
            JigsawConfig config = JungleTempleStructure.this.config;
            final boolean intersecting = false;
            JigsawManager.addPieces(
                    registries,
                    new VillageConfig(() -> getJigsawPattern(registries, config), config.size),
                    AbstractVillagePiece::new,
                    chunkGenerator,
                    templateManager,
                    centerPos,
                    this.pieces,
                    this.random,
                    intersecting,
                    config.placeAtHeightmap
            );

            // move pieces to fit into land
            this.pieces.forEach(piece -> piece.move(0, 1, 0));

            Vector3i structureCenter = this.pieces.get(0).getBoundingBox().getCenter();
            int xOffset = centerPos.getX() - structureCenter.getX();
            int zOffset = centerPos.getZ() - structureCenter.getZ();
            this.pieces.forEach(piece -> piece.move(xOffset, 0, zOffset));
            this.calculateBoundingBox();
        }

        @Override
        public void placeInChunk(ISeedReader seedReader, StructureManager manager, ChunkGenerator chunkGenerator, Random random, MutableBoundingBox boundingBox, ChunkPos pos) {
            super.placeInChunk(seedReader, manager, chunkGenerator, random, boundingBox, pos);

            // place stone bricks under the structure
            int i = this.boundingBox.y0;
            for (int j = boundingBox.x0; j <= boundingBox.x1; ++j) {
                for (int k = boundingBox.z0; k <= boundingBox.z1; ++k) {
                    BlockPos blockpos = new BlockPos(j, i, k);
                    if (!seedReader.isEmptyBlock(blockpos) && this.boundingBox.isInside(blockpos)) {
                        boolean flag = false;

                        for (StructurePiece structurepiece : this.pieces) {
                            if (structurepiece.getBoundingBox().isInside(blockpos)) {
                                flag = true;
                                break;
                            }
                        }

                        if (flag) {
                            for (int l = i - 1; l > 1; --l) {
                                BlockPos blockpos1 = new BlockPos(j, l, k);
                                if (!seedReader.isEmptyBlock(blockpos1) && !seedReader.getBlockState(blockpos1).getMaterial().isLiquid()) {
                                    break;
                                }

                                if (random.nextFloat() < 0.3F) {
                                    seedReader.setBlock(blockpos1, Blocks.MOSSY_STONE_BRICKS.defaultBlockState(), 2);
                                } else {
                                    seedReader.setBlock(blockpos1, Blocks.STONE_BRICKS.defaultBlockState(), 2);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
