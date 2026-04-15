package io.github.tommyrobot666.productivecherrytrees.blocks;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.LeafLitterBlock;

import java.util.List;
import java.util.function.Supplier;

public class ProductivePetalsBlock extends LeafLitterBlock {
	private final Supplier<List<ItemStack>> producedResourcesSupplier;
	private List<ItemStack> producedResourcesValue;
	public List<ItemStack> producedResources(){
		if (producedResourcesValue == null){
			producedResourcesValue = producedResourcesSupplier.get();
		}
		return producedResourcesValue;
	}

	public ProductivePetalsBlock(Properties properties, Supplier<List<ItemStack>> producedResourcesSupplier) {
		super(properties);
		this.producedResourcesSupplier = producedResourcesSupplier;
	}
}
