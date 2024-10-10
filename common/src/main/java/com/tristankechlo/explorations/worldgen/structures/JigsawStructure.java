package com.tristankechlo.explorations.worldgen.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import static net.minecraft.world.level.levelgen.structure.structures.JigsawStructure.DEFAULT_DIMENSION_PADDING;
import static net.minecraft.world.level.levelgen.structure.structures.JigsawStructure.DEFAULT_LIQUID_SETTINGS;

public abstract class JigsawStructure extends Structure {

    protected final JigsawStructureSettings settings;

    public JigsawStructure(StructureSettings config, JigsawStructureSettings settings) {
        super(config);
        this.settings = settings;
    }

    public static <S extends JigsawStructure> Codec<S> createCodec(BiFunction<StructureSettings, JigsawStructureSettings, S> factory) {
        return RecordCodecBuilder.<S>mapCodec(instance -> instance
                .group(Structure.settingsCodec(instance), jigsawSettingsCodec(instance))
                .apply(instance, factory)).codec();
    }

    public static <S extends JigsawStructure> RecordCodecBuilder<S, JigsawStructureSettings> jigsawSettingsCodec(RecordCodecBuilder.Instance<S> instance) {
        return JigsawStructureSettings.CODEC.forGetter((s) -> s.settings);
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        // skip generation when the chunk is not a feature chunk
        if (!this.isFeatureChunk(context)) {
            return Optional.empty();
        }
        BlockPos blockpos = this.generateStartPos(context);
        if (blockpos == null) {
            return Optional.empty();
        }

        return JigsawPlacement.addPieces(context, this.settings.startPool(), this.settings.startJigsawName(),
                this.settings.size(), blockpos, false, Optional.empty(), this.settings.maxDistanceFromCenter(),
                PoolAliasLookup.create(List.of(), blockpos, context.seed()), DEFAULT_DIMENSION_PADDING, DEFAULT_LIQUID_SETTINGS);
    }

    protected abstract BlockPos generateStartPos(GenerationContext context);

    protected abstract boolean isFeatureChunk(GenerationContext context);

    public record JigsawStructureSettings(Holder<StructureTemplatePool> startPool, Optional<ResourceLocation> startJigsawName, int size, int maxDistanceFromCenter) {
        public static final MapCodec<JigsawStructureSettings> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
                .group(StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(JigsawStructureSettings::startPool),
                        ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(JigsawStructureSettings::startJigsawName),
                        Codec.intRange(0, 30).fieldOf("size").orElse(5).forGetter(JigsawStructureSettings::size),
                        Codec.intRange(0, 30).fieldOf("max_distance_from_center").orElse(50).forGetter(JigsawStructureSettings::maxDistanceFromCenter))
                .apply(instance, JigsawStructureSettings::new));
    }

}
