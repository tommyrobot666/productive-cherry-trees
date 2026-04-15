package io.github.tommyrobot666.productivecherrytrees.blocks;

import io.github.tommyrobot666.productivecherrytrees.ProducedResources;
import net.minecraft.world.level.block.LeafLitterBlock;

public class ProductivePetalsBlock extends LeafLitterBlock {
	public final ProducedResources producedResources;

	public ProductivePetalsBlock(Properties properties, ProducedResources producedResources) {
		super(properties);
		this.producedResources = producedResources;
	}
}
