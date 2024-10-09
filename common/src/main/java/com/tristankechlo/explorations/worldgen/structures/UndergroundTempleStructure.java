package com.tristankechlo.explorations.worldgen.structures;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tristankechlo.explorations.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;

public final class UndergroundTempleStructure extends JigsawStructure {

    public static final MapCodec<UndergroundTempleStructure> CODEC = RecordCodecBuilder.mapCodec(
            (instance) -> instance.group(Structure.settingsCodec(instance), jigsawSettingsCodec(instance))
                    .apply(instance, UndergroundTempleStructure::new));

    public UndergroundTempleStructure(StructureSettings config, JigsawStructureSettings settings) {
        super(config, settings);
    }

    @Override
    protected BlockPos generateStartPos(GenerationContext context) {
        WorldgenRandom random = context.random();
        int highestY = context.chunkGenerator().getSeaLevel();
        if (highestY <= 30) {
            return null;
        }
        highestY -= 15;
        int lowestY = context.chunkGenerator().getMinY() + 15;
        int range = Math.abs(highestY - lowestY);
        if (range < 10) {
            return null;
        }
        int y = lowestY + random.nextInt(range);
        return context.chunkPos().getMiddleBlockPosition(y);
    }

    @Override
    protected boolean isFeatureChunk(GenerationContext context) {
        return true;
    }

    @Override
    public StructureType<?> type() {
        return ModRegistry.UNDERGROUND_TEMPLE.get();
    }

}
