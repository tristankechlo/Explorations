package com.tristankechlo.explorations;

import com.tristankechlo.explorations.init.ModRegistry;
import com.tristankechlo.explorations.init.ModTags;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class FabricExplorations implements ModInitializer {

    private static final ResourceLocation LARGE_MUSHROOM = new ResourceLocation(Explorations.MOD_ID, "large_mushroom");
    private static final ResourceLocation SCARECROW_ACACIA = new ResourceLocation(Explorations.MOD_ID, "scarecrow_acacia");
    private static final ResourceLocation SCARECROW_BIRCH = new ResourceLocation(Explorations.MOD_ID, "scarecrow_birch");
    private static final ResourceLocation SCARECROW_DARK_OAK = new ResourceLocation(Explorations.MOD_ID, "scarecrow_dark_oak");
    private static final ResourceLocation SCARECROW_JUNGLE = new ResourceLocation(Explorations.MOD_ID, "scarecrow_jungle");
    private static final ResourceLocation SCARECROW_OAK = new ResourceLocation(Explorations.MOD_ID, "scarecrow_oak");
    private static final ResourceLocation SCARECROW_SPRUCE = new ResourceLocation(Explorations.MOD_ID, "scarecrow_spruce");
    private static final ResourceLocation SCARECROW_MANGROVE = new ResourceLocation(Explorations.MOD_ID, "scarecrow_mangrove");

    @Override
    public void onInitialize() {
        ModRegistry.loadClass(); // load ModRegistry to register everything

        // add features to biomes
        addFeature(LARGE_MUSHROOM, ModTags.HAS_FEATURE_LARGE_MUSHROOM);
        addFeature(SCARECROW_ACACIA, ModTags.HAS_FEATURE_SCARECROW_ACACIA);
        addFeature(SCARECROW_BIRCH, ModTags.HAS_FEATURE_SCARECROW_BIRCH);
        addFeature(SCARECROW_DARK_OAK, ModTags.HAS_FEATURE_SCARECROW_DARK_OAK);
        addFeature(SCARECROW_JUNGLE, ModTags.HAS_FEATURE_SCARECROW_JUNGLE);
        addFeature(SCARECROW_OAK, ModTags.HAS_FEATURE_SCARECROW_OAK);
        addFeature(SCARECROW_SPRUCE, ModTags.HAS_FEATURE_SCARECROW_SPRUCE);
        addFeature(SCARECROW_MANGROVE, ModTags.HAS_FEATURE_SCARECROW_MANGROVE);
    }

    private static void addFeature(ResourceLocation location, TagKey<Biome> tag) {
        GenerationStep.Decoration step = GenerationStep.Decoration.VEGETAL_DECORATION;
        ResourceKey<PlacedFeature> featureKey = ResourceKey.create(Registries.PLACED_FEATURE, location);
        BiomeModifications.create(location).add(ModificationPhase.ADDITIONS,
                (context) -> context.hasTag(tag),
                (context) -> context.getGenerationSettings().addFeature(step, featureKey));
    }

}
