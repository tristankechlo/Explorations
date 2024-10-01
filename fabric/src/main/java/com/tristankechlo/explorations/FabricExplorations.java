package com.tristankechlo.explorations;

import com.tristankechlo.explorations.init.ModRegistry;
import com.tristankechlo.explorations.init.ModTags;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class FabricExplorations implements ModInitializer {

    @Override
    public void onInitialize() {
        ModRegistry.loadClass(); // load ModRegistry to register everything

        // add features to biomes
        addFeature(ModRegistry.LARGE_MUSHROOM_PLACED.get(), ModTags.HAS_FEATURE_LARGE_MUSHROOM);
        addFeature(ModRegistry.SCARECROW_ACACIA_PLACED.get(), ModTags.HAS_FEATURE_SCARECROW_ACACIA);
        addFeature(ModRegistry.SCARECROW_BIRCH_PLACED.get(), ModTags.HAS_FEATURE_SCARECROW_BIRCH);
        addFeature(ModRegistry.SCARECROW_DARK_OAK_PLACED.get(), ModTags.HAS_FEATURE_SCARECROW_DARK_OAK);
        addFeature(ModRegistry.SCARECROW_JUNGLE_PLACED.get(), ModTags.HAS_FEATURE_SCARECROW_JUNGLE);
        addFeature(ModRegistry.SCARECROW_OAK_PLACED.get(), ModTags.HAS_FEATURE_SCARECROW_OAK);
        addFeature(ModRegistry.SCARECROW_SPRUCE_PLACED.get(), ModTags.HAS_FEATURE_SCARECROW_SPRUCE);

        ServerLifecycleEvents.SERVER_STARTING.register(WorldGenHelper::addStatuesToVillage);
    }

    private static void addFeature(PlacedFeature feature, TagKey<Biome> tag) {
        GenerationStep.Decoration step = GenerationStep.Decoration.VEGETAL_DECORATION;
        ResourceLocation location = BuiltinRegistries.PLACED_FEATURE.getKey(feature);
        BiomeModifications.create(location).add(ModificationPhase.ADDITIONS,
                (context) -> context.hasTag(tag),
                (context) -> context.getGenerationSettings().addBuiltInFeature(step, feature));
    }

}
