package com.tristankechlo.explorations.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(SlimeEntity.class)
public class SlimeEntityMixin {

    @Inject(method = "checkSlimeSpawnRules", at = @At(value = "HEAD"), cancellable = true)
    private static void explorations$checkSpawnRules(EntityType<SlimeEntity> type, IWorld level, SpawnReason reason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
        if (level.getDifficulty() != Difficulty.PEACEFUL && reason == SpawnReason.SPAWNER) {
            cir.setReturnValue(MobEntity.checkMobSpawnRules(type, level, reason, pos, random));
        }
    }

}
