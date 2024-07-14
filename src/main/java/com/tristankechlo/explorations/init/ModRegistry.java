package com.tristankechlo.explorations.init;

import com.tristankechlo.explorations.Explorations;
import com.tristankechlo.explorations.worldgen.features.ScarecrowFeature;
import com.tristankechlo.explorations.worldgen.structures.pieces.SlimeCaveStructurePiece;
import com.tristankechlo.explorations.worldgen.structures.processors.StoneBrickAgingProcessor;
import com.tristankechlo.explorations.worldgen.structures.processors.WoolReplacingProcessor;
import com.tristankechlo.explorations.worldgen.treedecorators.LanternDecorator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRegistry {

    /* REGISTRIES */
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, Explorations.MOD_ID);
    public static final DeferredRegister<TreeDecoratorType<?>> TREEDECORATOS = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, Explorations.MOD_ID);

    /* REGISTRY OBJECTS */
    public static final RegistryObject<ScarecrowFeature> SCARECROW = FEATURES.register("scarecrow", ScarecrowFeature::new);
    public static final RegistryObject<TreeDecoratorType<?>> LANTERN = TREEDECORATOS.register("lantern", () -> new TreeDecoratorType<>(LanternDecorator.CODEC));
    public static IStructurePieceType SLIME_CAVE_PIECE;
    public static IStructureProcessorType<StoneBrickAgingProcessor> STONE_BRICK_AGING_PROCESSOR;
    public static IStructureProcessorType<WoolReplacingProcessor> WOOL_REPLACING_PROCESSOR;

    public static void setupStructureProcessors() {
        STONE_BRICK_AGING_PROCESSOR = Registry.register(Registry.STRUCTURE_PROCESSOR, loc("stone_brick_aging"), () -> StoneBrickAgingProcessor.CODEC);
        WOOL_REPLACING_PROCESSOR = Registry.register(Registry.STRUCTURE_PROCESSOR, loc("wool_replacing"), () -> WoolReplacingProcessor.CODEC);
    }

    public static void registerStructurePieces() {
        SLIME_CAVE_PIECE = Registry.register(Registry.STRUCTURE_PIECE, loc("slime_cave_piece"), SlimeCaveStructurePiece::new);
    }

    private static ResourceLocation loc(String name) {
        return new ResourceLocation(Explorations.MOD_ID, name);
    }

}
