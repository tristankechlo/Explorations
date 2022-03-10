package com.tristankechlo.explorations.structures;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.PostPlacementProcessor;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;

public class FloatingIslandStructure extends StructureFeature<JigsawConfiguration> {

	public FloatingIslandStructure() {
		super(JigsawConfiguration.CODEC, FloatingIslandStructure::createPiecesGenerator, PostPlacementProcessor.NONE);
	}

	@Override
	public GenerationStep.Decoration step() {
		return GenerationStep.Decoration.SURFACE_STRUCTURES;
	}

	private static boolean isFeatureChunk(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
		ChunkPos chunkpos = context.chunkPos();
		return !context.chunkGenerator().hasFeatureChunkInRange(BuiltinStructureSets.OCEAN_MONUMENTS, context.seed(),
				chunkpos.x, chunkpos.z, 10);
	}

	public static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(
			PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
		if (!FloatingIslandStructure.isFeatureChunk(context)) {
			return Optional.empty();
		}
		BlockPos blockpos = context.chunkPos().getMiddleBlockPosition(0);

		int topLandY = context.chunkGenerator().getFirstFreeHeight(blockpos.getX(), blockpos.getZ(),
				Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor());
		int targetHeight = Math.min(context.heightAccessor().getMaxBuildHeight() - 20, topLandY + 60);
		blockpos = blockpos.atY(targetHeight);

		Optional<PieceGenerator<JigsawConfiguration>> structurePiecesGenerator = JigsawPlacement.addPieces(context,
				PoolElementStructurePiece::new, blockpos, false, false);

		return structurePiecesGenerator;
	}
}
