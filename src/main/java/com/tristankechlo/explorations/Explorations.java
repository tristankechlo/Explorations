package com.tristankechlo.explorations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.tristankechlo.explorations.init.ModFeatures;
import com.tristankechlo.explorations.init.ModStructures;
import com.tristankechlo.explorations.init.ModTreeDecorators;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Explorations.MOD_ID)
public class Explorations {

	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "explorations";

	public Explorations() {
		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModStructures.STRUCTURES.register(modEventBus);
		ModFeatures.FEATURES.register(modEventBus);
		ModTreeDecorators.TREE_DECORATORS.register(modEventBus);

		MinecraftForge.EVENT_BUS.register(this);
	}

}
