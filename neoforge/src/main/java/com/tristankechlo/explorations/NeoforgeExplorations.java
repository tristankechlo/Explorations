package com.tristankechlo.explorations;

import com.tristankechlo.explorations.config.ConfigManager;
import com.tristankechlo.explorations.init.ModRegistry;
import com.tristankechlo.explorations.worldgen.WorldGenHelper;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerAboutToStartEvent;

@Mod(Explorations.MOD_ID)
public final class NeoforgeExplorations {

    public NeoforgeExplorations() {
        ModRegistry.loadClass(); // load ModRegistry to register everything
        NeoForge.EVENT_BUS.addListener(this::addStatuesToVillages);
    }

    public void addStatuesToVillages(final ServerAboutToStartEvent event) {
        ConfigManager.loadAndVerifyConfig();
        WorldGenHelper.addStatuesToVillages(event.getServer());
    }

}
