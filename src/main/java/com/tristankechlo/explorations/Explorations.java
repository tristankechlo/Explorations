package com.tristankechlo.explorations;

import com.tristankechlo.explorations.init.ModStructureProcessor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tristankechlo.explorations.eventhandler.BiomeLoadingHandler;
import com.tristankechlo.explorations.eventhandler.WorldLoadingHandler;
import com.tristankechlo.explorations.init.ConfiguredStructures;
import com.tristankechlo.explorations.init.ModStructures;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Explorations.MOD_ID)
public class Explorations {

	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "explorations";

	public Explorations() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		modEventBus.addListener(this::commonSetup);
		ModStructures.STRUCTURES.register(modEventBus);

		MinecraftForge.EVENT_BUS.register(new BiomeLoadingHandler());
		MinecraftForge.EVENT_BUS.register(new WorldLoadingHandler());

		MinecraftForge.EVENT_BUS.register(this);
	}

	private void commonSetup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			ModStructureProcessor.setupStructureProcessors();
			ModStructures.setupStructures();
			ConfiguredStructures.registerConfiguredStructures();
		});
	}

}
