package com.tristankechlo.explorations.worldgen;

import com.mojang.datafixers.util.Pair;
import com.tristankechlo.explorations.Explorations;
import com.tristankechlo.explorations.mixin.StructureTemplatePoolMixin;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://gist.github.com/TelepathicGrunt/4fdbc445ebcbcbeb43ac748f4b18f342">TelepathicGrunt/addNewBuildingsVillages.java</a>
 */
public final class WorldGenHelper {

    private static final ResourceKey<StructureProcessorList> EMPTY_PROCESSOR_LIST_KEY = ResourceKey.create(Registry.PROCESSOR_LIST_REGISTRY, new ResourceLocation("minecraft:empty"));

    public static void addStatuesToVillages(final MinecraftServer event) {
        Registry<StructureTemplatePool> templatePoolReg = event.registryAccess().registry(Registry.TEMPLATE_POOL_REGISTRY).orElseThrow();
        Registry<StructureProcessorList> processorListReg = event.registryAccess().registry(Registry.PROCESSOR_LIST_REGISTRY).orElseThrow();

        for (int i = 1; i <= 4; i++) {
            addBuildingToPool(templatePoolReg, processorListReg, getHouse("plains"), statue(i), 2);
            addBuildingToPool(templatePoolReg, processorListReg, getHouse("snowy"), statue(i), 2);
            addBuildingToPool(templatePoolReg, processorListReg, getHouse("savanna"), statue(i), 1);
            addBuildingToPool(templatePoolReg, processorListReg, getHouse("taiga"), statue(i), 1);
        }
        Explorations.LOGGER.info("Added statues to vanilla villages");
    }

    private static void addBuildingToPool(Registry<StructureTemplatePool> templatePoolReg, Registry<StructureProcessorList> processorListReg,
                                          ResourceLocation poolRL, String nbtPieceRL, int weight) {

        Holder<StructureProcessorList> emptyProcessorList = processorListReg.getHolderOrThrow(EMPTY_PROCESSOR_LIST_KEY);
        StructureTemplatePool pool = templatePoolReg.get(poolRL);
        if (!(pool instanceof StructureTemplatePoolMixin poolAccessor)) {
            return;
        }

        SinglePoolElement piece = SinglePoolElement.legacy(nbtPieceRL, emptyProcessorList).apply(StructureTemplatePool.Projection.RIGID);

        for (int i = 0; i < weight; i++) {
            poolAccessor.getTemplates().add(piece);
        }

        List<Pair<StructurePoolElement, Integer>> listOfPieceEntries = new ArrayList<>();
        listOfPieceEntries.addAll(poolAccessor.getRawTemplates());
        listOfPieceEntries.add(new Pair<>(piece, weight));
        poolAccessor.setRawTemplates(listOfPieceEntries);
    }

    private static ResourceLocation getHouse(String type) {
        return new ResourceLocation("minecraft:village/" + type + "/houses");
    }

    private static String statue(int i) {
        return "explorations:statues/statue_" + i;
    }

}
