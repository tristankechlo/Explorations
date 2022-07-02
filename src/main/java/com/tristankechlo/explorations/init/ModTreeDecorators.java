package com.tristankechlo.explorations.init;

import com.tristankechlo.explorations.Explorations;
import com.tristankechlo.explorations.treedecorators.CaveVineDecorator;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModTreeDecorators {

	public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(Registry.TREE_DECORATOR_TYPE_REGISTRY, Explorations.MOD_ID);

	public static final RegistryObject<TreeDecoratorType<CaveVineDecorator>> CAVE_VINES = TREE_DECORATORS.register("cave_vines", () -> new TreeDecoratorType<>(CaveVineDecorator.CODEC));

}
