package io.github.tommyrobot666.productivecherrytrees.blocks.cherry;

import io.github.tommyrobot666.productivecherrytrees.datagen.ProductiveCherryLoot;
import io.github.tommyrobot666.productivecherrytrees.ProductiveCherryTrees;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;

public class ProductiveCherryType {
	public final Block log;
	public final Block leafs;
	public final ProductivePetalsBlock petals;
	public final Block sapling;
	/** @noinspection unused*/
	public final ProductiveCherryLoot productiveCherryLoot;
	public final String id;
	public final ResourceKey<ConfiguredFeature<?,?>> treeFeatureKey;

	public ProductiveCherryType(Block log, Block leafs, ProductivePetalsBlock petals, Block sapling, ProductiveCherryLoot productiveCherryLoot, String id) {
		this.log = log;
		this.leafs = leafs;
		this.petals = petals;
		this.sapling = sapling;
		this.productiveCherryLoot = productiveCherryLoot;
		this.id = id;
		this.treeFeatureKey = ResourceKey.create(
			Registries.CONFIGURED_FEATURE,
			Identifier.fromNamespaceAndPath(ProductiveCherryTrees.ID, id+"_productive_cherry_tree")
		);
	}

	public static class ProductiveCherryTypeBuilder {
		String name;
		Identifier id;
		ProductiveCherryLoot productiveCherryLoot;
		double dropPetalsChance = 1;
		Item.Properties petalProperties = new Item.Properties();
		Item.Properties leafProperties = new Item.Properties();
		Item.Properties logProperties = new Item.Properties();
		Item.Properties saplingProperties = new Item.Properties();
		Item.Properties woodProperties = new Item.Properties();
		MapColor logSideColor = MapColor.TERRACOTTA_GRAY;
		MapColor logTopColor = MapColor.TERRACOTTA_WHITE;
		MapColor leafsColor = MapColor.COLOR_PINK;
		MapColor petalsColor = MapColor.PLANT;
		MapColor saplingColor = MapColor.COLOR_PINK;
		MapColor woodColor = MapColor.TERRACOTTA_WHITE;
		TreeGrower treeGrowerOverride;

		ProductiveCherryTypeBuilder(String name, Identifier id) {
			this.name = name;
			this.id = id;
		}

		void setDropPetalsChance(double dropPetalsChance){
			this.dropPetalsChance = dropPetalsChance;
		}

		public ProductiveCherryType buildAndRegister(){
			return null;
		}

	}
}
