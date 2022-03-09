package com.tristankechlo.explorations.init;

import com.tristankechlo.explorations.Explorations;
import com.tristankechlo.explorations.structures.ForgottenWellStructure;
import com.tristankechlo.explorations.structures.JungleTempleStructure;
import com.tristankechlo.explorations.structures.UnderGroundTempleStructure;

import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModStructures {

	public static final DeferredRegister<StructureFeature<?>> STRUCTURES = DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, Explorations.MOD_ID);

	public static final RegistryObject<StructureFeature<?>> FORGOTTEN_WELL = STRUCTURES.register("forgotten_well", ForgottenWellStructure::new);
	public static final RegistryObject<StructureFeature<?>> JUNGLE_TEMPLE = STRUCTURES.register("jungle_temple", JungleTempleStructure::new);
	public static final RegistryObject<StructureFeature<?>> UNDERGROUND_TEMPLE = STRUCTURES .register("underground_temple", UnderGroundTempleStructure::new);

}
