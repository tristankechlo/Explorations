package com.tristankechlo.explorations.structures;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.PostPlacementProcessor;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;

public class ForgottenWellStructure extends StructureFeature<JigsawConfiguration> {

	public ForgottenWellStructure() {
		super(JigsawConfiguration.CODEC, ForgottenWellStructure::createPiecesGenerator, PostPlacementProcessor.NONE);
	}

	@Override
	public GenerationStep.Decoration step() {
		return GenerationStep.Decoration.SURFACE_STRUCTURES;
	}

	private static boolean isFeatureChunk(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
		BlockPos blockPos = context.chunkPos().getWorldPosition();
		int landHeight = context.chunkGenerator().getFirstOccupiedHeight(blockPos.getX(), blockPos.getZ(),
				Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor());
		NoiseColumn columnOfBlocks = context.chunkGenerator().getBaseColumn(blockPos.getX(), blockPos.getZ(),
				context.heightAccessor());
		BlockState topBlock = columnOfBlocks.getBlock(landHeight);
		return topBlock.getFluidState().isEmpty();
	}

	private static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(
			PieceGeneratorSupplier.Context<JigsawConfiguration> context) {

		// skip generation when chunk is not a feature chunk
		if (!ForgottenWellStructure.isFeatureChunk(context)) {
			return Optional.empty();
		}

		BlockPos blockpos = context.chunkPos().getMiddleBlockPosition(0);

		int topLandY = context.chunkGenerator().getFirstFreeHeight(blockpos.getX(), blockpos.getZ(),
				Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor());
		blockpos = blockpos.atY(topLandY - 2);

		final boolean intersecting = false;
		final boolean placeAtHeightMap = false;
		Optional<PieceGenerator<JigsawConfiguration>> structurePiecesGenerator = JigsawPlacement.addPieces(context,
				PoolElementStructurePiece::new, blockpos, intersecting, placeAtHeightMap);

		return structurePiecesGenerator;
	}

}
