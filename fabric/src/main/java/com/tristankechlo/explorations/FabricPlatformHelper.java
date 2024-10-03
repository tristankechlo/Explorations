package com.tristankechlo.explorations;

import com.mojang.serialization.Codec;
import com.tristankechlo.explorations.mixin.TreeDecoratorTypeAccessor;
import com.tristankechlo.explorations.platform.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.nio.file.Path;

public final class FabricPlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Fabric";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return FabricLoader.getInstance().isModLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    @Override
    public Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public <P extends TreeDecorator> TreeDecoratorType<P> getTreeDecorator(Codec<P> codec) {
        return TreeDecoratorTypeAccessor.callNew(codec);
    }

}
