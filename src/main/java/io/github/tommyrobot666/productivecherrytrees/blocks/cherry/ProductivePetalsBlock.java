package io.github.tommyrobot666.productivecherrytrees.blocks.cherry;

import io.github.tommyrobot666.productivecherrytrees.datagen.ProductiveCherryLoot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeafLitterBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ProductivePetalsBlock extends LeafLitterBlock {
	public final BlockState placedBlock;

	public ProductivePetalsBlock(Properties properties, BlockState placedBlock) {
		super(properties);
		this.placedBlock = placedBlock;
	}

	@Override
	public void destroy(@NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockState state) {
		if (placedBlock != null) {
			level.setBlock(pos, placedBlock,3);
		}
	}
}
