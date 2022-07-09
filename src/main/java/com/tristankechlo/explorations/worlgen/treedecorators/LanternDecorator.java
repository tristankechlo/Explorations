package com.tristankechlo.explorations.worlgen.treedecorators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tristankechlo.explorations.init.ModTreeDecorators;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ChainBlock;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;

public class LanternDecorator extends TreeDecorator {

	public static final Codec<LanternDecorator> CODEC = RecordCodecBuilder.create((builder) -> {
		return builder.group(Codec.floatRange(0.0F, 1.0F).fieldOf("probability").forGetter((decorator) -> {
			return decorator.probability;
		}), IntProvider.codec(0, 10).fieldOf("lantern_count").orElse(ConstantInt.of(3)).forGetter((decorator) -> {
			return decorator.count;
		}), IntProvider.codec(0, 10).fieldOf("chain_length").orElse(ConstantInt.of(1)).forGetter((decorator) -> {
			return decorator.chainLength;
		})).apply(builder, LanternDecorator::new);
	});

	private final BlockState chain = Blocks.CHAIN.defaultBlockState().setValue(ChainBlock.AXIS, Axis.Y);
	private final BlockState lantern = Blocks.LANTERN.defaultBlockState().setValue(LanternBlock.HANGING, Boolean.TRUE);
	private final float probability; // chance for the tree to have lanterns
	private final IntProvider count; // number of lanterns per tree
	private final IntProvider chainLength; // the length of the chains

	public LanternDecorator(float probability, IntProvider count, IntProvider chainLength) {
		this.probability = probability;
		this.count = count;
		this.chainLength = chainLength;
	}

	@Override
	protected TreeDecoratorType<?> type() {
		return ModTreeDecorators.LANTERN.get();
	}

	@Override
	public void place(Context context) {
		RandomSource random = context.random();
		if (random.nextFloat() > this.probability) {
			return;
		}
		final int count = this.count.sample(random);
		List<BlockPos> possible = getLeavesShuffled(context, count);
		for (int i = 0; i < possible.size(); i++) {
			int length = this.chainLength.sample(random);
			BlockPos blockpos;
			// set all chains
			for (blockpos = possible.get(i); context.isAir(blockpos) && length > 0; --length) {
				context.setBlock(blockpos, chain);
				blockpos = blockpos.below();
			}
			// set the lantern below
			context.setBlock(blockpos, lantern);
		}
	}

	private static List<BlockPos> getLeavesShuffled(Context context, final int maxCount) {
		RandomSource random = context.random();
		List<BlockPos> all = context.leaves().stream().map((pos) -> pos.below()).filter((pos) -> context.isAir(pos))
				.collect(Collectors.toList());
		Set<BlockPos> target = new HashSet<>();
		int maxIndex = Math.min(maxCount, all.size());
		while (maxIndex > 0 && all.size() > 0) {
			BlockPos pos = all.remove(random.nextInt(all.size()));
			target.add(pos);
			maxIndex--;
		}
		return new ArrayList<>(target);
	}

}
