package com.tristankechlo.explorations;

import com.tristankechlo.explorations.init.ModRegistry;
import com.tristankechlo.explorations.registration.RegistryObject;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod(Explorations.MOD_ID)
@Mod.EventBusSubscriber(modid = Explorations.MOD_ID)
public final class ForgeExplorations {

    private static final List<ResourceKey<Biome>> LARGE_MUSHROOM_BIOMES = List.of(Biomes.DARK_FOREST);
    private static final List<ResourceKey<Biome>> SCARECROW_ACACIA_BIOMES = List.of(Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.WINDSWEPT_SAVANNA);
    private static final List<ResourceKey<Biome>> SCARECROW_BIRCH_BIOMES = List.of(Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST);
    private static final List<ResourceKey<Biome>> SCARECROW_DARK_OAK_BIOMES = List.of(Biomes.DARK_FOREST);
    private static final List<ResourceKey<Biome>> SCARECROW_JUNGLE_BIOMES = List.of(Biomes.JUNGLE, Biomes.BAMBOO_JUNGLE, Biomes.SPARSE_JUNGLE);
    private static final List<ResourceKey<Biome>> SCARECROW_OAK_BIOMES = List.of(Biomes.FOREST, Biomes.DARK_FOREST, Biomes.BIRCH_FOREST, Biomes.OLD_GROWTH_BIRCH_FOREST, Biomes.FLOWER_FOREST, Biomes.WINDSWEPT_FOREST, Biomes.WOODED_BADLANDS);
    private static final List<ResourceKey<Biome>> SCARECROW_SPRUCE_BIOMES = List.of(Biomes.SNOWY_TAIGA, Biomes.TAIGA, Biomes.OLD_GROWTH_PINE_TAIGA, Biomes.OLD_GROWTH_SPRUCE_TAIGA, Biomes.GROVE, Biomes.WINDSWEPT_HILLS);

    public ForgeExplorations() {
        ModRegistry.loadClass(); // load ModRegistry to register everything
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void onBiomeLoad(final BiomeLoadingEvent event) {
        addFeature(event, ModRegistry.LARGE_MUSHROOM_PLACED, LARGE_MUSHROOM_BIOMES);
        addFeature(event, ModRegistry.SCARECROW_ACACIA_PLACED, SCARECROW_ACACIA_BIOMES);
        addFeature(event, ModRegistry.SCARECROW_BIRCH_PLACED, SCARECROW_BIRCH_BIOMES);
        addFeature(event, ModRegistry.SCARECROW_DARK_OAK_PLACED, SCARECROW_DARK_OAK_BIOMES);
        addFeature(event, ModRegistry.SCARECROW_JUNGLE_PLACED, SCARECROW_JUNGLE_BIOMES);
        addFeature(event, ModRegistry.SCARECROW_OAK_PLACED, SCARECROW_OAK_BIOMES);
        addFeature(event, ModRegistry.SCARECROW_SPRUCE_PLACED, SCARECROW_SPRUCE_BIOMES);
    }

    private static void addFeature(BiomeLoadingEvent event, RegistryObject<PlacedFeature> feature, List<ResourceKey<Biome>> biomes) {
        ResourceLocation name = event.getName();
        if (name == null) {
            return;
        }
        ResourceKey<Biome> biome = ResourceKey.create(Registry.BIOME_REGISTRY, name);
        if (biomes.contains(biome)) {
            GenerationStep.Decoration step = GenerationStep.Decoration.VEGETAL_DECORATION;
            event.getGeneration().addFeature(step, feature.asHolder());
        }
    }

}
