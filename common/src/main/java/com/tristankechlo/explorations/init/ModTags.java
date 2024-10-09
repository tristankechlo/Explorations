package com.tristankechlo.explorations.init;

import com.tristankechlo.explorations.Explorations;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public final class ModTags {

    public static final TagKey<Biome> HAS_FEATURE_LARGE_MUSHROOM = getTagKey("has_feature/large_mushroom");
    public static final TagKey<Biome> HAS_FEATURE_SCARECROW_ACACIA = getTagKey("has_feature/scarecrow/acacia");
    public static final TagKey<Biome> HAS_FEATURE_SCARECROW_BIRCH = getTagKey("has_feature/scarecrow/birch");
    public static final TagKey<Biome> HAS_FEATURE_SCARECROW_DARK_OAK = getTagKey("has_feature/scarecrow/dark_oak");
    public static final TagKey<Biome> HAS_FEATURE_SCARECROW_JUNGLE = getTagKey("has_feature/scarecrow/jungle");
    public static final TagKey<Biome> HAS_FEATURE_SCARECROW_OAK = getTagKey("has_feature/scarecrow/oak");
    public static final TagKey<Biome> HAS_FEATURE_SCARECROW_SPRUCE = getTagKey("has_feature/scarecrow/spruce");
    public static final TagKey<Biome> HAS_FEATURE_SCARECROW_MANGROVE = getTagKey("has_feature/scarecrow/mangrove");
    public static final TagKey<Biome> HAS_FEATURE_SCARECROW_CHERRY = getTagKey("has_feature/scarecrow/cherry");
    public static final TagKey<Biome> HAS_FEATURE_SCARECROW_BAMBOO = getTagKey("has_feature/scarecrow/bamboo");

    private static TagKey<Biome> getTagKey(String id) {
        return TagKey.create(Registries.BIOME, new ResourceLocation(Explorations.MOD_ID, id));
    }

}
