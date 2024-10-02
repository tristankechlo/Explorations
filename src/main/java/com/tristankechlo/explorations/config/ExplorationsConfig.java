package com.tristankechlo.explorations.config;

import com.google.gson.JsonElement;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tristankechlo.explorations.Explorations;
import com.tristankechlo.explorations.config.types.VillageType;
import com.tristankechlo.explorations.config.types.WeightedResourceLocation;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExplorationsConfig {

    public static final Codec<ExplorationsConfig> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    Codec.unboundedMap(VillageType.CODEC, WeightedResourceLocation.CODEC.listOf()).fieldOf("statues").forGetter(ExplorationsConfig::statues)
            ).apply(instance, ExplorationsConfig::new)
    );

    public static final ExplorationsConfig DEFAULT = new ExplorationsConfig(makeStatues());
    private static ExplorationsConfig INSTANCE = DEFAULT;
    private final Map<VillageType, List<WeightedResourceLocation>> statues;

    private ExplorationsConfig(Map<VillageType, List<WeightedResourceLocation>> statues) {
        this.statues = statues;
    }

    public Map<VillageType, List<WeightedResourceLocation>> statues() {
        return this.statues;
    }

    public static ExplorationsConfig get() {
        return INSTANCE;
    }

    public static void setToDefault() {
        INSTANCE = ExplorationsConfig.DEFAULT;
    }

    public static JsonElement toJson() {
        DataResult<JsonElement> result = ExplorationsConfig.CODEC.encodeStart(JsonOps.INSTANCE, INSTANCE);
        return result.resultOrPartial((string) -> {
            Explorations.LOGGER.error("An error occurred while attempting to serialize the config.");
            Explorations.LOGGER.error("==> {}", string);
        }).get();
    }

    public static void fromJson(JsonElement json) {
        DataResult<ExplorationsConfig> result = ExplorationsConfig.CODEC.parse(JsonOps.INSTANCE, json);
        INSTANCE = result.resultOrPartial((string) -> {
            Explorations.LOGGER.error("An error occurred while attempting to deserialize the config.");
            Explorations.LOGGER.error("==> {}", string);
        }).get();
    }

    private static Map<VillageType, List<WeightedResourceLocation>> makeStatues() {
        Map<VillageType, List<WeightedResourceLocation>> m = new HashMap<>();
        for (VillageType village : VillageType.NO_DESERT) {
            List<WeightedResourceLocation> l = new ArrayList<>();
            for (int i = 1; i <= 4; i++) {
                l.add(new WeightedResourceLocation(statue(i), village.getDefaultWeight()));
            }
            m.put(village, l);
        }
        return m;
    }

    private static ResourceLocation statue(int i) {
        return new ResourceLocation(Explorations.MOD_ID, "statues/statue_" + i);
    }

}
