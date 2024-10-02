package com.tristankechlo.explorations.worldgen;

import com.mojang.datafixers.util.Pair;
import com.tristankechlo.explorations.Explorations;
import com.tristankechlo.explorations.config.ExplorationsConfig;
import com.tristankechlo.explorations.config.types.VillageType;
import com.tristankechlo.explorations.mixin.StructureTemplatePoolAccessor;
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

    public static void addStatuesToVillages(final MinecraftServer server) {
        Registry<StructureTemplatePool> templatePoolReg = server.registryAccess().registry(Registry.TEMPLATE_POOL_REGISTRY).orElseThrow();
        Registry<StructureProcessorList> processorListReg = server.registryAccess().registry(Registry.PROCESSOR_LIST_REGISTRY).orElseThrow();

        ExplorationsConfig.get().statues().forEach((type, list) -> {
            list.forEach((e) -> {
                addBuildingToPool(templatePoolReg, processorListReg, type, e.nbtLoc(), e.weight());
            });
        });
        Explorations.LOGGER.info("Added statues to vanilla villages");
    }

    private static void addBuildingToPool(Registry<StructureTemplatePool> templatePoolReg, Registry<StructureProcessorList> processorListReg,
                                          VillageType type, String nbtPieceRL, int weight) {

        Holder<StructureProcessorList> emptyProcessorList = processorListReg.getHolderOrThrow(EMPTY_PROCESSOR_LIST_KEY);
        StructureTemplatePool pool = templatePoolReg.get(type.getLocation());
        if (!(pool instanceof StructureTemplatePoolAccessor poolAccessor)) {
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

}
