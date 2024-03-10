package com.tristankechlo.explorations.worldgen.structures;

import com.tristankechlo.explorations.worldgen.structures.util.JigsawConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class ForgottenWellStructure extends JigsawStructure {

    public ForgottenWellStructure() {
        super(JigsawConfig.FORGOTTEN_WELL);
    }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return ForgottenWellStructure.Start::new;
    }

    public class Start extends JigsawStructure.Start {

        public Start(Structure<NoFeatureConfig> structure, int chunkX, int chunkZ, MutableBoundingBox boundingBox, int reference, long seed) {
            super(structure, chunkX, chunkZ, boundingBox, reference, seed);
        }

        @Override
        public void generatePieces(DynamicRegistries registries, ChunkGenerator chunkGenerator, TemplateManager templateManager,
                                   int chunkX, int chunkZ, Biome biome, NoFeatureConfig configIn) {

            // transform chunk coordinates to block coordinates
            int x = chunkX * 16;
            int z = chunkZ * 16;
            BlockPos centerPos = new BlockPos(x, 0, z);

            // generate jigsaw structure
            JigsawConfig config = ForgottenWellStructure.this.config;
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
            this.pieces.forEach(piece -> piece.move(0, -2, 0));
            this.pieces.forEach(piece -> piece.getBoundingBox().y0 += 2);

            // recalculate position of whole structure
            Vector3i structureCenter = this.pieces.get(0).getBoundingBox().getCenter();
            int xOffset = centerPos.getX() - structureCenter.getX();
            int zOffset = centerPos.getZ() - structureCenter.getZ();
            for (StructurePiece structurePiece : this.pieces) {
                structurePiece.move(xOffset, 0, zOffset);
            }
            this.calculateBoundingBox();
        }
    }

}
