package com.tristankechlo.explorations.mixin;

import com.mojang.datafixers.util.Pair;
import com.tristankechlo.explorations.Explorations;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

@Mixin(JigsawPattern.class)
public abstract class JigsawPatternMixin {

    @Shadow
    public List<Pair<JigsawPiece, Integer>> rawTemplates;
    @Shadow
    @Final
    private List<JigsawPiece> templates;

    private static final Pattern explorations$PATTERN = Pattern.compile("minecraft:village/(?>plains|snowy|savanna|taiga)/houses", Pattern.CASE_INSENSITIVE);
    private static final int explorations$WEIGHT = 1;

    @Inject(method = "<init>(Lnet/minecraft/util/ResourceLocation;Lnet/minecraft/util/ResourceLocation;Ljava/util/List;)V", at = @At("TAIL"))
    private void explorations$init(ResourceLocation name, ResourceLocation fallback, List<Pair<JigsawPiece, Integer>> templates,
                                   CallbackInfo ci) {
        if (explorations$PATTERN.matcher(name.toString()).find()) {
            explorations$doInject(JigsawPattern.PlacementBehaviour.RIGID);
            Explorations.LOGGER.info("1 Added statues to {}", name);
        }
    }

    @Inject(method = "<init>(Lnet/minecraft/util/ResourceLocation;Lnet/minecraft/util/ResourceLocation;Ljava/util/List;Lnet/minecraft/world/gen/feature/jigsaw/JigsawPattern$PlacementBehaviour;)V", at = @At("TAIL"))
    private void explorations$init2(ResourceLocation name, ResourceLocation fallback, List<Pair<Function<JigsawPattern.PlacementBehaviour, ? extends JigsawPiece>, Integer>> templates,
                                    JigsawPattern.PlacementBehaviour placementBehaviour, CallbackInfo ci) {
        if (explorations$PATTERN.matcher(name.toString()).find()) {
            explorations$doInject(placementBehaviour);
            Explorations.LOGGER.info("2 Added statues to {}", name);
        }
    }

    private void explorations$doInject(JigsawPattern.PlacementBehaviour behaviour) {
        for (int i = 1; i <= 4; i++) {
            JigsawPiece jigsawPiece = explorations$makePiece(i, behaviour);
            for (int j = 0; j < explorations$WEIGHT; ++j) {
                this.templates.add(jigsawPiece);
            }
            List<Pair<JigsawPiece, Integer>> tempList = new ArrayList<>();
            tempList.addAll(this.rawTemplates);
            tempList.add(Pair.of(jigsawPiece, explorations$WEIGHT));
            this.rawTemplates = tempList;
        }
    }

    private JigsawPiece explorations$makePiece(int i, JigsawPattern.PlacementBehaviour behaviour) {
        return JigsawPiece.single("explorations:statues/statue_" + i).apply(behaviour);
    }

}
