package com.tristankechlo.explorations;

import com.tristankechlo.explorations.init.ModRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Explorations.MOD_ID)
public class Explorations {

    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "explorations";

    public Explorations() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModRegistry.STRUCTURES.register(modEventBus);
        ModRegistry.FEATURES.register(modEventBus);
        ModRegistry.TREE_DECORATORS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

}
