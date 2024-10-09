package com.tristankechlo.explorations;

import com.tristankechlo.explorations.config.ConfigManager;
import com.tristankechlo.explorations.init.ModRegistry;
import com.tristankechlo.explorations.worldgen.WorldGenHelper;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(Explorations.MOD_ID)
public final class ForgeExplorations {

    public ForgeExplorations() {
        ModRegistry.loadClass(); // load ModRegistry to register everything
        MinecraftForge.EVENT_BUS.addListener(this::addStatuesToVillages);
    }

    public void addStatuesToVillages(final ServerAboutToStartEvent event) {
        ConfigManager.loadAndVerifyConfig();
        WorldGenHelper.addStatuesToVillages(event.getServer());
    }

}
