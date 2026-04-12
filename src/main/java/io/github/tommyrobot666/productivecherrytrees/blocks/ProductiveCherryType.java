package io.github.tommyrobot666.productivecherrytrees.blocks;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.List;

public class ProductiveCherryType {
	public final Block log;
	public final Block leafs;
	public final Block petals;
	public final Block sapling;
	public final List<Item> producedResources;
	public final String id;

	public ProductiveCherryType(Block log, Block leafs, Block petals, Block sapling, List<Item> producedResources, String id) {
		this.log = log;
		this.leafs = leafs;
		this.petals = petals;
		this.sapling = sapling;
		this.producedResources = producedResources;
		this.id = id;
	}
}
