package com.tristankechlo.explorations.worldgen;

import com.mojang.datafixers.util.Pair;
import com.tristankechlo.explorations.Explorations;
import com.tristankechlo.explorations.config.ExplorationsConfig;
import com.tristankechlo.explorations.config.types.VillageType;
import com.tristankechlo.explorations.mixin.JigsawPatternAccessor;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.jigsaw.JigsawPattern;
import net.minecraft.world.gen.feature.jigsaw.JigsawPiece;
import net.minecraft.world.gen.feature.jigsaw.SingleJigsawPiece;

import java.util.ArrayList;
import java.util.List;

/**
 * @see <a href="https://gist.github.com/TelepathicGrunt/4fdbc445ebcbcbeb43ac748f4b18f342">TelepathicGrunt/addNewBuildingsVillages.java</a>
 */
public final class WorldGenHelper {

    public static void addStatuesToVillages(final MinecraftServer server) {
        MutableRegistry<JigsawPattern> templatePoolReg = server.registryAccess().registry(Registry.TEMPLATE_POOL_REGISTRY).get();

        ExplorationsConfig.get().statues().forEach((type, list) -> {
            list.forEach((e) -> {
                addBuildingToPool(templatePoolReg, type, e.nbtLoc(), e.weight());
            });
        });
        Explorations.LOGGER.info("Added statues to vanilla villages");
    }

    private static void addBuildingToPool(MutableRegistry<JigsawPattern> templatePoolReg, VillageType type, String nbtPieceRL, int weight) {
        JigsawPattern pool = templatePoolReg.get(type.getLocation());
        if (!(pool instanceof JigsawPatternAccessor)) {
            return;
        }

        JigsawPatternAccessor poolAccess = (JigsawPatternAccessor) pool;
        SingleJigsawPiece piece = SingleJigsawPiece.single(nbtPieceRL).apply(JigsawPattern.PlacementBehaviour.RIGID);

        for (int i = 0; i < weight; i++) {
            poolAccess.getTemplates().add(piece);
        }

        List<Pair<JigsawPiece, Integer>> listOfPieceEntries = new ArrayList<>();
        listOfPieceEntries.addAll(poolAccess.getRawTemplates());
        listOfPieceEntries.add(new Pair<>(piece, weight));
        poolAccess.setRawTemplates(listOfPieceEntries);
    }

}
