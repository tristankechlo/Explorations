package com.tristankechlo.explorations.worlgen.structures;

import java.util.Optional;
import java.util.Random;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tristankechlo.explorations.init.ModStructures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class UndergroundTempleStructure extends Structure {

	public static final Codec<UndergroundTempleStructure> CODEC = RecordCodecBuilder
			.<UndergroundTempleStructure>mapCodec(instance -> instance
					.group(UndergroundTempleStructure.settingsCodec(instance),
							StructureTemplatePool.CODEC.fieldOf("start_pool")
									.forGetter(structure -> structure.startPool),
							ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name")
									.forGetter(structure -> structure.startJigsawName),
							Codec.intRange(0, 30).fieldOf("size").forGetter(structure -> structure.size))
					.apply(instance, UndergroundTempleStructure::new))
			.codec();

	private final int size;
	private final Holder<StructureTemplatePool> startPool;
	private final Optional<ResourceLocation> startJigsawName;

	public UndergroundTempleStructure(Structure.StructureSettings config, Holder<StructureTemplatePool> startPool,
			Optional<ResourceLocation> startJigsawName, int size) {
		super(config);
		this.size = size;
		this.startPool = startPool;
		this.startJigsawName = startJigsawName;
	}

	private static boolean isFeatureChunk(Structure.GenerationContext context) {
		Random random = new Random(context.seed());
		return random.nextDouble() < 0.5;
	}

	@Override
	public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {

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

		Optional<Structure.GenerationStub> structurePiecesGenerator = JigsawPlacement.addPieces(context, this.startPool,
				this.startJigsawName, this.size, blockpos, false, Optional.empty(), 80);

		return structurePiecesGenerator;
	}

	@Override
	public StructureType<?> type() {
		return ModStructures.UNDERGROUND_TEMPLE.get();
	}

}
