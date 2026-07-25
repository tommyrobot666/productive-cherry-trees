package io.github.tommyrobot666.productivecherrytrees.blocks.cherry;

import io.github.tommyrobot666.productivecherrytrees.ProductiveCherryTrees;
import io.github.tommyrobot666.productivecherrytrees.blocks.ModBlocks;
import io.github.tommyrobot666.productivecherrytrees.datagen.ProductiveCherryLoot;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.material.MapColor;

import java.util.Optional;
import java.util.function.Consumer;

import static io.github.tommyrobot666.productivecherrytrees.ProductiveCherryTrees.ID;

public class ProductiveCherryType {
	public final String name;
	public final Identifier id;
	public final double dropPetalsChance;
	public final ProducedResources producedResources;
	public final DatagenSettings datagenSettings;
	public final RotatedPillarBlock log;
	public final ProductiveLeafsBlock leafs;
	public final ProductivePetalsBlock petals;
	public final SaplingBlock sapling;
	public final Block planks;
	public final SlabBlock slab;
	public final StairBlock stairs;
	public final FenceBlock fence;
	public final FenceGateBlock fenceGate;
	public final ButtonBlock button;
	public final PressurePlateBlock pressurePlate;
	/// Used for tree datagen

	public ProductiveCherryType(String name, Identifier id, double dropPetalsChance, ProducedResources producedResources, DatagenSettings datagenSettings, RotatedPillarBlock log, ProductiveLeafsBlock leafs, ProductivePetalsBlock petals, SaplingBlock sapling, Block planks, SlabBlock slab, StairBlock stairs, FenceBlock fence, FenceGateBlock fenceGate, ButtonBlock button, PressurePlateBlock pressurePlate) {
		this.name = name;
		this.id = id;
		this.dropPetalsChance = dropPetalsChance;
		this.producedResources = producedResources;
		this.datagenSettings = datagenSettings;
		this.log = log;
		this.leafs = leafs;
		this.petals = petals;
		this.sapling = sapling;
		this.planks = planks;
		this.slab = slab;
		this.stairs = stairs;
		this.fence = fence;
		this.fenceGate = fenceGate;
		this.button = button;
		this.pressurePlate = pressurePlate;
	}

	public static class Builder {
		/// Shown to user in en_us
		String name;
		/// Internal name
		Identifier id;
		ProducedResources producedResources = null;
		DatagenSettings datagenSettings = new DatagenSettings();
		BlockState petalsPlacedBlock = null;
		double dropPetalsChance = 1;
		Item.Properties petalItemProperties = new Item.Properties();
		Item.Properties leafItemProperties = new Item.Properties();
		Item.Properties logItemProperties = new Item.Properties();
		Item.Properties saplingItemProperties = new Item.Properties();
		Item.Properties woodItemProperties = new Item.Properties();
		BlockBehaviour.Properties petalProperties = BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_PETALS).strength(.3f);
		BlockBehaviour.Properties leafProperties = BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_LEAVES);
		BlockBehaviour.Properties logProperties = BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_LOG);
		BlockBehaviour.Properties saplingProperties = BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_SAPLING);
		BlockBehaviour.Properties woodProperties = BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_PLANKS);
		MapColor logSideColor = MapColor.TERRACOTTA_GRAY;
		MapColor logTopColor = MapColor.TERRACOTTA_WHITE;
		MapColor leafsColor = MapColor.COLOR_PINK;
		MapColor petalsColor = MapColor.PLANT;
		MapColor saplingColor = MapColor.COLOR_PINK;
		MapColor woodColor = MapColor.TERRACOTTA_WHITE;
		TreeGrower treeGrowerOverride;
		ParticleOptions leafParticles = ParticleTypes.CHERRY_LEAVES;

		public Builder(String id){
			this(id, Identifier.fromNamespaceAndPath(ID,id));
		}

		public Builder(String name, Identifier id) {
			this.name = name;
			this.id = id;
			datagenSettings.treeFeatureKey = ResourceKey.create(Registries.CONFIGURED_FEATURE,id);
		}

		public Builder changeDatagenSettings(Consumer<DatagenSettings> consumer){
			consumer.accept(datagenSettings);
			return this;
		}

		public Builder petalsPlaceBlock(BlockState placedBlock){
			this.petalsPlacedBlock = placedBlock;
			return this;
		}

		public Builder setProducedResources(ProducedResources producedResources){
			this.producedResources = producedResources;
			return this;
		}

		public Builder changeProducedResources(Consumer<ProducedResources> consumer){
			consumer.accept(producedResources);
			return this;
		}

		public Builder setProductiveCherryLoot(ProductiveCherryLoot productiveCherryLoot){
			this.datagenSettings.productiveCherryLoot = productiveCherryLoot;
			return this;
		}

		public Builder changeProductiveCherryLoot(Consumer<ProductiveCherryLoot> consumer){
			consumer.accept(datagenSettings.productiveCherryLoot);
			return this;
		}

		public Builder setDropPetalsChance(double dropPetalsChance){
			this.dropPetalsChance = dropPetalsChance;
			return this;
		}

		public Builder setTreeGrowerOverride(TreeGrower treeGrower){
			treeGrowerOverride = treeGrower;
			return this;
		}

		public Builder setTreeFeatureKey(ResourceKey<ConfiguredFeature<?,?>> key){
			datagenSettings.treeFeatureKey = key;
			return this;
		}

		public Builder setLeafParticles(ParticleOptions particleType){
			leafParticles = particleType;
			return this;
		}

		public Builder setLogSideColor(MapColor color){
			logSideColor = color;
			return this;
		}

		public Builder setLogTopColor(MapColor color){
			logTopColor = color;
			return this;
		}

		public Builder setLeafsColor(MapColor color){
			leafsColor = color;
			return this;
		}

		public Builder setPetalsColor(MapColor color){
			petalsColor = color;
			return this;
		}

		public Builder setSaplingColor(MapColor color){
			saplingColor = color;
			return this;
		}

		public Builder setWoodColor(MapColor color){
			woodColor = color;
			return this;
		}

		public Builder applyMapColors(){
			logProperties.mapColor((state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? logTopColor : logSideColor));
			petalProperties.mapColor(petalsColor);
			leafProperties.mapColor(leafsColor);
			saplingProperties.mapColor(saplingColor);
			woodProperties.mapColor(woodColor);
			return this;
		}

		public Builder changePetalProperties(Consumer<BlockBehaviour.Properties> consumer){
			consumer.accept(petalProperties);
			return this;
		}

		public Builder changeLeafProperties(Consumer<BlockBehaviour.Properties> consumer){
			consumer.accept(leafProperties);
			return this;
		}

		public Builder changeLogProperties(Consumer<BlockBehaviour.Properties> consumer){
			consumer.accept(logProperties);
			return this;
		}

		public Builder changeSaplingProperties(Consumer<BlockBehaviour.Properties> consumer){
			consumer.accept(saplingProperties);
			return this;
		}

		public Builder changeWoodProperties(Consumer<BlockBehaviour.Properties> consumer){
			consumer.accept(woodProperties);
			return this;
		}

		public Builder changePetalItemProperties(Consumer<Item.Properties> consumer){
			consumer.accept(petalItemProperties);
			return this;
		}

		public Builder changeLeafItemProperties(Consumer<Item.Properties> consumer){
			consumer.accept(leafItemProperties);
			return this;
		}

		public Builder changeLogItemProperties(Consumer<Item.Properties> consumer){
			consumer.accept(logItemProperties);
			return this;
		}

		public Builder changeSaplingItemProperties(Consumer<Item.Properties> consumer){
			consumer.accept(saplingItemProperties);
			return this;
		}

		public Builder changeWoodItemProperties(Consumer<Item.Properties> consumer){
			consumer.accept(woodItemProperties);
			return this;
		}

		TreeGrower createDefaultTreeGrower(){
			return new TreeGrower(
				id.getNamespace()+"_productive_cherry_tree",
				Optional.empty(),
				Optional.of(datagenSettings.treeFeatureKey),
				Optional.empty()
			);
		}

		public ProductiveCherryType buildAndRegister(){
			RotatedPillarBlock log = (RotatedPillarBlock) ModBlocks.registerWithItem(id.withSuffix("_log"),
				RotatedPillarBlock::new,logProperties, BlockItem::new,logItemProperties);
			ProductivePetalsBlock petals = (ProductivePetalsBlock) ModBlocks.registerWithItem(id.withSuffix("_petals"),
				(p) -> new ProductivePetalsBlock(p, petalsPlacedBlock),petalProperties,BlockItem::new,petalItemProperties);
			ProductiveLeafsBlock leafs = (ProductiveLeafsBlock) ModBlocks.registerWithItem(id.withSuffix("_leafs"),
				(p) -> new ProductiveLeafsBlock(0.1F, leafParticles, dropPetalsChance, petals, p),
				leafProperties,BlockItem::new,leafItemProperties);
			SaplingBlock sapling = (SaplingBlock) ModBlocks.registerWithItem(id.withSuffix("_sapling"),
				(p) -> new SaplingBlock(treeGrowerOverride==null?createDefaultTreeGrower():treeGrowerOverride,p),
				saplingProperties,BlockItem::new,saplingItemProperties);

			Block planks = ModBlocks.registerWithItem(id.withSuffix("_planks"),Block::new,
				woodProperties, BlockItem::new,woodItemProperties);
			SlabBlock slab = (SlabBlock) ModBlocks.registerWithItem(id.withSuffix("_slab"),SlabBlock::new,
				woodProperties, BlockItem::new,woodItemProperties);
			StairBlock stairs = (StairBlock) ModBlocks.registerWithItem(id.withSuffix("_stairs"),
				(p) -> new StairBlock(planks.defaultBlockState(),p),
				woodProperties, BlockItem::new,woodItemProperties);
			FenceGateBlock fenceGate = (FenceGateBlock) ModBlocks.registerWithItem(id.withSuffix("_fence_gate"),
				(p) -> new FenceGateBlock(WoodType.CHERRY,p),
				woodProperties, BlockItem::new,woodItemProperties);
			FenceBlock fence = (FenceBlock) ModBlocks.registerWithItem(id.withSuffix("_fence"),FenceBlock::new,
				woodProperties, BlockItem::new,woodItemProperties);
			ButtonBlock button = (ButtonBlock) ModBlocks.registerWithItem(id.withSuffix("_button"),
				(p) -> new ButtonBlock(BlockSetType.CHERRY,30,p),
				woodProperties, BlockItem::new,woodItemProperties);
			PressurePlateBlock pressurePlate = (PressurePlateBlock) ModBlocks.registerWithItem(id.withSuffix("_pressure_plate"),
				(p) -> new PressurePlateBlock(BlockSetType.CHERRY,p),
				woodProperties, BlockItem::new,woodItemProperties);

			ProductiveCherryType productiveCherryType = new ProductiveCherryType(
				name,id,dropPetalsChance,producedResources,datagenSettings,
				log,leafs,petals,sapling,planks,slab,stairs,fence,fenceGate,button,pressurePlate);
			Registry.register(ProductiveCherryTrees.CHERRY_TYPES,id,productiveCherryType);
			return productiveCherryType;
		}

	}

	public static class DatagenSettings {
		public ProductiveCherryLoot productiveCherryLoot = null;
		public boolean genLoot = true;
		public boolean genModels = true;
		public boolean genRecipes = true;
		public boolean genEnLang = true;
		public boolean genTree = true;
		public boolean genTags = true;
		public ResourceKey<ConfiguredFeature<?,?>> treeFeatureKey;
	}
}
