package io.github.tommyrobot666.productivecherrytrees.blocks.cherry;

import io.github.tommyrobot666.productivecherrytrees.datagen.ProductiveCherryLoot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LeafLitterBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ProductivePetalsBlock extends LeafLitterBlock {
	public final ProductiveCherryLoot productiveCherryLoot;

	public ProductivePetalsBlock(Properties properties, ProductiveCherryLoot productiveCherryLoot) {
		super(properties);
		this.productiveCherryLoot = productiveCherryLoot;
	}

	@Override
	public void destroy(@NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockState state) {
		if (productiveCherryLoot.placedBlock != null) {
			level.setBlock(pos, productiveCherryLoot.placedBlock.defaultBlockState(),3);
		}
	}
}
