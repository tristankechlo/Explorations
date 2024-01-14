package com.tristankechlo.explorations.eventhandler;

import com.tristankechlo.explorations.mixin_util.ChunkGeneratorAddon;
import com.tristankechlo.explorations.Explorations;
import com.tristankechlo.explorations.init.ModStructures;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.FlatChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.HashMap;
import java.util.Map;

public class WorldLoadingHandler {

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void addDimensionalSpacing(final WorldEvent.Load event) {
        if (!(event.getWorld() instanceof ServerWorld)) {
            return;
        }
        ServerWorld serverWorld = (ServerWorld) event.getWorld();

        // do not add when worldtype is terraforged
        if (isTerraForged(serverWorld)) {
            return;
        }

        // do not add when worldtype is SuperFlat
        if (serverWorld.getChunkSource().getGenerator() instanceof FlatChunkGenerator && serverWorld.dimension().equals(World.OVERWORLD)) {
            return;
        }

        // manually add structure spacing to world's chunkgenerator
        Map<Structure<?>, StructureSeparationSettings> tempMap = new HashMap<>(serverWorld.getChunkSource().generator.getSettings().structureConfig());
        tempMap.putIfAbsent(ModStructures.DESERT_RUIN.get(), getSettings(ModStructures.DESERT_RUIN.get()));
        tempMap.putIfAbsent(ModStructures.FORGOTTEN_WELL.get(), getSettings(ModStructures.FORGOTTEN_WELL.get()));
        tempMap.putIfAbsent(ModStructures.JUNGLE_TEMPLE.get(), getSettings(ModStructures.JUNGLE_TEMPLE.get()));
        tempMap.putIfAbsent(ModStructures.UNDERGROUND_TEMPLE.get(), getSettings(ModStructures.UNDERGROUND_TEMPLE.get()));
        serverWorld.getChunkSource().generator.getSettings().structureConfig = tempMap;
    }

    // check if the current world uses a ChunkGenerator from 'terraforged'
    private boolean isTerraForged(ServerWorld world) {
        try {
            ChunkGenerator generator = world.getChunkSource().generator;
            if (!(generator instanceof ChunkGeneratorAddon)) {
                return false;
            }
            ResourceLocation cgRL = Registry.CHUNK_GENERATOR.getKey(((ChunkGeneratorAddon) generator).explorations$getCodec());
            if (cgRL != null && cgRL.getNamespace().equals("terraforged")) {
                return true;
            }
        } catch (Exception e) {
            Explorations.LOGGER.error("Was unable to check if " + world.dimension().location() + " is using Terraforged's ChunkGenerator.");
        }
        return false;
    }

    private StructureSeparationSettings getSettings(Structure<?> structure) {
        return DimensionStructuresSettings.DEFAULTS.get(structure);
    }

}
