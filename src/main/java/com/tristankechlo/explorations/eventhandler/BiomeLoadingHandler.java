package com.tristankechlo.explorations.eventhandler;

import com.tristankechlo.explorations.init.ConfiguredStructures;
import com.tristankechlo.explorations.structures.ForgottenWellStructure;
import com.tristankechlo.explorations.structures.JungleTempleStructure;

import net.minecraft.world.level.biome.Biome.BiomeCategory;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BiomeLoadingHandler {

	@SubscribeEvent(priority = EventPriority.HIGH)
	public void biomeModification(final BiomeLoadingEvent event) {

		// add forgotten well biomes
		if (ForgottenWellStructure.DEFAULT_BIOMES.contains(event.getName().toString())) {
			event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_FORGOTTEN_WELL);
		}

		// add jungle temple biomes
		if (JungleTempleStructure.DEFAULT_BIOMES.contains(event.getName().toString())) {
			event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_JUNGLE_TEMPLE);
		}

		// add undergrounde temple to all overworld biomes
		if (event.getCategory() != BiomeCategory.NETHER && event.getCategory() != BiomeCategory.THEEND) {
			event.getGeneration().getStructures().add(() -> ConfiguredStructures.CONFIGURED_UNDERGROUND_TEMPLE);
		}

	}

}