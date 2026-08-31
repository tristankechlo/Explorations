package com.tristankechlo.explorations;

import com.tristankechlo.explorations.config.ConfigManager;
import com.tristankechlo.explorations.init.ModRegistry;
import com.tristankechlo.explorations.init.ModTags;
import com.tristankechlo.explorations.worldgen.WorldGenHelper;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class FabricExplorations implements ModInitializer {

    private static final Identifier LARGE_MUSHROOM = Identifier.fromNamespaceAndPath(Explorations.MOD_ID, "large_mushroom");
    private static final Identifier SCARECROW_ACACIA = Identifier.fromNamespaceAndPath(Explorations.MOD_ID, "scarecrow_acacia");
    private static final Identifier SCARECROW_BIRCH = Identifier.fromNamespaceAndPath(Explorations.MOD_ID, "scarecrow_birch");
    private static final Identifier SCARECROW_DARK_OAK = Identifier.fromNamespaceAndPath(Explorations.MOD_ID, "scarecrow_dark_oak");
    private static final Identifier SCARECROW_JUNGLE = Identifier.fromNamespaceAndPath(Explorations.MOD_ID, "scarecrow_jungle");
    private static final Identifier SCARECROW_OAK = Identifier.fromNamespaceAndPath(Explorations.MOD_ID, "scarecrow_oak");
    private static final Identifier SCARECROW_SPRUCE = Identifier.fromNamespaceAndPath(Explorations.MOD_ID, "scarecrow_spruce");
    private static final Identifier SCARECROW_MANGROVE = Identifier.fromNamespaceAndPath(Explorations.MOD_ID, "scarecrow_mangrove");
    private static final Identifier SCARECROW_CHERRY = Identifier.fromNamespaceAndPath(Explorations.MOD_ID, "scarecrow_cherry");
    private static final Identifier SCARECROW_BAMBOO = Identifier.fromNamespaceAndPath(Explorations.MOD_ID, "scarecrow_bamboo");

    @Override
    public void onInitialize() {
        ModRegistry.loadClass(); // load ModRegistry to register everything

        ServerLifecycleEvents.SERVER_STARTING.register((server) -> {
            ConfigManager.loadAndVerifyConfig();
            WorldGenHelper.addStatuesToVillages(server);
        });

        // add features to biomes
        addFeature(LARGE_MUSHROOM, ModTags.HAS_FEATURE_LARGE_MUSHROOM);
        addFeature(SCARECROW_ACACIA, ModTags.HAS_FEATURE_SCARECROW_ACACIA);
        addFeature(SCARECROW_BIRCH, ModTags.HAS_FEATURE_SCARECROW_BIRCH);
        addFeature(SCARECROW_DARK_OAK, ModTags.HAS_FEATURE_SCARECROW_DARK_OAK);
        addFeature(SCARECROW_JUNGLE, ModTags.HAS_FEATURE_SCARECROW_JUNGLE);
        addFeature(SCARECROW_OAK, ModTags.HAS_FEATURE_SCARECROW_OAK);
        addFeature(SCARECROW_SPRUCE, ModTags.HAS_FEATURE_SCARECROW_SPRUCE);
        addFeature(SCARECROW_MANGROVE, ModTags.HAS_FEATURE_SCARECROW_MANGROVE);
        addFeature(SCARECROW_CHERRY, ModTags.HAS_FEATURE_SCARECROW_CHERRY);
        addFeature(SCARECROW_BAMBOO, ModTags.HAS_FEATURE_SCARECROW_BAMBOO);
    }

    private static void addFeature(Identifier location, TagKey<Biome> tag) {
        BiomeModifications.addFeature(
                BiomeSelectors.tag(tag),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ResourceKey.create(Registries.PLACED_FEATURE, location)
        );
    }

}
