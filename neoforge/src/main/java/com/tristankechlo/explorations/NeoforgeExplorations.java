package com.tristankechlo.explorations;

import com.tristankechlo.explorations.init.ModRegistry;
import net.neoforged.fml.common.Mod;

@Mod(Explorations.MOD_ID)
public final class NeoforgeExplorations {

    public NeoforgeExplorations() {
        ModRegistry.loadClass(); // load ModRegistry to register everything
    }

}
