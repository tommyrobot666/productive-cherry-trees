package io.github.tommyrobot666.productivecherrytrees.blocks;

import io.github.tommyrobot666.productivecherrytrees.ProducedResources;
import io.github.tommyrobot666.productivecherrytrees.ProductiveCherryTrees;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class ProductiveCherryType {
	public final Block log;
	public final Block leafs;
	public final ProductivePetalsBlock petals;
	public final Block sapling;
	public final ProducedResources producedResources;
	public final String id;
	public final ResourceKey<ConfiguredFeature<?,?>> treeFeatureKey;

	public ProductiveCherryType(Block log, Block leafs, ProductivePetalsBlock petals, Block sapling, ProducedResources producedResources, String id) {
		this.log = log;
		this.leafs = leafs;
		this.petals = petals;
		this.sapling = sapling;
		this.producedResources = producedResources;
		this.id = id;
		this.treeFeatureKey = ResourceKey.create(
			Registries.CONFIGURED_FEATURE,
			Identifier.fromNamespaceAndPath(ProductiveCherryTrees.ID, id+"_productive_cherry_tree")
		);
	}
}
