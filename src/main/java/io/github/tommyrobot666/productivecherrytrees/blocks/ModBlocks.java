package io.github.tommyrobot666.productivecherrytrees.blocks;

import io.github.tommyrobot666.productivecherrytrees.blocks.cherry.ModCherryEssences;
import io.github.tommyrobot666.productivecherrytrees.blocks.cherry.ProducedResources;
import io.github.tommyrobot666.productivecherrytrees.blocks.cherry.ProductiveCherryType;
import io.github.tommyrobot666.productivecherrytrees.datagen.ProductiveCherryLoot;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
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
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import static io.github.tommyrobot666.productivecherrytrees.ProductiveCherryTrees.ID;

public class ModBlocks {
	private static Block register(Identifier id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties) {
		Block block = factory.apply(properties.setId(ResourceKey.create(BuiltInRegistries.BLOCK.key(),id)));
		return Registry.register(BuiltInRegistries.BLOCK, id, block);
	}

	private static Block registerWithItem(Identifier id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties) {
		Block block = register(id, factory, properties);
		Registry.register(BuiltInRegistries.ITEM, id, new BlockItem(block, new Item.Properties().setId(ResourceKey.create(BuiltInRegistries.ITEM.key(),id))));
		return block;
	}

	public static Block registerWithItem(Identifier id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties, BiFunction<Block, Item.Properties, Item> itemFactory, Item.Properties itemProperties) {
		Block block = register(id, factory, properties);
		Item item = itemFactory.apply(block,itemProperties.setId(ResourceKey.create(Registries.ITEM,id)));
		Registry.register(BuiltInRegistries.ITEM, id, item);
		return block;
	}

	private static <T extends BlockEntity> BlockEntityType<T> registerEntity(Identifier id, FabricBlockEntityTypeBuilder.Factory<T> factory,Block... blocks){
		return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE,id,
			FabricBlockEntityTypeBuilder.create(factory,blocks).build());
	}


	public static final Block SAPLING_INFUSER = registerWithItem(
		Identifier.fromNamespaceAndPath(ID,"sapling_infusion"),
		SaplingInfusionBlock::new,
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.COLOR_GRAY).instrument(NoteBlockInstrument.BELL)
			.strength(3.0F, 6.0F).sound(SoundType.METAL));

	public static final BlockEntityType<@NotNull SaplingInfusionBlockEntity> SAPLING_INFUSER_ENTITY =
		registerEntity(Identifier.fromNamespaceAndPath(ID,"sapling_infusion"),
			SaplingInfusionBlockEntity::new,
			SAPLING_INFUSER);

	public static final ProductiveCherryType TEST_CHERRY = new ProductiveCherryType.Builder("test")
		.setProductiveCherryLoot(new ProductiveCherryLoot().with(Items.PINK_CONCRETE,2))
		.setProducedResources(new ProducedResources(List.of(ModCherryEssences.TRUE_MAGIC_OF_ULTIMATE_POWER),List.of()))
		.setDropPetalsChance(.1f)
		.setLogSideColor(MapColor.TERRACOTTA_WHITE)
		.setLogTopColor(MapColor.COLOR_LIGHT_GREEN)
		.setLeafsColor(MapColor.COLOR_PINK)
		.setPetalsColor(MapColor.COLOR_PINK)
		.applyMapColors()
		.buildAndRegister();

	public static final ProductiveCherryType GOLD_CHERRY = new ProductiveCherryType.Builder("gold")
		.setProductiveCherryLoot(new ProductiveCherryLoot().with(Items.RAW_GOLD,3,0.3).with(Items.GOLD_INGOT,.01).with(Items.GOLD_NUGGET,7))
		.setProducedResources(new ProducedResources(List.of(ModCherryEssences.GOLD),List.of()))
		.setDropPetalsChance(.05f)
		.setLogSideColor(MapColor.GOLD)
		.setLogTopColor(MapColor.TERRACOTTA_WHITE)
		.setLeafsColor(MapColor.GOLD)
		.setPetalsColor(MapColor.GOLD)
		.applyMapColors()
		.buildAndRegister();

	public static final ProductiveCherryType STONE_CHERRY = new ProductiveCherryType.Builder("stone")
		.setProductiveCherryLoot(new ProductiveCherryLoot().with(Items.COBBLESTONE,5).with(Items.STONE,3,1.7)
			.with(Items.COBBLED_DEEPSLATE,.1).with(Items.GOLD_NUGGET,.01))
		.setProducedResources(new ProducedResources(List.of(ModCherryEssences.STONE),List.of()))
		.setDropPetalsChance(.13f)
		.setLogSideColor(MapColor.TERRACOTTA_GRAY)
		.setLogTopColor(MapColor.COLOR_GRAY)
		.setLeafsColor(MapColor.COLOR_LIGHT_GRAY)
		.setPetalsColor(MapColor.COLOR_LIGHT_GRAY)
		.applyMapColors()
		.buildAndRegister();

	public static final ProductiveCherryType FIRE_CHERRY = new ProductiveCherryType.Builder("fire")
		.setProductiveCherryLoot(new ProductiveCherryLoot().dropSelf())
		.changePetalItemProperties(Item.Properties::fireResistant)
		.petalsPlaceBlock(Blocks.FIRE.defaultBlockState())
		.setProducedResources(new ProducedResources(List.of(ModCherryEssences.FIRE),List.of()))
		.setDropPetalsChance(.09f)
		.setLogSideColor(MapColor.FIRE)
		.setLogTopColor(MapColor.COLOR_GRAY)
		.setLeafsColor(MapColor.FIRE)
		.setPetalsColor(MapColor.FIRE)
		.applyMapColors()
		.buildAndRegister();

	public static final ProductiveCherryType WATER_CHERRY = new ProductiveCherryType.Builder("water")
		.setProductiveCherryLoot(new ProductiveCherryLoot().with(Items.POTION,3,57,
				DataComponentMap.builder().set(DataComponents.MAX_STACK_SIZE,64)
					.set(DataComponents.POTION_CONTENTS,new PotionContents(Potions.WATER))
					.set(DataComponents.CUSTOM_NAME,
						Component.translatableWithFallback("item."+ID+".compressed_bottle","Water Bottle With Higher Stack Size")).build())
			.with(0.1,Items.BUBBLE_CORAL_FAN,Items.BRAIN_CORAL_FAN,Items.FIRE_CORAL_FAN,Items.HORN_CORAL_FAN,Items.TUBE_CORAL_FAN))
		.changePetalItemProperties(Item.Properties::fireResistant)
		.setProducedResources(new ProducedResources(List.of(ModCherryEssences.WATER),List.of()))
		.setDropPetalsChance(.09f)
		.setLogSideColor(MapColor.WATER)
		.setLogTopColor(MapColor.WATER)
		.setLeafsColor(MapColor.WATER)
		.setPetalsColor(MapColor.WATER)
		.applyMapColors()
		.buildAndRegister();

	public static void register(){}
}
