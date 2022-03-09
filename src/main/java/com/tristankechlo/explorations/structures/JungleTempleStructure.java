package com.tristankechlo.explorations.structures;

import java.util.Optional;
import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.PiecesContainer;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;

public class JungleTempleStructure extends StructureFeature<JigsawConfiguration> {

	public JungleTempleStructure() {
		super(JigsawConfiguration.CODEC, JungleTempleStructure::createPiecesGenerator, JungleTempleStructure::afterPlacement);
	}

	@Override
	public Decoration step() {
		return Decoration.SURFACE_STRUCTURES;
	}

	private static boolean isFeatureChunk(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
		ChunkPos chunkpos = context.chunkPos();
		return !context.chunkGenerator().hasFeatureChunkInRange(BuiltinStructureSets.JUNGLE_TEMPLES, context.seed(), chunkpos.x, chunkpos.z, 5);
	}

	public static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {

		// skip generation when chunk is not a feature chunk
		if (!JungleTempleStructure.isFeatureChunk(context)) {
			return Optional.empty();
		}

		BlockPos blockpos = context.chunkPos().getMiddleBlockPosition(0);

		int topLandY = context.chunkGenerator().getFirstFreeHeight(blockpos.getX(), blockpos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor());
		blockpos = blockpos.atY(topLandY + 1);

		final boolean intersecting = false;
		final boolean placeAtHeightMap = false;
		Optional<PieceGenerator<JigsawConfiguration>> structurePiecesGenerator = JigsawPlacement.addPieces(context, PoolElementStructurePiece::new, blockpos, intersecting, placeAtHeightMap);

		return structurePiecesGenerator;
	}

	/** place cobble and mossy cobble under the temple */
	private static void afterPlacement(WorldGenLevel level, StructureFeatureManager featureManager,
			ChunkGenerator chunkGenerator, Random random, BoundingBox boundingBoxIn, ChunkPos chunkPos,
			PiecesContainer piecesContainer) {

		BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
		int i = level.getMinBuildHeight();
		BoundingBox boundingbox = piecesContainer.calculateBoundingBox();
		int j = boundingbox.minY();

		for (int k = boundingBoxIn.minX(); k <= boundingBoxIn.maxX(); ++k) {
			for (int l = boundingBoxIn.minZ(); l <= boundingBoxIn.maxZ(); ++l) {
				blockpos$mutableblockpos.set(k, j, l);
				if (!level.isEmptyBlock(blockpos$mutableblockpos) && boundingbox.isInside(blockpos$mutableblockpos)
						&& piecesContainer.isInsidePiece(blockpos$mutableblockpos)) {
					for (int i1 = j - 1; i1 > i; --i1) {
						blockpos$mutableblockpos.setY(i1);
						if (!level.isEmptyBlock(blockpos$mutableblockpos)
								&& !level.getBlockState(blockpos$mutableblockpos).getMaterial().isLiquid()
								&& !level.getBlockState(blockpos$mutableblockpos).is(BlockTags.REPLACEABLE_PLANTS)) {
							break;
						}

						if (random.nextDouble() < 0.65D) {
							level.setBlock(blockpos$mutableblockpos, Blocks.COBBLESTONE.defaultBlockState(), 2);
						} else {
							level.setBlock(blockpos$mutableblockpos, Blocks.MOSSY_COBBLESTONE.defaultBlockState(), 2);
						}
					}
				}
			}
		}
	}

}
