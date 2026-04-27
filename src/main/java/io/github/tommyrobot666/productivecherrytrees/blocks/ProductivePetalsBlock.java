package io.github.tommyrobot666.productivecherrytrees.blocks;

import io.github.tommyrobot666.productivecherrytrees.ProducedResources;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.LeafLitterBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ProductivePetalsBlock extends LeafLitterBlock {
	public final ProducedResources producedResources;

	public ProductivePetalsBlock(Properties properties, ProducedResources producedResources) {
		super(properties);
		this.producedResources = producedResources;
	}

	@Override
	public void destroy(@NotNull LevelAccessor level, @NotNull BlockPos pos, @NotNull BlockState state) {
		if (producedResources.placedBlock != null) {
			level.setBlock(pos,producedResources.placedBlock.defaultBlockState(),3);
		}
	}
}
