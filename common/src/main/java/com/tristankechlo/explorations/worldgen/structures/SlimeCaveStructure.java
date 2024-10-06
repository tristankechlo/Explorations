package com.tristankechlo.explorations.worldgen.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tristankechlo.explorations.init.ModRegistry;
import com.tristankechlo.explorations.worldgen.structures.pieces.SlimeCaveStructurePiece;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistryCodecs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
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
        return new StructureSettings(biomes, Map.of(), Decoration.UNDERGROUND_STRUCTURES, TerrainAdjustment.BURY);
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        WorldgenRandom random = context.random();
        int highestY = context.chunkGenerator().getSeaLevel();
        if (highestY <= 30) {
            return Optional.empty();
        }
        highestY -= 20;
        int lowestY = context.chunkGenerator().getMinY() + 15;
        int range = Math.abs(highestY - lowestY);
        if (range < 10) {
            return Optional.empty();
        }
        int y = lowestY + random.nextInt(range);
        BlockPos pos = context.chunkPos().getWorldPosition().atY(y);
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
