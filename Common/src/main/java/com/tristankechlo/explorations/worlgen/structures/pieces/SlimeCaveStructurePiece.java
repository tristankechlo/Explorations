package com.tristankechlo.explorations.worlgen.structures.pieces;

import com.tristankechlo.explorations.init.ModRegistry;
import com.tristankechlo.explorations.worlgen.structures.processors.DeepslateProcessor;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;

public class SlimeCaveStructurePiece extends TemplateStructurePiece {

    public SlimeCaveStructurePiece(StructureTemplateManager manager, ResourceLocation location, BlockPos pos, Rotation rotation) {
        super(ModRegistry.SLIME_CAVE_PIECE.get(), 0, manager, location, location.toString(), makeSettings(rotation), pos);
    }

    public SlimeCaveStructurePiece(StructureTemplateManager manager, CompoundTag tag) {
        super(ModRegistry.SLIME_CAVE_PIECE.get(), tag, manager, ($$1x) -> makeSettings(Rotation.valueOf(tag.getString("Rot"))));
    }

    public SlimeCaveStructurePiece(StructurePieceSerializationContext context, CompoundTag compoundTag) {
        this(context.structureTemplateManager(), compoundTag);
    }

    private static StructurePlaceSettings makeSettings(Rotation rotation) {
        return (new StructurePlaceSettings()).setRotation(rotation).addProcessor(DeepslateProcessor.INSTANCE);
    }

    @Override
    protected void handleDataMarker(String marker, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox bb) {
        if (marker.equals("spawner")) {
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
            level.setBlock(pos, Blocks.SPAWNER.defaultBlockState(), 2);
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof SpawnerBlockEntity) {
                ((SpawnerBlockEntity) blockEntity).setEntityId(EntityType.SLIME, random);
                blockEntity.setChanged();
            }
        } else if (marker.equals("slime")) {
            Slime slime = EntityType.SLIME.create(level.getLevel());
            if (slime != null) {
                slime.moveTo(pos, 0.0F, 0.0F);
                slime.setSize(random.nextInt(3) + 1, true);
                slime.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), MobSpawnType.STRUCTURE, null, null);
                level.addFreshEntity(slime);
            }
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
        }
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
        super.addAdditionalSaveData(context, tag);
        tag.putString("Rot", this.placeSettings.getRotation().name());
    }
}
