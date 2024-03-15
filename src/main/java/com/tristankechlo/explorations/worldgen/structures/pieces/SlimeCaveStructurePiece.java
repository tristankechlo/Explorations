package com.tristankechlo.explorations.worldgen.structures.pieces;

import com.tristankechlo.explorations.Explorations;
import com.tristankechlo.explorations.init.ModRegistry;
import com.tristankechlo.explorations.mixin.SlimeEntityInvoker;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.monster.SlimeEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;

public class SlimeCaveStructurePiece extends TemplateStructurePiece {

    private static final ResourceLocation ID = new ResourceLocation(Explorations.MOD_ID, "slime_cave");
    private static CompoundNBT spawnDataTag = null;
    private static ListNBT spawnPotentialsTag = null;
    private final Rotation rotation;

    public SlimeCaveStructurePiece(BlockPos absolutePosition, Rotation rotation, TemplateManager manager) {
        super(ModRegistry.SLIME_CAVE_PIECE, 0);
        this.rotation = rotation;
        this.templatePosition = absolutePosition;
        Template template = manager.getOrCreate(ID);
        createSpawnData();
        this.loadTemplate(template);
    }

    public SlimeCaveStructurePiece(TemplateManager manager, CompoundNBT nbt) {
        super(ModRegistry.SLIME_CAVE_PIECE, nbt);
        this.rotation = Rotation.valueOf(nbt.getString("Rot"));
        Template template = manager.getOrCreate(ID);
        createSpawnData();
        this.loadTemplate(template);
    }

    private void loadTemplate(Template template) {
        BlockPos rotationPivot = calculateRotationPivot(template);

        PlacementSettings settings = new PlacementSettings();
        settings.setRotation(this.rotation);
        settings.setRotationPivot(rotationPivot);

        this.setup(template, this.templatePosition, settings);
    }

    private BlockPos calculateRotationPivot(Template template) {
        return new BlockPos(template.getSize().getX() / 2, 0, template.getSize().getZ() / 2);
    }

    @Override
    protected void handleDataMarker(String marker, BlockPos pos, IServerWorld level, Random random, MutableBoundingBox bb) {
        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 2);
        if (marker.equals("spawner")) {
            level.setBlock(pos, Blocks.SPAWNER.defaultBlockState(), 2);
            TileEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof MobSpawnerTileEntity) {
                MobSpawnerTileEntity spawner = (MobSpawnerTileEntity) blockEntity;
                CompoundNBT oldData = spawner.getSpawner().save(new CompoundNBT());
                oldData.put("SpawnData", spawnDataTag);
                oldData.put("SpawnPotentials", spawnPotentialsTag);
                spawner.getSpawner().load(oldData);
                spawner.setChanged();
                blockEntity.setChanged();
            }
        } else if (marker.equals("slime")) {
            SlimeEntity slime = EntityType.SLIME.create(level.getLevel());
            if (slime != null && (slime instanceof SlimeEntityInvoker)) {
                slime.moveTo(pos, 0.0F, 0.0F);
                slime.finalizeSpawn(level, level.getCurrentDifficultyAt(pos), SpawnReason.STRUCTURE, null, null);
                ((SlimeEntityInvoker) slime).explorations$setSize(random.nextInt(3) + 1, true);
                level.addFreshEntity(slime);
            }
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT tag) {
        super.addAdditionalSaveData(tag);
        tag.putString("Rot", rotation.name());
    }

    @Override
    public Rotation getRotation() {
        return rotation;
    }

    private static void createSpawnData() {
        if (spawnDataTag == null || spawnPotentialsTag == null) {
            spawnDataTag = new CompoundNBT();
            spawnDataTag.putString("id", "minecraft:slime");

            CompoundNBT entity = new CompoundNBT();
            entity.put("Entity", spawnDataTag.copy());
            spawnPotentialsTag = new ListNBT();
            spawnPotentialsTag.add(entity);
        }
    }

}
