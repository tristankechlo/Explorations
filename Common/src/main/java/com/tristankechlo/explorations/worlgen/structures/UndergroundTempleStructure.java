package com.tristankechlo.explorations.worlgen.structures;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.PostPlacementProcessor;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;

import java.util.Optional;
import java.util.Random;

public final class UndergroundTempleStructure extends StructureFeature<JigsawConfiguration> {

    public UndergroundTempleStructure() {
        super(JigsawConfiguration.CODEC, UndergroundTempleStructure::createPiecesGenerator, PostPlacementProcessor.NONE);
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.UNDERGROUND_STRUCTURES;
    }

    private static boolean isFeatureChunk(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        Random random = new Random(context.seed());
        return random.nextDouble() < 0.6;
    }

    public static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        // skip generation when the chunk is not a feature chunk
        if (!UndergroundTempleStructure.isFeatureChunk(context)) {
            return Optional.empty();
        }

        Random random = new Random(context.seed());
        int maxY = context.chunkGenerator().getSeaLevel();
        if (maxY <= 30) {
            return Optional.empty();
        }
        maxY -= 15;
        int minY = context.chunkGenerator().getMinY() + 15;
        int y = minY + random.nextInt(maxY - minY);
        BlockPos blockpos = context.chunkPos().getMiddleBlockPosition(y);

        final boolean intersecting = false;
        final boolean placeAtHeightMap = false;

        return JigsawPlacement.addPieces(context, PoolElementStructurePiece::new, blockpos, intersecting, placeAtHeightMap);
    }

}
