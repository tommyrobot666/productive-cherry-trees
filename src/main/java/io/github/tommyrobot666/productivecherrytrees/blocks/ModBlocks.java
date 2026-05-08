package io.github.tommyrobot666.productivecherrytrees.blocks;

import io.github.tommyrobot666.productivecherrytrees.ProducedResources;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import static io.github.tommyrobot666.productivecherrytrees.ProductiveCherryTrees.ID;

public class ModBlocks {
	private static Block register(Identifier id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties) {
		Block block = factory.apply(properties.setId(ResourceKey.create(BuiltInRegistries.BLOCK.key(),id)));
		return Registry.register(BuiltInRegistries.BLOCK, id, block);
	}

	private static Block registerI(Identifier id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties) {
		Block block = register(id, factory, properties);
		Registry.register(BuiltInRegistries.ITEM, id, new BlockItem(block, new Item.Properties().setId(ResourceKey.create(BuiltInRegistries.ITEM.key(),id))));
		return block;
	}

	private static Block registerI(Identifier id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties, Item.Properties itemProperties) {
		Block block = register(id, factory, properties);
		Registry.register(BuiltInRegistries.ITEM, id, new BlockItem(block, itemProperties.setId(ResourceKey.create(BuiltInRegistries.ITEM.key(),id))));
		return block;
	}

	/** @noinspection unused, unused , unused */
	private static Block registerItem(Identifier id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties,
									  BiFunction<Item.Properties, Block, Item> itemFactory, Item.Properties itemProperties) {
		Block block = register(id, factory, properties);
		// ModItems.register...
		throw new UnsupportedOperationException("Function not written");
	}

	private static <T extends BlockEntity> BlockEntityType<T> registerEntity(Identifier id, FabricBlockEntityTypeBuilder.Factory<T> factory,Block... blocks){
		return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,id,
			FabricBlockEntityTypeBuilder.create(factory,blocks).build());
	}


	public static final Block SAPLING_INFUSER = registerI(
		Identifier.fromNamespaceAndPath(ID,"sapling_infusion"),
		SaplingInfusionBlock::new,
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.GOLD).instrument(NoteBlockInstrument.BELL)
			.strength(3.0F, 6.0F).sound(SoundType.METAL));

	public static final BlockEntityType<@NotNull SaplingInfusionBlockEntity> SAPLING_INFUSER_ENTITY =
		registerEntity(Identifier.fromNamespaceAndPath(ID,"sapling_infusion"),
			SaplingInfusionBlockEntity::new,
			SAPLING_INFUSER);

	private static ProductiveCherryType registerCherry(String id, ProducedResources producedResources, double dropPetalsChance, MapColor logSideColor, MapColor logTopColor, MapColor leafsColor, MapColor petalsColor) {
		return registerCherry(id,producedResources,dropPetalsChance,new Item.Properties(),logSideColor,logTopColor,leafsColor,petalsColor);
	}

	private static ProductiveCherryType registerCherry(String id, ProducedResources producedResources, double dropPetalsChance, Item.Properties petalProperties, MapColor logSideColor, MapColor logTopColor, MapColor leafsColor, MapColor petalsColor) {
		Block log = registerI(Identifier.tryBuild(ID, id+"_log"), RotatedPillarBlock::new,
			BlockBehaviour.Properties.of().sound(SoundType.WOOD).ignitedByLava().strength(2f)
				.mapColor((state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? logTopColor : logSideColor)));
		ProductivePetalsBlock petals = (ProductivePetalsBlock) registerI(Identifier.tryBuild(ID, id+"_petals"),
			(p) -> new ProductivePetalsBlock(p, producedResources),
			BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_PETALS).strength(.3f).mapColor(petalsColor),petalProperties);
		Block leafs = registerI(Identifier.tryBuild(ID, id+"_leafs"),
			(p) -> new ProductiveLeafsBlock(0.1F, ParticleTypes.CHERRY_LEAVES, dropPetalsChance, petals, p),
			BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_LEAVES).mapColor(leafsColor).randomTicks());
		TreeGrower treeGrower = new TreeGrower(
			id+"_productive_cherry_tree",
			Optional.empty(),
			Optional.of(ResourceKey.create(Registries.CONFIGURED_FEATURE,
				Identifier.fromNamespaceAndPath(ID,id+"_productive_cherry_tree"))),
			Optional.empty()
		);
		Block sapling = registerI(Identifier.tryBuild(ID, id+"_sapling"),
			(p) -> new SaplingBlock(treeGrower,p),
			BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_SAPLING).mapColor(petalsColor));
		return new ProductiveCherryType(log,leafs, petals,sapling,producedResources,id);
	}

	public static final ProductiveCherryType TEST_CHERRY = registerCherry("test",new ProducedResources().with(Items.PINK_CONCRETE,2),
		0.1f,MapColor.TERRACOTTA_WHITE,MapColor.COLOR_LIGHT_GREEN,MapColor.COLOR_PINK,MapColor.COLOR_PINK);

	public static final ProductiveCherryType GOLD_CHERRY = registerCherry("gold",
		new ProducedResources().with(Items.RAW_GOLD,3,0.3).with(Items.GOLD_INGOT,.01).with(Items.GOLD_NUGGET,7),
		0.05f,MapColor.GOLD,MapColor.TERRACOTTA_WHITE,MapColor.GOLD,MapColor.GOLD);

	public static final ProductiveCherryType STONE_CHERRY = registerCherry("stone",
		new ProducedResources().with(Items.COBBLESTONE,5).with(Items.STONE,3,1.7)
			.with(Items.COBBLED_DEEPSLATE,.1).with(Items.GOLD_NUGGET,.01),
		0.13f,MapColor.TERRACOTTA_GRAY,MapColor.COLOR_GRAY,MapColor.COLOR_LIGHT_GRAY,MapColor.COLOR_LIGHT_GRAY);

	public static final ProductiveCherryType FIRE_CHERRY = registerCherry("fire",
		new ProducedResources().placeBlock(Blocks.FIRE).dropSelf(),0.09f,
		new Item.Properties().fireResistant(),MapColor.FIRE,MapColor.COLOR_GRAY,MapColor.FIRE,MapColor.FIRE);

	public static final ProductiveCherryType WATER_CHERRY = registerCherry("water",
		new ProducedResources().with(Items.POTION,3,57,
				DataComponentMap.builder().set(DataComponents.MAX_STACK_SIZE,64)
					.set(DataComponents.POTION_CONTENTS,new PotionContents(Potions.WATER))
					.set(DataComponents.CUSTOM_NAME,
						Component.translatableWithFallback("item."+ID+".compressed_bottle","Water Bottle With Higher Stack Size")).build())
			.with(0.1,Items.BUBBLE_CORAL_FAN,Items.BRAIN_CORAL_FAN,Items.FIRE_CORAL_FAN,Items.HORN_CORAL_FAN,Items.TUBE_CORAL_FAN),
		0.09f,MapColor.WATER,MapColor.WATER,MapColor.WATER,MapColor.WATER);

	public static void register(){}
}
