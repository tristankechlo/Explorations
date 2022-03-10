package com.tristankechlo.explorations.init;

import com.tristankechlo.explorations.Explorations;
import com.tristankechlo.explorations.structures.FloatingIslandStructure;
import com.tristankechlo.explorations.structures.UnderGroundTempleStructure;

import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModStructures {

	public static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Explorations.MOD_ID);

	public static final RegistryObject<StructureFeature<?>> UNDERGROUND_TEMPLE = STRUCTURES.register("underground_temple", UnderGroundTempleStructure::new);
	public static final RegistryObject<StructureFeature<?>> FLOATING_ISLAND = STRUCTURES.register("floating_island", FloatingIslandStructure::new);

}
