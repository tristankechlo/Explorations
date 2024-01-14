package com.tristankechlo.explorations.worldgen.structures;

import com.google.common.collect.ImmutableList;
import com.tristankechlo.explorations.Explorations;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo.Spawners;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.jigsaw.JigsawManager;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.structure.AbstractVillagePiece;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.List;

public class UnderGroundTempleStructure extends Structure<NoFeatureConfig> {

    private static final List<Spawners> STRUCTURE_MONSTERS = ImmutableList.of(
            new Spawners(EntityType.ILLUSIONER, 100, 4, 9),
            new Spawners(EntityType.VINDICATOR, 100, 4, 9),
            new Spawners(EntityType.PILLAGER, 100, 4, 9)
    );

    public UnderGroundTempleStructure() {
        super(NoFeatureConfig.CODEC);
    }

    @Override
    public List<Spawners> getDefaultSpawnList() {
        return STRUCTURE_MONSTERS;
    }

    @Override
    public IStartFactory<NoFeatureConfig> getStartFactory() {
        return UnderGroundTempleStructure.Start::new;
    }

    @Override
    public Decoration step() {
        return GenerationStage.Decoration.UNDERGROUND_STRUCTURES;
    }

    @Override
    protected boolean isFeatureChunk(ChunkGenerator chunkGenerator, BiomeProvider biomeSource, long seed, SharedSeedRandom chunkRandom, int chunkX, int chunkZ, Biome biome, ChunkPos chunkPos, NoFeatureConfig featureConfig) {
        return chunkRandom.nextDouble() < 0.6;
    }

    public static class Start extends StructureStart<NoFeatureConfig> {

        private static final ResourceLocation START_POOL = new ResourceLocation(Explorations.MOD_ID, "underground_temple/underground_temple_start");

        public Start(Structure<NoFeatureConfig> structure, int chunkX, int chunkZ, MutableBoundingBox boundingBox, int reference, long seed) {
            super(structure, chunkX, chunkZ, boundingBox, reference, seed);
        }

        @Override
        public void generatePieces(DynamicRegistries dynamicRegistryManager, ChunkGenerator chunkGenerator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biome, NoFeatureConfig config) {

            int x = chunkX * 16;
            int z = chunkZ * 16;
            BlockPos centerPos = new BlockPos(x, 0, z);
            final int maxDepth = 6;
            final boolean placeAtHeightMap = false;
            final boolean intersecting = false;
            JigsawManager.addPieces(
                    dynamicRegistryManager,
                    new VillageConfig(() -> getJigsawPattern(dynamicRegistryManager), maxDepth),
                    AbstractVillagePiece::new,
                    chunkGenerator,
                    templateManagerIn,
                    centerPos,
                    this.pieces,
                    this.random,
                    intersecting,
                    placeAtHeightMap
            );

            Vector3i structureCenter = this.pieces.get(0).getBoundingBox().getCenter();
            int xOffset = centerPos.getX() - structureCenter.getX();
            int zOffset = centerPos.getZ() - structureCenter.getZ();
            this.pieces.forEach(piece -> piece.move(xOffset, 0, zOffset));
            this.calculateBoundingBox();
            this.moveBelowSeaLevel(chunkGenerator.getSeaLevel(), this.random, 20);
        }

        private JigsawPattern getJigsawPattern(DynamicRegistries dynamicRegistryManager) {
            return dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(START_POOL);
        }

    }

}
