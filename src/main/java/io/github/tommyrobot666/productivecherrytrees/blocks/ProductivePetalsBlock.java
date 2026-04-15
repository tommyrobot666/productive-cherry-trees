package io.github.tommyrobot666.productivecherrytrees.blocks;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.LeafLitterBlock;

import java.util.List;

public class ProductivePetalsBlock extends LeafLitterBlock {
	public final List<ItemStack> producedResources;

	public ProductivePetalsBlock(Properties properties, List<ItemStack> producedResources) {
		super(properties);
		this.producedResources = producedResources;
	}
}
