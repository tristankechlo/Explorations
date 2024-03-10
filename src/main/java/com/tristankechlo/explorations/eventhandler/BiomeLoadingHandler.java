package com.tristankechlo.explorations.eventhandler;

import com.tristankechlo.explorations.init.ConfiguredStructures;
import com.tristankechlo.explorations.worldgen.structures.config.JigsawConfig;
import net.minecraft.world.biome.Biome.Category;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BiomeLoadingHandler {

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void biomeModification(final BiomeLoadingEvent event) {

        // register generation biomes for desert ruins
        if (JigsawConfig.DESERT_RUIN.spawnBiomes.contains(event.getName().toString())) {
            event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_DESERT_RUIN);
        }

        // add forgotten well biomes
        if (JigsawConfig.FORGOTTEN_WELL.spawnBiomes.contains(event.getName().toString())) {
            event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_FORGOTTEN_WELL);
        }

        // add jungle temple biomes
        if (JigsawConfig.JUNGLE_TEMPLE.spawnBiomes.contains(event.getName().toString())) {
            event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_JUNGLE_TEMPLE);
        }

        // add floating island biomes
        if (JigsawConfig.FLOATING_ISLAND.spawnBiomes.contains(event.getName().toString())) {
            event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_FLOATING_ISLAND);
        }

        // add large oak tree biomes
        if (JigsawConfig.LARGE_OAK_TREE.spawnBiomes.contains(event.getName().toString())) {
            event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_LARGE_OAK_TREE);
        }

        // add logs biomes
        if (JigsawConfig.LOGS.spawnBiomes.contains(event.getName().toString())) {
            event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_LOGS);
        }

        // add shrine biomes
        if (JigsawConfig.SHRINE.spawnBiomes.contains(event.getName().toString())) {
            event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_SHRINE);
        }

        // add undergrounde temple to all overworld biomes
        if (event.getCategory() != Category.NETHER && event.getCategory() != Category.THEEND) {
            event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_UNDERGROUND_TEMPLE);
        }

    }

}
