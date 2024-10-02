package com.tristankechlo.explorations.config.types;

import com.mojang.serialization.Codec;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

public enum VillageType implements IStringSerializable {

    DESERT("desert", 2),
    PLAINS("plains", 2),
    SAVANNA("savanna", 2),
    SNOWY("snowy", 4),
    TAIGA("taiga", 5);

    private final String name;
    private final int defaultWeight;
    private final ResourceLocation location;
    private static final Map<String, VillageType> BY_NAME = Arrays.stream(VillageType.values()).collect(Collectors.toMap(VillageType::getSerializedName, ($$0) -> $$0));
    public static final Codec<VillageType> CODEC = IStringSerializable.fromEnum(VillageType::values, VillageType::byName);
    public static final VillageType[] NO_DESERT = new VillageType[]{PLAINS, SAVANNA, SNOWY, TAIGA};

    VillageType(String name, int defaultWeight) {
        this.name = name;
        this.defaultWeight = defaultWeight;
        this.location = new ResourceLocation("minecraft:village/" + name + "/houses");
    }

    public ResourceLocation getLocation() {
        return this.location;
    }

    public int getDefaultWeight() {
        return defaultWeight;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public static VillageType byName(String search) {
        return search == null ? null : BY_NAME.get(search.toLowerCase(Locale.ROOT));
    }

}
