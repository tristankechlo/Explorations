package com.tristankechlo.explorations.structures;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.tristankechlo.explorations.Explorations;
import com.tristankechlo.explorations.init.ModStructures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.biome.MobSpawnSettings.SpawnerData;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.feature.structures.JigsawPlacement;
import net.minecraft.world.level.levelgen.feature.structures.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.PostPlacementProcessor;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.world.StructureSpawnListGatherEvent;

public class UnderGroundTempleStructure extends StructureFeature<JigsawConfiguration> {

	private static final ResourceLocation START_POOL = new ResourceLocation(Explorations.MOD_ID,
			"underground_temple/underground_temple_start");

	public UnderGroundTempleStructure(Codec<JigsawConfiguration> codec) {
		super(codec, UnderGroundTempleStructure::createPiecesGenerator, PostPlacementProcessor.NONE);
	}

	private static final Lazy<List<MobSpawnSettings.SpawnerData>> STRUCTURE_MONSTERS = Lazy
			.of(() -> ImmutableList.of(new SpawnerData(EntityType.ILLUSIONER, 100, 4, 9),
					new SpawnerData(EntityType.VINDICATOR, 100, 4, 9),
					new SpawnerData(EntityType.PILLAGER, 100, 4, 9)));

	public static void setupStructureSpawns(final StructureSpawnListGatherEvent event) {
		if (event.getStructure() == ModStructures.UNDERGROUND_TEMPLE.get()) {
			event.addEntitySpawns(MobCategory.MONSTER, STRUCTURE_MONSTERS.get());
		}
	}

	@Override
	public Decoration step() {
		return Decoration.UNDERGROUND_STRUCTURES;
	}

	private static boolean isFeatureChunk(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
		Random random = new Random(context.seed());
		return random.nextDouble() < 0.6;
	}

	public static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(
			PieceGeneratorSupplier.Context<JigsawConfiguration> context) {

		// skip generation when chunk is not a feature chunk
		if (!UnderGroundTempleStructure.isFeatureChunk(context)) {
			return Optional.empty();
		}

		final int maxDepth = 6;
		JigsawConfiguration config = new JigsawConfiguration(() -> getJigsawPattern(context.registryAccess()),
				maxDepth);

		PieceGeneratorSupplier.Context<JigsawConfiguration> newContext = new PieceGeneratorSupplier.Context<>(
				context.chunkGenerator(), context.biomeSource(), context.seed(), context.chunkPos(), config,
				context.heightAccessor(), context.validBiome(), context.structureManager(), context.registryAccess());

		int sealevel = context.chunkGenerator().getSeaLevel();
		Random random = new Random(context.seed());
		BlockPos blockpos = context.chunkPos().getMiddleBlockPosition(random.nextInt(0, sealevel) - 20);

		final boolean intersecting = false;
		final boolean placeAtHeightMap = false;
		Optional<PieceGenerator<JigsawConfiguration>> structurePiecesGenerator = JigsawPlacement.addPieces(newContext,
				PoolElementStructurePiece::new, blockpos, intersecting, placeAtHeightMap);

		return structurePiecesGenerator;
	}

	private static StructureTemplatePool getJigsawPattern(RegistryAccess dynamicRegistryManager) {
		return dynamicRegistryManager.registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY).get(START_POOL);
	}

}
