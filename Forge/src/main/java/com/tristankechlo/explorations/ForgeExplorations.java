package com.tristankechlo.explorations;

import com.tristankechlo.explorations.init.ModRegistry;
import net.minecraftforge.fml.common.Mod;

@Mod(Explorations.MOD_ID)
public final class ForgeExplorations {

    public ForgeExplorations() {
        ModRegistry.loadClass(); // load ModRegistry to register everything
    }

}
