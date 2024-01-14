package com.tristankechlo.explorations.mixin;

import com.mojang.serialization.Codec;
import com.tristankechlo.explorations.mixin_util.ChunkGeneratorAddon;
import net.minecraft.world.gen.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

/* get access to the codec of the chunk generator */
@Mixin(ChunkGenerator.class)
public abstract class ChunkGeneratorAccessor implements ChunkGeneratorAddon {

    @Shadow
    protected abstract Codec<? extends ChunkGenerator> codec();

    @Override
    public Codec<? extends ChunkGenerator> explorations$getCodec() {
        return this.codec();
    }

}
