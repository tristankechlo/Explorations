package com.tristankechlo.explorations.worldgen.structures;

import com.tristankechlo.explorations.worldgen.structures.pieces.SlimeCaveStructurePiece;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.PostPlacementProcessor;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;

import java.util.Optional;
import java.util.Random;

public final class SlimeCaveStructure extends StructureFeature<NoneFeatureConfiguration> {

    private static final ResourceLocation ID = new ResourceLocation("explorations", "slime_cave");

    public SlimeCaveStructure() {
        super(NoneFeatureConfiguration.CODEC, SlimeCaveStructure::createPiecesGenerator, PostPlacementProcessor.NONE);
    }

    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.UNDERGROUND_STRUCTURES;
    }

    public static Optional<PieceGenerator<NoneFeatureConfiguration>> createPiecesGenerator(PieceGeneratorSupplier.Context<NoneFeatureConfiguration> context) {
        Random random = new Random(context.seed());
        int highestY = context.chunkGenerator().getSeaLevel();
        if (highestY <= 30) {
            return Optional.empty();
        }
        highestY -= 20;
        int lowestY = context.chunkGenerator().getMinY() + 5;
        int range = Math.abs(highestY - lowestY);
        if (range < 10) {
            return Optional.empty();
        }
        int y = lowestY + random.nextInt(range);
        BlockPos pos = context.chunkPos().getWorldPosition().atY(y);
        return Optional.of((builder, context2) -> generatePieces(builder, context2, pos));
    }

    private static void generatePieces(StructurePiecesBuilder builder, PieceGenerator.Context<NoneFeatureConfiguration> context, BlockPos pos) {
        StructureManager templateManager = context.structureManager();
        Random random = new Random(context.seed());
        Rotation rotation = Rotation.getRandom(random);
        builder.addPiece(new SlimeCaveStructurePiece(templateManager, ID, pos, rotation));
    }

}
