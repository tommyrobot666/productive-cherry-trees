package io.github.tommyrobot666.productivecherrytrees.blocks;

import io.github.tommyrobot666.productivecherrytrees.ProducedResources;
import net.minecraft.world.level.block.Block;

public class ProductiveCherryType {
	public final Block log;
	public final Block leafs;
	public final ProductivePetalsBlock petals;
	public final Block sapling;
	public final ProducedResources producedResources;
	public final String id;

	public ProductiveCherryType(Block log, Block leafs, ProductivePetalsBlock petals, Block sapling, ProducedResources producedResources, String id) {
		this.log = log;
		this.leafs = leafs;
		this.petals = petals;
		this.sapling = sapling;
		this.producedResources = producedResources;
		this.id = id;
	}
}
