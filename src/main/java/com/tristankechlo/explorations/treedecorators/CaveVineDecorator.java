package com.tristankechlo.explorations.treedecorators;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tristankechlo.explorations.init.ModTreeDecorators;

import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class CaveVineDecorator extends TreeDecorator {

	public static final Codec<CaveVineDecorator> CODEC = RecordCodecBuilder.create((builder) -> {
		return builder.group(Codec.floatRange(0.0F, 1.0F).fieldOf("start_chance").forGetter((decorator) -> {
			return decorator.startProbability;
		}), Codec.floatRange(0.0F, 1.0F).fieldOf("berry_chance").forGetter((decorator) -> {
			return decorator.berrySpawnChance;
		}), Codec.intRange(0, 10).optionalFieldOf("max_vine_length", 4).forGetter((decorator) -> {
			return decorator.maxVineLength;
		}), Codec.BOOL.optionalFieldOf("only_outer_leaves", true).forGetter((decorator) -> {
			return decorator.onlyOuterLeaves;
		})).apply(builder, CaveVineDecorator::new);
	});

	private final float startProbability; // chance for the leave to have cave_vines
	private final float berrySpawnChance; // chance for the vines to generate with berries
	private final int maxVineLength; // maximum length of the vines
	private final boolean onlyOuterLeaves; // whether or not only the outer leaves of the tree should have vines

	public CaveVineDecorator(float startProbability, float berrySpawnChance, int maxVineLength,
			boolean onlyOuterLeaves) {
		this.startProbability = startProbability;
		this.berrySpawnChance = berrySpawnChance;
		this.maxVineLength = maxVineLength;
		this.onlyOuterLeaves = onlyOuterLeaves;
	}

	@Override
	protected TreeDecoratorType<?> type() {
		return ModTreeDecorators.CAVE_VINES.get();
	}

	@Override
	public void place(Context context) {
		RandomSource random = context.random();
		context.leaves().forEach((blockPosLeave) -> {
			if (random.nextFloat() < this.startProbability) {
				if (onlyOuterLeaves && !isOuterBlock(blockPosLeave, context)) {
					return;
				}
				BlockPos pos = blockPosLeave.below();
				if (context.isAir(pos)) {
					addCaveVines(pos, context, berrySpawnChance, maxVineLength);
				}
			}
		});
	}

	private static boolean isOuterBlock(BlockPos startPos, Context context) {
		ImmutableList<BlockPos> neighbours = ImmutableList.of(startPos.north(), startPos.east(), startPos.south(),
				startPos.west());
		for (BlockPos pos : neighbours) {
			if (context.isAir(pos)) {
				return true;
			}
		}
		return false;
	}

	private static void addCaveVines(BlockPos startPos, Context context, float berryChance, final int maxVineLength) {
		RandomSource random = context.random();
		int i = maxVineLength;
		for (BlockPos blockpos = startPos; context.isAir(blockpos) && i > 0; --i) {
			BlockState state = Blocks.CAVE_VINES.defaultBlockState();
			if (random.nextFloat() < berryChance) {
				state = state.setValue(BlockStateProperties.BERRIES, Boolean.TRUE);
			}
			context.setBlock(blockpos, state);
			blockpos = blockpos.below();
		}
	}

}
