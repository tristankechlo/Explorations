package com.tristankechlo.explorations.mixin;

import com.mojang.serialization.Codec;
import com.tristankechlo.explorations.mixin_util.ChunkgeneratorUtil;
import net.minecraft.world.gen.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ChunkGenerator.class)
public abstract class ChunkgeneratorMixin implements ChunkgeneratorUtil {

    @Shadow
    protected abstract Codec<? extends ChunkGenerator> codec();

    @Override
    public Codec<? extends ChunkGenerator> explorations$getCodec() {
        return this.codec();
    }

}
