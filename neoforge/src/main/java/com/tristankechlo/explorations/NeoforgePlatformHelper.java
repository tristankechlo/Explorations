package com.tristankechlo.explorations;

import com.mojang.serialization.MapCodec;
import com.tristankechlo.explorations.mixin.TreeDecoratorTypeAccessor;
import com.tristankechlo.explorations.platform.IPlatformHelper;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

public final class NeoforgePlatformHelper implements IPlatformHelper {

    @Override
    public String getPlatformName() {
        return "Neoforge";
    }

    @Override
    public boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    @Override
    public boolean isDevelopmentEnvironment() {
        return !FMLLoader.isProduction();
    }

    @Override
    public Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    @Override
    public <P extends TreeDecorator> TreeDecoratorType<P> getTreeDecorator(MapCodec<P> codec) {
        return TreeDecoratorTypeAccessor.callNew(codec);
    }

}
