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

public class UndergroundTempleStructure extends StructureFeature<JigsawConfiguration> {

    public UndergroundTempleStructure() {
        super(JigsawConfiguration.CODEC, UndergroundTempleStructure::createPiecesGenerator,
                PostPlacementProcessor.NONE);
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.UNDERGROUND_STRUCTURES;
    }

    private static boolean isFeatureChunk(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        Random random = new Random(context.seed());
        return random.nextDouble() < 0.6;
    }


    public static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(
            PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        // skip generation when the chunk is not a feature chunk
        if (!UndergroundTempleStructure.isFeatureChunk(context)) {
            return Optional.empty();
        }

        int sealevel = context.chunkGenerator().getSeaLevel();
        if (sealevel <= 30) {
            return Optional.empty();
        }

        Random random = new Random(context.seed());
        BlockPos blockpos = context.chunkPos().getMiddleBlockPosition(random.nextInt(0, sealevel) - 15);

        final boolean intersecting = false;
        final boolean placeAtHeightMap = false;
        Optional<PieceGenerator<JigsawConfiguration>> structurePiecesGenerator = JigsawPlacement.addPieces(context,
                PoolElementStructurePiece::new, blockpos, intersecting, placeAtHeightMap);

        return structurePiecesGenerator;
    }

}
