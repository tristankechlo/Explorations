package com.tristankechlo.explorations.worldgen.structures;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;

import java.util.List;
import java.util.Optional;

import static net.minecraft.world.level.levelgen.structure.structures.JigsawStructure.DEFAULT_DIMENSION_PADDING;
import static net.minecraft.world.level.levelgen.structure.structures.JigsawStructure.DEFAULT_LIQUID_SETTINGS;

public abstract class ImprovedJigsawStructure extends Structure {

    protected final JigsawStructureSettings config;

    public ImprovedJigsawStructure(StructureSettings settings, JigsawStructureSettings config) {
        super(settings);
        this.config = config;
    }

    public static <S extends ImprovedJigsawStructure> RecordCodecBuilder<S, JigsawStructureSettings> jigsawSettingsCodec(RecordCodecBuilder.Instance<S> instance) {
        return JigsawStructureSettings.CODEC.forGetter((s) -> s.config);
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

        return JigsawPlacement.addPieces(context, this.config.startPool(), this.config.startJigsawName(),
                this.config.size(), blockpos, false, Optional.empty(), this.config.maxDistanceFromCenter(),
                PoolAliasLookup.create(List.of(), blockpos, context.seed()), DEFAULT_DIMENSION_PADDING, this.config.liquidSettings());
    }

    protected abstract BlockPos generateStartPos(GenerationContext context);

    protected abstract boolean isFeatureChunk(GenerationContext context);

    public record JigsawStructureSettings(
            Holder<StructureTemplatePool> startPool,
            Optional<Identifier> startJigsawName,
            int size,
            JigsawStructure.MaxDistance maxDistanceFromCenter,
            LiquidSettings liquidSettings
    ) {
        public static final MapCodec<JigsawStructureSettings> CODEC = RecordCodecBuilder.mapCodec(instance -> instance
                .group(StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(JigsawStructureSettings::startPool),
                        Identifier.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(JigsawStructureSettings::startJigsawName),
                        Codec.intRange(0, 30).fieldOf("size").orElse(5).forGetter(JigsawStructureSettings::size),
                        JigsawStructure.MaxDistance.CODEC.fieldOf("max_distance_from_center").orElse(new JigsawStructure.MaxDistance(50)).forGetter(JigsawStructureSettings::maxDistanceFromCenter),
                        LiquidSettings.CODEC.fieldOf("liquid_settings").orElse(DEFAULT_LIQUID_SETTINGS).forGetter(JigsawStructureSettings::liquidSettings)
                ).apply(instance, JigsawStructureSettings::new));
    }

}
