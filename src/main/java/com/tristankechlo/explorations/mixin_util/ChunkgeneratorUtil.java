package com.tristankechlo.explorations.mixin_util;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.ChunkGenerator;

public interface ChunkgeneratorUtil {

    Codec<? extends ChunkGenerator> explorations$getCodec();

}
