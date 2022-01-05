package com.tristankechlo.explorations.structures;

import java.util.Optional;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.tristankechlo.explorations.Explorations;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.PostPlacementProcessor;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;

public class ForgottenWellStructure extends StructureFeature<JigsawConfiguration> {

	public static final ResourceLocation START_POOL = new ResourceLocation(Explorations.MOD_ID,
			"forgotten_well_start_pool");

	public ForgottenWellStructure(Codec<JigsawConfiguration> codec) {
		super(codec, ForgottenWellStructure::createPiecesGenerator, PostPlacementProcessor.NONE);
	}

	@Override
	public GenerationStep.Decoration step() {
		return GenerationStep.Decoration.SURFACE_STRUCTURES;
	}

	public static ImmutableSet<ResourceKey<Biome>> getDefaultSpawnBiomes() {
		ImmutableSet<ResourceKey<Biome>> biomes = ImmutableSet.<ResourceKey<Biome>>builder().add(Biomes.FOREST)
				.add(Biomes.BIRCH_FOREST).add(Biomes.OLD_GROWTH_BIRCH_FOREST).add(Biomes.DARK_FOREST)
				.add(Biomes.FLOWER_FOREST).build();
		return biomes;
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

	public static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(
			PieceGeneratorSupplier.Context<JigsawConfiguration> context) {

		// skip generation when chunk is not a feature chunk
		if (!ForgottenWellStructure.isFeatureChunk(context)) {
			return Optional.empty();
		}

		final int maxDepth = 5;
		JigsawConfiguration config = new JigsawConfiguration(() -> getJigsawPattern(context.registryAccess()),
				maxDepth);

		PieceGeneratorSupplier.Context<JigsawConfiguration> newContext = new PieceGeneratorSupplier.Context<>(
				context.chunkGenerator(), context.biomeSource(), context.seed(), context.chunkPos(), config,
				context.heightAccessor(), context.validBiome(), context.structureManager(), context.registryAccess());

		BlockPos blockpos = context.chunkPos().getMiddleBlockPosition(0);
		int landHeight = context.chunkGenerator().getFirstOccupiedHeight(blockpos.getX(), blockpos.getZ(),
				Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor());

		final boolean intersecting = false;
		final boolean placeAtHeightMap = false;
		Optional<PieceGenerator<JigsawConfiguration>> structurePiecesGenerator = JigsawPlacement.addPieces(newContext,
				PoolElementStructurePiece::new, blockpos.atY(landHeight - 1), intersecting, placeAtHeightMap);

		// TODO move pieces to fit into land

		return structurePiecesGenerator;

	}

	private static StructureTemplatePool getJigsawPattern(RegistryAccess dynamicRegistryManager) {
		return dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(START_POOL);
	}

}
