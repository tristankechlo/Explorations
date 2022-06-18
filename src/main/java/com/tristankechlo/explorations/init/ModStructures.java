package com.tristankechlo.explorations.init;

import com.tristankechlo.explorations.Explorations;
import com.tristankechlo.explorations.structures.UndergroundTempleStructure;

import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModStructures {

	public static final DeferredRegister<StructureType<?>> STRUCTURES = DeferredRegister.create(Registry.STRUCTURE_TYPE_REGISTRY, Explorations.MOD_ID);

	public static final RegistryObject<StructureType<UndergroundTempleStructure>> UNDERGROUND_TEMPLE = STRUCTURES.register("underground_temple", () -> () -> UndergroundTempleStructure.CODEC);

}
