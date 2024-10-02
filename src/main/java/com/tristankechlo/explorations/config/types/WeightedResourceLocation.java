package com.tristankechlo.explorations.config.types;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ResourceLocation;

public class WeightedResourceLocation {

    public static final Codec<WeightedResourceLocation> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    ResourceLocation.CODEC.fieldOf("location").forGetter(WeightedResourceLocation::location),
                    Codec.intRange(1, 150).fieldOf("weight").forGetter(WeightedResourceLocation::weight)
            ).apply(instance, WeightedResourceLocation::new)
    );

    private final ResourceLocation location;
    private final int weight;

    public WeightedResourceLocation(ResourceLocation location, int weight) {
        this.location = location;
        this.weight = weight;
    }

    public ResourceLocation location() {
        return this.location;
    }

    public int weight() {
        return this.weight;
    }

    public String nbtLoc() {
        return this.location().toString();
    }

}
