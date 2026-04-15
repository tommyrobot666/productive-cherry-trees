package io.github.tommyrobot666.productivecherrytrees.blocks;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.List;
import java.util.function.Supplier;

public class ProductiveCherryType {
	public final Block log;
	public final Block leafs;
	public final ProductivePetalsBlock petals;
	public final Block sapling;
	private final Supplier<List<ItemStack>> producedResourcesSupplier;
	private List<ItemStack> producedResourcesValue;
	public List<ItemStack> producedResources(){
		if (producedResourcesValue == null){
			producedResourcesValue = producedResourcesSupplier.get();
		}
		return producedResourcesValue;
	}
	public final String id;

	public ProductiveCherryType(Block log, Block leafs, ProductivePetalsBlock petals, Block sapling, Supplier<List<ItemStack>> producedResourcesSupplier, String id) {
		this.log = log;
		this.leafs = leafs;
		this.petals = petals;
		this.sapling = sapling;
		this.producedResourcesSupplier = producedResourcesSupplier;
		this.id = id;
	}
}
