package com.tristankechlo.explorations.worldgen;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.ArrayList;
import java.util.List;

public class WorldGenHelper {

    private static final ResourceKey<StructureProcessorList> EMPTY_PROCESSOR_LIST_KEY = ResourceKey.create(Registry.PROCESSOR_LIST_REGISTRY, new ResourceLocation("minecraft", "empty"));

    public static void addStatuesToVillage(final MinecraftServer server) {
        Registry<StructureTemplatePool> templatePoolRegistry = server.registryAccess().registry(Registry.TEMPLATE_POOL_REGISTRY).orElseThrow();
        Registry<StructureProcessorList> processorListRegistry = server.registryAccess().registry(Registry.PROCESSOR_LIST_REGISTRY).orElseThrow();

        for (int i = 1; i <= 4; i++) {
            addBuildingToPool(templatePoolRegistry, processorListRegistry, getHouses("plains"), "explorations:statues/statue_" + i, 1);
            addBuildingToPool(templatePoolRegistry, processorListRegistry, getHouses("snowy"), "explorations:statues/statue_" + i, 1);
            addBuildingToPool(templatePoolRegistry, processorListRegistry, getHouses("savanna"), "explorations:statues/statue_" + i, 1);
            addBuildingToPool(templatePoolRegistry, processorListRegistry, getHouses("taiga"), "explorations:statues/statue_" + i, 1);
        }
    }

    private static void addBuildingToPool(Registry<StructureTemplatePool> templatePoolRegistry, 
                                          Registry<StructureProcessorList> processorListRegistry,
                                          ResourceLocation poolRL, 
                                          String nbtPieceRL,
                                          int weight) {
        // Grabs the processor list we want to use along with our piece.
        // This is a requirement as using the ProcessorLists.EMPTY field will cause the game to throw errors.
        // The reason why is, the empty processor list in the world's registry is not the same instance as in that field once the world is started up.
        Holder<StructureProcessorList> emptyProcessorList = processorListRegistry.getHolderOrThrow(EMPTY_PROCESSOR_LIST_KEY);

        StructureTemplatePool pool = templatePoolRegistry.get(poolRL);
        if (!(pool instance StructureTemplatePoolAccessor poolAccess)) {
            return;
        }

        // Grabs the nbt piece and creates a SinglePoolElement of it that we can add to a structure's pool.
        // Use .legacy( for villages/outposts and .single( for everything else
        SinglePoolElement piece = SinglePoolElement.legacy(nbtPieceRL, emptyProcessorList).apply(StructureTemplatePool.Projection.RIGID);

        // Use AccessTransformer or Accessor Mixin to make StructureTemplatePool's templates field public for us to see.
        // Weight is handled by how many times the entry appears in this list.
        // We do not need to worry about immutability as this field is created using Lists.newArrayList(); which makes a mutable list.
        for (int i = 0; i < weight; i++) {
            poolAccess.getTemplates().add(piece);
        }

        // Use AccessTransformer or Accessor Mixin to make StructureTemplatePool's rawTemplates field public for us to see.
        // This list of pairs of pieces and weights is not used by vanilla by default but another mod may need it for efficiency.
        // So lets add to this list for completeness. We need to make a copy of the array as it can be an immutable list.
        //   NOTE: This is a com.mojang.datafixers.util.Pair. It is NOT a fastUtil pair class. Use the mojang class.
        List<Pair<StructurePoolElement, Integer>> listOfPieceEntries = new ArrayList<>();
        listOfPieceEntries.addAll(poolAccess.getRawTemplates());
        listOfPieceEntries.add(new Pair<>(piece, weight));
        poolAccess.setRawTemplates() = listOfPieceEntries;
    }

    private static ResourceLocation getHouses(String hous) {
        return new ResourceLocation("minecraft:village/" + house + "/houses")
    }

}
