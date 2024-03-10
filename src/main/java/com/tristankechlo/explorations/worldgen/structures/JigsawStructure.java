package com.tristankechlo.explorations.worldgen.structures;

import com.tristankechlo.explorations.worldgen.structures.config.JigsawConfig;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.structure.*;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;

public class JigsawStructure extends Structure<NoFeatureConfig> {

    protected final JigsawConfig config;

    public JigsawStructure(JigsawConfig config) {
        super(NoFeatureConfig.CODEC);
        this.config = config;
    }

    @Override
    public GenerationStage.Decoration step() {
        return this.config.step;
    }

    @Override
    public List<MobSpawnInfo.Spawners> getDefaultSpawnList() {
        return this.config.defaultSpawnList;
    }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return JigsawStructure.Start::new;
    }

    protected class Start extends StructureStart<NoFeatureConfig> {

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
            JigsawConfig config = JigsawStructure.this.config;
            final boolean intersecting = false;
            this.addJigsawPieces(registries, chunkGenerator, templateManager, centerPos, intersecting, config);

            // move pieces to fit into land
            if (config.yOffset != 0) {
                this.boundingBox.move(0, config.yOffset, 0);
                this.pieces.forEach(piece -> piece.move(0, config.yOffset, 0));
                this.pieces.forEach(piece -> piece.getBoundingBox().y0 += config.yOffset);
            }

            // recalculate position of whole structure
            Vector3i structureCenter = this.pieces.get(0).getBoundingBox().getCenter();
            int xOffset = centerPos.getX() - structureCenter.getX();
            int zOffset = centerPos.getZ() - structureCenter.getZ();
            for (StructurePiece structurePiece : this.pieces) {
                structurePiece.move(xOffset, 0, zOffset);
            }
            this.calculateBoundingBox();
        }

        protected JigsawPattern getJigsawPattern(DynamicRegistries registries, JigsawConfig config) {
            return registries.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(config.startPool);
        }

        protected void addJigsawPieces(DynamicRegistries registries, ChunkGenerator chunkGenerator, TemplateManager templateManager,
                                       BlockPos centerPos, boolean intersecting, JigsawConfig config) {
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
        }
    }

}
