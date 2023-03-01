package com.tristankechlo.explorations.init;

import com.tristankechlo.explorations.Explorations;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

public final class ModTags {

    public static final TagKey<Biome> HAS_FEATURE_LARGE_MUSHROOM = getTagKey("has_feature/large_mushroom");
    public static final TagKey<Biome> HAS_FEATURE_SCARECROW_ACACIA = getTagKey("has_feature/scarecrow/acacia");
    public static final TagKey<Biome> HAS_FEATURE_SCARECROW_BIRCH = getTagKey("has_feature/scarecrow/birch");
    public static final TagKey<Biome> HAS_FEATURE_SCARECROW_DARK_OAK = getTagKey("has_feature/scarecrow/dark_oak");
    public static final TagKey<Biome> HAS_FEATURE_SCARECROW_JUNGLE = getTagKey("has_feature/scarecrow/jungle");
    public static final TagKey<Biome> HAS_FEATURE_SCARECROW_OAK = getTagKey("has_feature/scarecrow/oak");
    public static final TagKey<Biome> HAS_FEATURE_SCARECROW_SPRUCE = getTagKey("has_feature/scarecrow/spruce");

    public static final TagKey<Block> SCARECROW_SPAWNABLE_ON = getBlockTagKey("scarecrow_spawnable_on");

    private static TagKey<Biome> getTagKey(String id) {
        return TagKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(Explorations.MOD_ID, id));
    }

    private static TagKey<Block> getBlockTagKey(String id) {
        return TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(Explorations.MOD_ID, id));
    }

}
