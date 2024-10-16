package com.tristankechlo.explorations.platform;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

import java.nio.file.Path;
import java.util.function.Supplier;

public interface IPlatformHelper {

    String getPlatformName();

    boolean isModLoaded(String modId);

    boolean isDevelopmentEnvironment();

    Path getConfigDirectory();

    <P extends TreeDecorator> TreeDecoratorType<P> getTreeDecorator(MapCodec<P> codec);

    default <P extends TreeDecorator> Supplier<TreeDecoratorType<P>> getTreeDecoratorType(MapCodec<P> codec) {
        return () -> this.getTreeDecorator(codec);
    }


}
