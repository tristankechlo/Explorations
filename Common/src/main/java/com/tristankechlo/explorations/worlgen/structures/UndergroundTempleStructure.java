package com.tristankechlo.explorations.worlgen.structures;

import com.mojang.serialization.Codec;
import com.tristankechlo.explorations.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.StructureType;

public final class UndergroundTempleStructure extends JigsawStructure {

    public static final Codec<UndergroundTempleStructure> CODEC = JigsawStructure.createCodec(UndergroundTempleStructure::new);

    public UndergroundTempleStructure(StructureSettings config, JigsawStructureSettings settings) {
        super(config, settings);
    }

    @Override
    protected BlockPos generateStartPos(GenerationContext context) {
        WorldgenRandom random = context.random();
        int maxY = context.chunkGenerator().getSeaLevel();
        if (maxY <= 30) {
            return null;
        }
        maxY -= 15;
        int minY = context.chunkGenerator().getMinY() + 15;
        int y = minY + random.nextInt(maxY - minY);
        return context.chunkPos().getMiddleBlockPosition(y);
    }

    @Override
    protected boolean isFeatureChunk(GenerationContext context) {
        return context.random().nextDouble() < 0.6;
    }

    @Override
    public StructureType<?> type() {
        return ModRegistry.UNDERGROUND_TEMPLE.get();
    }

}
