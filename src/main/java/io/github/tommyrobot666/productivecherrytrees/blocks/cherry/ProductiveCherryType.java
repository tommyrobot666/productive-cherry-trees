package io.github.tommyrobot666.productivecherrytrees.blocks.cherry;

import io.github.tommyrobot666.productivecherrytrees.blocks.ModBlocks;
import io.github.tommyrobot666.productivecherrytrees.datagen.ProductiveCherryLoot;
import io.github.tommyrobot666.productivecherrytrees.ProductiveCherryTrees;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static io.github.tommyrobot666.productivecherrytrees.ProductiveCherryTrees.ID;

public class ProductiveCherryType {
	public final String name;
	public final Identifier id;
	public final double dropPetalsChance;
	public final ProducedResources producedResources;
	/// Used for loot datagen
	public final ProductiveCherryLoot productiveCherryLoot;
	public final RotatedPillarBlock log;
	public final ProductiveLeafsBlock leafs;
	public final ProductivePetalsBlock petals;
	public final SaplingBlock sapling;
	/// Used for tree datagen
	public final ResourceKey<ConfiguredFeature<?,?>> treeFeatureKey;

	public ProductiveCherryType(String name, Identifier id, double dropPetalsChance, ProducedResources producedResources, ProductiveCherryLoot productiveCherryLoot, RotatedPillarBlock log, ProductiveLeafsBlock leafs, ProductivePetalsBlock petals, SaplingBlock sapling, ResourceKey<ConfiguredFeature<?,?>> treeFeatureKey) {
		this.name = name;
		this.id = id;
		this.dropPetalsChance = dropPetalsChance;
		this.producedResources = producedResources;
		this.productiveCherryLoot = productiveCherryLoot;
		this.log = log;
		this.leafs = leafs;
		this.petals = petals;
		this.sapling = sapling;
		this.treeFeatureKey = treeFeatureKey;
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
		ParticleOptions leafParticles = ParticleTypes.CHERRY_LEAVES;

		public ProductiveCherryTypeBuilder(String name, Identifier id, ProducedResources producedResources, ProductiveCherryLoot productiveCherryLoot) {
			this.name = name;
			this.id = id;
			this.producedResources = producedResources;
			this.productiveCherryLoot = productiveCherryLoot;
			treeFeatureKey = ResourceKey.create(Registries.CONFIGURED_FEATURE,id);
		}

		public ProductiveCherryTypeBuilder setDropPetalsChance(double dropPetalsChance){
			this.dropPetalsChance = dropPetalsChance;
			return this;
		}

		public ProductiveCherryTypeBuilder setTreeGrowerOverride(TreeGrower treeGrower){
			treeGrowerOverride = treeGrower;
			return this;
		}

		public ProductiveCherryTypeBuilder setTreeFeatureKey(ResourceKey<ConfiguredFeature<?,?>> key){
			treeFeatureKey = key;
			return this;
		}

		public ProductiveCherryTypeBuilder setLeafParticles(ParticleOptions particleType){
			leafParticles = particleType;
			return this;
		}

		public ProductiveCherryTypeBuilder setLogSideColor(MapColor color){
			logSideColor = color;
			return this;
		}

		public ProductiveCherryTypeBuilder setLogTopColor(MapColor color){
			logTopColor = color;
			return this;
		}

		public ProductiveCherryTypeBuilder setLeafsColor(MapColor color){
			leafsColor = color;
			return this;
		}

		public ProductiveCherryTypeBuilder setPetalsColor(MapColor color){
			petalsColor = color;
			return this;
		}

		public ProductiveCherryTypeBuilder setSaplingColor(MapColor color){
			saplingColor = color;
			return this;
		}

		public ProductiveCherryTypeBuilder setWoodColor(MapColor color){
			woodColor = color;
			return this;
		}

		public ProductiveCherryTypeBuilder applyMapColors(){
			logProperties.mapColor((state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? logTopColor : logSideColor));
			petalProperties.mapColor(petalsColor);
			leafProperties.mapColor(leafsColor);
			saplingProperties.mapColor(saplingColor);
			woodProperties.mapColor(woodColor);
			return this;
		}

		public ProductiveCherryTypeBuilder changePetalProperties(Consumer<BlockBehaviour.Properties> consumer){
			consumer.accept(petalProperties);
			return this;
		}

		public ProductiveCherryTypeBuilder changeLeafProperties(Consumer<BlockBehaviour.Properties> consumer){
			consumer.accept(leafProperties);
			return this;
		}

		public ProductiveCherryTypeBuilder changeLogProperties(Consumer<BlockBehaviour.Properties> consumer){
			consumer.accept(logProperties);
			return this;
		}

		public ProductiveCherryTypeBuilder changeSaplingProperties(Consumer<BlockBehaviour.Properties> consumer){
			consumer.accept(saplingProperties);
			return this;
		}

		public ProductiveCherryTypeBuilder changeWoodProperties(Consumer<BlockBehaviour.Properties> consumer){
			consumer.accept(woodProperties);
			return this;
		}

		public ProductiveCherryTypeBuilder changePetalItemProperties(Consumer<Item.Properties> consumer){
			consumer.accept(petalItemProperties);
			return this;
		}

		public ProductiveCherryTypeBuilder changeLeafItemProperties(Consumer<Item.Properties> consumer){
			consumer.accept(leafItemProperties);
			return this;
		}

		public ProductiveCherryTypeBuilder changeLogItemProperties(Consumer<Item.Properties> consumer){
			consumer.accept(logItemProperties);
			return this;
		}

		public ProductiveCherryTypeBuilder changeSaplingItemProperties(Consumer<Item.Properties> consumer){
			consumer.accept(saplingItemProperties);
			return this;
		}

		public ProductiveCherryTypeBuilder changeWoodItemProperties(Consumer<Item.Properties> consumer){
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
			RotatedPillarBlock log = (RotatedPillarBlock) ModBlocks.registerItem(id.withSuffix("_log"),
				RotatedPillarBlock::new,logProperties,logItemProperties);
			ProductivePetalsBlock petals = (ProductivePetalsBlock) ModBlocks.registerItem(id.withSuffix("_petals"),
				(p) -> new ProductivePetalsBlock(p, productiveCherryLoot),petalProperties,petalItemProperties);
			ProductiveLeafsBlock leafs = (ProductiveLeafsBlock) ModBlocks.registerItem(id.withSuffix("_leafs"),
				(p) -> new ProductiveLeafsBlock(0.1F, leafParticles, dropPetalsChance, petals, p),
				leafProperties,leafItemProperties);
			SaplingBlock sapling = (SaplingBlock) ModBlocks.registerItem(id.withSuffix("_sapling"),
				(p) -> new SaplingBlock(treeGrowerOverride==null?createDefaultTreeGrower():treeGrowerOverride,p),
				saplingProperties,saplingItemProperties);
			return new ProductiveCherryType(name,id,dropPetalsChance,producedResources,productiveCherryLoot,log,leafs,petals,sapling,treeFeatureKey);
		}

	}
}
