package com.tristankechlo.explorations.config.types;

import com.mojang.serialization.Codec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.StringRepresentable;

public enum VillageType implements StringRepresentable {

    DESERT("desert", 2),
    PLAINS("plains", 2),
    SAVANNA("savanna", 2),
    SNOWY("snowy", 3),
    TAIGA("taiga", 4);

    private final String name;
    private final int defaultWeight;
    private final Identifier location;
    public static final Codec<VillageType> CODEC = StringRepresentable.fromEnum(VillageType::values);
    public static final VillageType[] NO_DESERT = new VillageType[]{PLAINS, SAVANNA, SNOWY, TAIGA};

    VillageType(String name, int defaultWeight) {
        this.name = name;
        this.defaultWeight = defaultWeight;
        this.location = Identifier.withDefaultNamespace("village/" + name + "/houses");
    }

    public Identifier getLocation() {
        return this.location;
    }

    public int getDefaultWeight() {
        return defaultWeight;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

}
