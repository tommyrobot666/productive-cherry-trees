package io.github.tommyrobot666.productivecherrytrees.blocks.cherry;

import io.github.tommyrobot666.productivecherrytrees.datagen.ProductiveCherryLoot;
import io.github.tommyrobot666.productivecherrytrees.ProductiveCherryTrees;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.github.tommyrobot666.productivecherrytrees.ProductiveCherryTrees.ID;

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
		/// Shown to user in en_us
		String name;
		/// Internal name
		Identifier id;
		ProducedResources producedResources;
		ProductiveCherryLoot productiveCherryLoot;
		double dropPetalsChance = 1;
		Item.Properties petalItemProperties = new Item.Properties();
		Item.Properties leafItemProperties = new Item.Properties();
		Item.Properties logItemProperties = new Item.Properties();
		Item.Properties saplingItemProperties = new Item.Properties();
		// TODO add wood set
		Item.Properties woodItemProperties = new Item.Properties();
		BlockBehaviour.Properties petalProperties = BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_PETALS).strength(.3f);
		BlockBehaviour.Properties leafProperties = BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_LEAVES);
		BlockBehaviour.Properties logProperties = BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_LOG);
		BlockBehaviour.Properties saplingProperties = BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_SAPLING);
		// TODO add wood set
		BlockBehaviour.Properties woodProperties = BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_PLANKS);
		MapColor logSideColor = MapColor.TERRACOTTA_GRAY;
		MapColor logTopColor = MapColor.TERRACOTTA_WHITE;
		MapColor leafsColor = MapColor.COLOR_PINK;
		MapColor petalsColor = MapColor.PLANT;
		MapColor saplingColor = MapColor.COLOR_PINK;
		// TODO add wood set
		MapColor woodColor = MapColor.TERRACOTTA_WHITE;
		TreeGrower treeGrowerOverride;
		ResourceKey<ConfiguredFeature<?,?>> treeFeatureKey;

		ProductiveCherryTypeBuilder(String name, Identifier id, ProducedResources producedResources, ProductiveCherryLoot productiveCherryLoot) {
			this.name = name;
			this.id = id;
			this.producedResources = producedResources;
			this.productiveCherryLoot = productiveCherryLoot;
			treeFeatureKey = ResourceKey.create(Registries.CONFIGURED_FEATURE,id);
		}

		ProductiveCherryTypeBuilder setDropPetalsChance(double dropPetalsChance){
			this.dropPetalsChance = dropPetalsChance;
			return this;
		}

		ProductiveCherryTypeBuilder setTreeGrowerOverride(TreeGrower treeGrower){
			treeGrowerOverride = treeGrower;
			return this;
		}

		ProductiveCherryTypeBuilder setTreeFeatureKey(ResourceKey<ConfiguredFeature<?,?>> key){
			treeFeatureKey = key;
			return this;
		}

		ProductiveCherryTypeBuilder setLogSideColor(MapColor color){
			logSideColor = color;
			return this;
		}

		ProductiveCherryTypeBuilder setLogTopColor(MapColor color){
			logTopColor = color;
			return this;
		}

		ProductiveCherryTypeBuilder setLeafsColor(MapColor color){
			leafsColor = color;
			return this;
		}

		ProductiveCherryTypeBuilder setPetalsColor(MapColor color){
			petalsColor = color;
			return this;
		}

		ProductiveCherryTypeBuilder setSaplingColor(MapColor color){
			saplingColor = color;
			return this;
		}

		ProductiveCherryTypeBuilder setWoodColor(MapColor color){
			woodColor = color;
			return this;
		}

		ProductiveCherryTypeBuilder applyMapColors(){
			logProperties.mapColor((state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? logTopColor : logSideColor));
			petalProperties.mapColor(petalsColor);
			leafProperties.mapColor(leafsColor);
			saplingProperties.mapColor(saplingColor);
			woodProperties.mapColor(woodColor);
			return this;
		}

		ProductiveCherryTypeBuilder changePetalProperties(Consumer<BlockBehaviour.Properties> consumer){
			consumer.accept(petalProperties);
			return this;
		}

		ProductiveCherryTypeBuilder changeLeafProperties(Consumer<BlockBehaviour.Properties> consumer){
			consumer.accept(leafProperties);
			return this;
		}

		ProductiveCherryTypeBuilder changeLogProperties(Consumer<BlockBehaviour.Properties> consumer){
			consumer.accept(logProperties);
			return this;
		}

		ProductiveCherryTypeBuilder changeSaplingProperties(Consumer<BlockBehaviour.Properties> consumer){
			consumer.accept(saplingProperties);
			return this;
		}

		ProductiveCherryTypeBuilder changeWoodProperties(Consumer<BlockBehaviour.Properties> consumer){
			consumer.accept(woodProperties);
			return this;
		}

		ProductiveCherryTypeBuilder changePetalItemProperties(Consumer<Item.Properties> consumer){
			consumer.accept(petalItemProperties);
			return this;
		}

		ProductiveCherryTypeBuilder changeLeafItemProperties(Consumer<Item.Properties> consumer){
			consumer.accept(leafItemProperties);
			return this;
		}

		ProductiveCherryTypeBuilder changeLogItemProperties(Consumer<Item.Properties> consumer){
			consumer.accept(logItemProperties);
			return this;
		}

		ProductiveCherryTypeBuilder changeSaplingItemProperties(Consumer<Item.Properties> consumer){
			consumer.accept(saplingItemProperties);
			return this;
		}

		ProductiveCherryTypeBuilder changeWoodItemProperties(Consumer<Item.Properties> consumer){
			consumer.accept(woodItemProperties);
			return this;
		}

		TreeGrower createDefaultTreeGrower(){
			return new TreeGrower(
				id.getNamespace()+"_productive_cherry_tree",
				Optional.empty(),
				Optional.of(treeFeatureKey),
				Optional.empty()
			);
		}

		public ProductiveCherryType buildAndRegister(){
			return null;
		}

	}
}
