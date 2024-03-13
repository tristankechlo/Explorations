package com.tristankechlo.explorations.init;

import com.tristankechlo.explorations.Explorations;
import com.tristankechlo.explorations.worldgen.structures.processors.StoneBrickAgingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;

public class ModStructureProcessor {

    public static IStructureProcessorType<StoneBrickAgingProcessor> STONE_BRICK_AGING_PROCESSOR;

    public static void setupStructureProcessors() {
        STONE_BRICK_AGING_PROCESSOR = Registry.register(Registry.STRUCTURE_PROCESSOR, loc("stone_brick_aging"), () -> StoneBrickAgingProcessor.CODEC);
    }

    private static ResourceLocation loc(String name) {
        return new ResourceLocation(Explorations.MOD_ID, name);
    }

}
