package com.tristankechlo.explorations.mixin;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(JigsawPattern.class)
public interface JigsawPatternAccessor {

    @Accessor("templates")
    List<JigsawPiece> getTemplates();

    @Accessor("rawTemplates")
    List<Pair<JigsawPiece, Integer>> getRawTemplates();

    @Mutable
    @Accessor("rawTemplates")
    void setRawTemplates(List<Pair<JigsawPiece, Integer>> rawTemplates);

}
