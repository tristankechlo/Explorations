package com.tristankechlo.explorations.init;

import com.tristankechlo.explorations.Explorations;
import com.tristankechlo.explorations.worldgen.structures.pieces.SlimeCaveStructurePiece;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;

public class ModStructurePieces {

    public static IStructurePieceType SLIME_CAVE_PIECE;

    public static void registerStructurePieces() {
        SLIME_CAVE_PIECE = Registry.register(Registry.STRUCTURE_PIECE, loc("slime_cave_piece"), SlimeCaveStructurePiece::new);
    }

    private static ResourceLocation loc(String name) {
        return new ResourceLocation(Explorations.MOD_ID, name);
    }

}
