package com.tristankechlo.explorations.eventhandler;

import com.tristankechlo.explorations.init.ConfiguredFeatures;
import com.tristankechlo.explorations.init.ConfiguredStructures;
import com.tristankechlo.explorations.worldgen.structures.config.JigsawConfig;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.Category;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.GenerationStage.Decoration;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BiomeLoadingHandler {

    private static final Set<String> SCARECROW_ACACIA_BIOMES = make(Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.SHATTERED_SAVANNA, Biomes.SHATTERED_SAVANNA_PLATEAU);
    private static final Set<String> SCARECROW_BIRCH_BIOMES = make(Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.TALL_BIRCH_FOREST, Biomes.TALL_BIRCH_HILLS);
    private static final Set<String> SCARECROW_DARK_OAK_BIOMES = make(Biomes.DARK_FOREST, Biomes.DARK_FOREST_HILLS);
    private static final Set<String> SCARECROW_JUNGLE_BIOMES = make(Biomes.JUNGLE, Biomes.JUNGLE_EDGE, Biomes.JUNGLE_HILLS, Biomes.BAMBOO_JUNGLE, Biomes.BAMBOO_JUNGLE_HILLS, Biomes.MODIFIED_JUNGLE, Biomes.MODIFIED_JUNGLE_EDGE);
    private static final Set<String> SCARECROW_OAK_BIOMES = make(Biomes.FOREST, Biomes.FLOWER_FOREST, Biomes.BIRCH_FOREST, Biomes.DARK_FOREST, Biomes.PLAINS);
    private static final Set<String> SCARECROW_SPRUCE_BIOMES = make(Biomes.TAIGA, Biomes.TAIGA_HILLS, Biomes.TAIGA_MOUNTAINS, Biomes.GIANT_TREE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA, Biomes.GIANT_TREE_TAIGA_HILLS, Biomes.GIANT_SPRUCE_TAIGA_HILLS);
    private static final Set<String> LARGE_MUSHROOM_BIOMES = make(Biomes.DARK_FOREST, Biomes.DARK_FOREST_HILLS);

    private static final Set<Category> OVERWORLD = makeCategories();
    private static List<Tuple<List<String>, StructureFeature<?, ?>>> CONFIG = null;

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void biomeModification(final BiomeLoadingEvent event) {
        ResourceLocation identifier = event.getName();
        if (identifier == null) {
            return;
        }

        addScarecrows(identifier.toString(), event);

        // add large mushroom
        if (LARGE_MUSHROOM_BIOMES.contains(identifier.toString())) {
            event.getGeneration().addFeature(Decoration.VEGETAL_DECORATION, ConfiguredFeatures.CONFIGURED_LARGE_MUSHROOM);
        }

        addStructures(identifier.toString(), event);
    }

    private static void addStructures(String location, BiomeLoadingEvent event) {
        // add undergrounde temple to all overworld biomes
        if (OVERWORLD.contains(event.getCategory())) {
            event.getGeneration().addStructureStart(ConfiguredStructures.CONFIGURED_UNDERGROUND_TEMPLE);
            event.getGeneration().addStructureStart(ConfiguredStructures.CONFIGURED_SLIME_CAVE);
        }

        // register all other structures to their correct biomes
        if (CONFIG == null) {
            CONFIG = makeConfig();
        }
        for (Tuple<List<String>, StructureFeature<?, ?>> tuple : CONFIG) {
            if (tuple.getA().contains(location)) {
                event.getGeneration().addStructureStart(tuple.getB());
            }
        }
    }

    private static void addScarecrows(String location, BiomeLoadingEvent event) {
        if (SCARECROW_ACACIA_BIOMES.contains(location)) {
            event.getGeneration().addFeature(Decoration.VEGETAL_DECORATION, ConfiguredFeatures.CONFIGURED_SCARECROW_ACACIA);
        }
        if (SCARECROW_BIRCH_BIOMES.contains(location)) {
            event.getGeneration().addFeature(Decoration.VEGETAL_DECORATION, ConfiguredFeatures.CONFIGURED_SCARECROW_BIRCH);
        }
        if (SCARECROW_DARK_OAK_BIOMES.contains(location)) {
            event.getGeneration().addFeature(Decoration.VEGETAL_DECORATION, ConfiguredFeatures.CONFIGURED_SCARECROW_DARK_OAK);
        }
        if (SCARECROW_JUNGLE_BIOMES.contains(location)) {
            event.getGeneration().addFeature(Decoration.VEGETAL_DECORATION, ConfiguredFeatures.CONFIGURED_SCARECROW_JUNGLE);
        }
        if (SCARECROW_OAK_BIOMES.contains(location)) {
            event.getGeneration().addFeature(Decoration.VEGETAL_DECORATION, ConfiguredFeatures.CONFIGURED_SCARECROW_OAK);
        }
        if (SCARECROW_SPRUCE_BIOMES.contains(location)) {
            event.getGeneration().addFeature(Decoration.VEGETAL_DECORATION, ConfiguredFeatures.CONFIGURED_SCARECROW_SPRUCE);
        }
    }

    @SafeVarargs
    private static Set<String> make(RegistryKey<Biome>... types) {
        HashSet<String> set = new HashSet<>();
        for (RegistryKey<Biome> biome : types) {
            set.add(biome.location().toString());
        }
        return set;
    }

    private static List<Tuple<List<String>, StructureFeature<?, ?>>> makeConfig() {
        List<Tuple<List<String>, StructureFeature<?, ?>>> config = new ArrayList<>();
        config.add(new Tuple<>(JigsawConfig.DESERT_RUIN.spawnBiomes, ConfiguredStructures.CONFIGURED_DESERT_RUIN));
        config.add(new Tuple<>(JigsawConfig.FORGOTTEN_WELL.spawnBiomes, ConfiguredStructures.CONFIGURED_FORGOTTEN_WELL));
        config.add(new Tuple<>(JigsawConfig.JUNGLE_TEMPLE.spawnBiomes, ConfiguredStructures.CONFIGURED_JUNGLE_TEMPLE));
        config.add(new Tuple<>(JigsawConfig.FLOATING_ISLAND.spawnBiomes, ConfiguredStructures.CONFIGURED_FLOATING_ISLAND));
        config.add(new Tuple<>(JigsawConfig.LARGE_OAK_TREE.spawnBiomes, ConfiguredStructures.CONFIGURED_LARGE_OAK_TREE));
        config.add(new Tuple<>(JigsawConfig.LOGS.spawnBiomes, ConfiguredStructures.CONFIGURED_LOGS));
        config.add(new Tuple<>(JigsawConfig.SHRINE.spawnBiomes, ConfiguredStructures.CONFIGURED_SHRINE));
        config.add(new Tuple<>(JigsawConfig.CAMPSITE.spawnBiomes, ConfiguredStructures.CONFIGURED_CAMPSITE));
        return config;
    }

    private static Set<Category> makeCategories() {
        Set<Category> categories = new HashSet<>();
        categories.add(Category.TAIGA);
        categories.add(Category.EXTREME_HILLS);
        categories.add(Category.JUNGLE);
        categories.add(Category.MESA);
        categories.add(Category.PLAINS);
        categories.add(Category.SAVANNA);
        categories.add(Category.ICY);
        categories.add(Category.BEACH);
        categories.add(Category.FOREST);
        categories.add(Category.OCEAN);
        categories.add(Category.DESERT);
        categories.add(Category.RIVER);
        categories.add(Category.SWAMP);
        categories.add(Category.MUSHROOM);
        return categories;
    }

}
