package com.tristankechlo.explorations.config.types;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

public record WeightedResourceLocation(ResourceLocation location, Integer weight) {

    public static final Codec<WeightedResourceLocation> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    ResourceLocation.CODEC.fieldOf("location").forGetter(WeightedResourceLocation::location),
                    Codec.intRange(1, Integer.MAX_VALUE).fieldOf("weight").forGetter(WeightedResourceLocation::weight)
            ).apply(instance, WeightedResourceLocation::new)
    );

    public String nbtLoc() {
        return this.location().toString();
    }

}
