package com.tristankechlo.explorations.worlgen.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tristankechlo.explorations.init.ModRegistry;
import com.tristankechlo.explorations.worlgen.structures.pieces.SlimeCaveStructurePiece;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Map;
import java.util.Optional;

public final class SlimeCaveStructure extends Structure {

    public static final Codec<SlimeCaveStructure> CODEC = RecordCodecBuilder.<SlimeCaveStructure>mapCodec(instance ->
            instance.group(RegistryCodecs.homogeneousList(Registries.BIOME).fieldOf("biomes").forGetter(s -> s.biomes))
                    .apply(instance, SlimeCaveStructure::new)).codec();
    private static final ResourceLocation ID = new ResourceLocation("explorations", "slime_cave");
    private final HolderSet<Biome> biomes;

    public SlimeCaveStructure(HolderSet<Biome> biomes) {
        super(makeSettings(biomes));
        this.biomes = biomes;
    }

    public static StructureSettings makeSettings(HolderSet<Biome> biomes) {
        return new StructureSettings(biomes, Map.of(), Decoration.UNDERGROUND_STRUCTURES, TerrainAdjustment.NONE);
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        WorldgenRandom random = context.random();
        int x = context.chunkPos().getMinBlockX() + random.nextInt(16);
        int z = context.chunkPos().getMinBlockZ() + random.nextInt(16);
        int maxY = context.chunkGenerator().getFirstOccupiedHeight(x, z, Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor(), context.randomState());
        if (maxY <= 30) {
            return Optional.empty();
        }
        maxY -= 15;
        int minY = context.chunkGenerator().getMinY() + 15;
        int y = minY + random.nextInt(maxY - minY);
        BlockPos pos = new BlockPos(x, y, z);
        return Optional.of(new GenerationStub(pos, (builder) -> this.generatePieces(builder, context, pos)));
    }

    private void generatePieces(StructurePiecesBuilder builder, GenerationContext context, BlockPos pos) {
        StructureTemplateManager templateManager = context.structureTemplateManager();
        Rotation rotation = Rotation.getRandom(context.random());
        builder.addPiece(new SlimeCaveStructurePiece(templateManager, ID, pos, rotation));
    }

    @Override
    public StructureType<?> type() {
        return ModRegistry.SLIME_CAVE_STRUCTURE.get();
    }

}
