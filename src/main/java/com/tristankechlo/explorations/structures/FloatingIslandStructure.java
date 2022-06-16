package com.tristankechlo.explorations.structures;

import java.util.Optional;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tristankechlo.explorations.init.ModStructures;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public class FloatingIslandStructure extends Structure {

	public static final Codec<FloatingIslandStructure> CODEC = RecordCodecBuilder.<FloatingIslandStructure>mapCodec(
			instance -> instance.group(FloatingIslandStructure.settingsCodec(instance),
					StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
					ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name")
							.forGetter(structure -> structure.startJigsawName),
					Codec.intRange(0, 30).fieldOf("size").forGetter(structure -> structure.size),
					HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
					Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap")
							.forGetter(structure -> structure.startToHeightmap))
					.apply(instance, FloatingIslandStructure::new))
			.codec();

	private final int size;
	private final HeightProvider startHeight;
	private final Holder<StructureTemplatePool> startPool;
	private final Optional<ResourceLocation> startJigsawName;
	private final Optional<Heightmap.Types> startToHeightmap;

	public FloatingIslandStructure(Structure.StructureSettings config, Holder<StructureTemplatePool> startPool,
			Optional<ResourceLocation> startJigsawName, int size, HeightProvider startHeight,
			Optional<Heightmap.Types> projectStartToHeightmap) {
		super(config);
		this.size = size;
		this.startHeight = startHeight;
		this.startPool = startPool;
		this.startJigsawName = startJigsawName;
		this.startToHeightmap = projectStartToHeightmap;
	}

	@Override
	public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
		int startY = this.startHeight.sample(context.random(),
				new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));

		ChunkPos chunkPos = context.chunkPos();
		BlockPos blockPos = new BlockPos(chunkPos.getMiddleBlockX(), startY, chunkPos.getMiddleBlockZ());

		Optional<Structure.GenerationStub> structurePiecesGenerator = JigsawPlacement.addPieces(context, this.startPool,
				this.startJigsawName, this.size, blockPos, false, this.startToHeightmap, 80);

		return structurePiecesGenerator;
	}

	@Override
	public StructureType<?> type() {
		return ModStructures.FLOATING_ISLAND.get();
	}
}
