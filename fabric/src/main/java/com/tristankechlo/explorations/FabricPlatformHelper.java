package com.tristankechlo.explorations;

import com.google.auto.service.AutoService;
import com.mojang.serialization.MapCodec;
import com.tristankechlo.explorations.mixin.TreeDecoratorTypeAccessor;
import com.tristankechlo.explorations.platform.IPlatformHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.nio.file.Path;

@AutoService(IPlatformHelper.class)
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
    public <P extends TreeDecorator> TreeDecoratorType<P> getTreeDecorator(MapCodec<P> codec) {
        return TreeDecoratorTypeAccessor.callNew(codec);
    }

}
