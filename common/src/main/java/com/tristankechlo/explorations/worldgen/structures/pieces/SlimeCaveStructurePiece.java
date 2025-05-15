package com.tristankechlo.explorations.worldgen.structures.pieces;

import com.tristankechlo.explorations.init.ModRegistry;
import com.tristankechlo.explorations.worldgen.structures.processors.DeepslateProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.InclusiveRange;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

import java.util.Optional;

public class SlimeCaveStructurePiece extends TemplateStructurePiece {

    private static final InclusiveRange<Integer> RANGE = new InclusiveRange<>(0, 7);
    private static Tag spawnDataTag = null;

    public SlimeCaveStructurePiece(StructureTemplateManager manager, ResourceLocation location, BlockPos pos, Rotation rotation) {
        super(ModRegistry.SLIME_CAVE_PIECE.get(), 0, manager, location, location.toString(), makeSettings(rotation), pos);
        createSpawnData();
    }

    public SlimeCaveStructurePiece(StructureTemplateManager manager, CompoundTag nbt) {
        super(ModRegistry.SLIME_CAVE_PIECE.get(), nbt, manager, (id) -> makeSettings(nbt));
        createSpawnData();
    }

    public SlimeCaveStructurePiece(StructurePieceSerializationContext context, CompoundTag nbt) {
        this(context.structureTemplateManager(), nbt);
    }

    private static StructurePlaceSettings makeSettings(CompoundTag nbt) {
        Optional<String> rotation = nbt.getString("Rot");
        return new StructurePlaceSettings()
                .setRotation(rotation.map(Rotation::valueOf).orElse(Rotation.NONE))
                .addProcessor(DeepslateProcessor.INSTANCE);
    }

    private static StructurePlaceSettings makeSettings(Rotation rotation) {
        return new StructurePlaceSettings().setRotation(rotation).addProcessor(DeepslateProcessor.INSTANCE);
    }

    private static void createSpawnData() {
        if (spawnDataTag == null) {
            CompoundTag tag = new CompoundTag();
            tag.putString("id", "minecraft:slime");
            SpawnData.CustomSpawnRules customSpawnRules = new SpawnData.CustomSpawnRules(RANGE, RANGE);
            SpawnData spawnData = new SpawnData(tag, Optional.of(customSpawnRules), Optional.empty());
            spawnDataTag = SpawnData.CODEC.encodeStart(NbtOps.INSTANCE, spawnData).result()
                    .orElseThrow(() -> new IllegalStateException("Invalid SpawnData"));
        }
    }

    @Override
    protected void handleDataMarker(String marker, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox bb) {
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
        if (marker.equals("spawner")) {
            level.setBlock(pos, Blocks.SPAWNER.defaultBlockState(), 2);
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof SpawnerBlockEntity spawner) {
                CompoundTag oldData = spawner.getSpawner().save(new CompoundTag());
                oldData.put("SpawnData", spawnDataTag);
                spawner.getSpawner().load(null, pos, oldData);
                spawner.setChanged();
                blockEntity.setChanged();
            }
        } else if (marker.equals("slime")) {
            Slime slime = EntityType.SLIME.create(level.getLevel(), EntitySpawnReason.STRUCTURE);
            if (slime != null) {
                slime.snapTo(pos, 0.0F, 0.0F);
                slime.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), EntitySpawnReason.STRUCTURE, null);
                slime.setSize(random.nextInt(3) + 1, true);
                level.addFreshEntity(slime);
            }
        }
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
        super.addAdditionalSaveData(context, tag);
        tag.putString("Rot", this.placeSettings.getRotation().name());
    }
}
