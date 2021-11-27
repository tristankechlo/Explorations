package com.tristankechlo.explorations;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod(Explorations.MOD_ID)
public class Explorations {

	public static final Logger LOGGER = LogManager.getLogger();
	public static final String MOD_ID = "explorations";

	public Explorations() {

		// add registers

		MinecraftForge.EVENT_BUS.register(this);
	}

}
