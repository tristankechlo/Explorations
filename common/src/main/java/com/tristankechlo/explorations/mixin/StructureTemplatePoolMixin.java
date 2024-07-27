package com.tristankechlo.explorations.mixin;

import com.mojang.datafixers.util.Pair;
import com.tristankechlo.explorations.Explorations;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

@Mixin(StructureTemplatePool.class)
public abstract class StructureTemplatePoolMixin {

    @Mutable
    @Final
    @Shadow
    private List<Pair<StructurePoolElement, Integer>> rawTemplates;
    @Shadow
    @Final
    private List<StructurePoolElement> templates;

    private static final Pattern explorations$PATTERN = Pattern.compile("minecraft:village/(?>plains|snowy|savanna|taiga)/houses", Pattern.CASE_INSENSITIVE);
    private static final int explorations$WEIGHT = 1;

    @Inject(method = "<init>(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;Ljava/util/List;)V", at = @At("TAIL"))
    private void explorations$init(ResourceLocation name, ResourceLocation fallback, List<Pair<StructurePoolElement, Integer>> templates, CallbackInfo ci) {
        if (explorations$PATTERN.matcher(name.toString()).find()) {
            explorations$doInject(StructureTemplatePool.Projection.RIGID);
            Explorations.LOGGER.info("1 Added statues to {}", name);
        }
    }

    @Inject(method = "<init>(Lnet/minecraft/resources/ResourceLocation;Lnet/minecraft/resources/ResourceLocation;Ljava/util/List;Lnet/minecraft/world/level/levelgen/structure/pools/StructureTemplatePool$Projection;)V", at = @At("TAIL"))
    private void explorations$init2(ResourceLocation name, ResourceLocation fallback, List<Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>> templates, StructureTemplatePool.Projection placementBehaviour, CallbackInfo ci) {
        if (explorations$PATTERN.matcher(name.toString()).find()) {
            explorations$doInject(placementBehaviour);
            Explorations.LOGGER.info("2 Added statues to {}", name);
        }
    }

    private void explorations$doInject(StructureTemplatePool.Projection behaviour) {
        for (int i = 1; i <= 4; i++) {
            StructurePoolElement jigsawPiece = explorations$makePiece(i, behaviour);
            for (int j = 0; j < explorations$WEIGHT; ++j) {
                this.templates.add(jigsawPiece);
            }
            List<Pair<StructurePoolElement, Integer>> tempList = new ArrayList<>();
            tempList.addAll(this.rawTemplates);
            tempList.add(Pair.of(jigsawPiece, explorations$WEIGHT));
            this.rawTemplates = tempList;
        }
    }

    private StructurePoolElement explorations$makePiece(int i, StructureTemplatePool.Projection behaviour) {
        return StructurePoolElement.single("explorations:statues/statue_" + i).apply(behaviour);
    }

}
