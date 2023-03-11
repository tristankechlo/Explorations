package com.tristankechlo.explorations.worlgen.structures;

import com.mojang.serialization.Codec;
import com.tristankechlo.explorations.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.StructureType;

public final class SlimeCaveStructure extends JigsawStructure {

    public static final Codec<SlimeCaveStructure> CODEC = JigsawStructure.createCodec(SlimeCaveStructure::new);

    public SlimeCaveStructure(StructureSettings config, JigsawStructureSettings settings) {
        super(config, settings);
    }

    @Override
    protected boolean isFeatureChunk(GenerationContext context) {
        return true;
    }

    @Override
    protected BlockPos generateStartPos(GenerationContext context) {
        WorldgenRandom random = context.random();
        int sealevel = context.chunkGenerator().getSeaLevel();
        if (sealevel <= 30) {
            return null;
        }
        int y = random.nextInt(sealevel - 15);
        return context.chunkPos().getMiddleBlockPosition(y);
        //sealevel -= 15;
        //int minY = context.chunkGenerator().getMinY();
        //int y = random.nextInt(sealevel - minY) + minY;
        //return context.chunkPos().getMiddleBlockPosition(y);
    }

    @Override
    public StructureType<?> type() {
        return ModRegistry.SLIME_CAVE_STRUCTURE.get();
    }

}
