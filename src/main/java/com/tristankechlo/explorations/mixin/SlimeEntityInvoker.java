package com.tristankechlo.explorations.mixin;

import net.minecraft.entity.monster.SlimeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SlimeEntity.class)
public interface SlimeEntityInvoker {

    @Invoker("setSize")
    void explorations$setSize(int p_70799_1_, boolean p_70799_2_);

}
