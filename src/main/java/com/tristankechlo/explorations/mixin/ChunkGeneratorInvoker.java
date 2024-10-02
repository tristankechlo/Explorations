package com.tristankechlo.explorations.mixin;

import com.mojang.serialization.Codec;
import net.minecraft.world.gen.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/* get access to the codec of the chunk generator */
@Mixin(ChunkGenerator.class)
public interface ChunkGeneratorInvoker {

    @Invoker("codec")
    Codec<? extends ChunkGenerator> getCodec();

}
