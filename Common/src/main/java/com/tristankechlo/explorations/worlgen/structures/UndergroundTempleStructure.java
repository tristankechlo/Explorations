package com.tristankechlo.explorations.worlgen.structures;

import com.mojang.serialization.Codec;
import com.tristankechlo.explorations.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.StructureType;

import java.util.Random;

public final class UndergroundTempleStructure extends JigsawStructure {

    public static final Codec<UndergroundTempleStructure> CODEC = JigsawStructure.createCodec(UndergroundTempleStructure::new);

    public UndergroundTempleStructure(StructureSettings config, JigsawStructureSettings settings) {
        super(config, settings);
    }

    @Override
    protected BlockPos generateStartPos(GenerationContext context) {
        int sealevel = context.chunkGenerator().getSeaLevel();
        if (sealevel <= 30) {
            return null;
        }
        WorldgenRandom random = context.random();
        int y = random.nextInt(sealevel - 15);
        return context.chunkPos().getMiddleBlockPosition(y);
    }

    @Override
    protected boolean isFeatureChunk(GenerationContext context) {
        return context.random().nextDouble() < 0.5;
    }

    @Override
    public StructureType<?> type() {
        return ModRegistry.UNDERGROUND_TEMPLE.get();
    }

}
