package io.github.tommyrobot666.productivecherrytrees.blocks;

import io.github.tommyrobot666.productivecherrytrees.ProducedResources;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

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

	/** @noinspection unused, unused , unused */
	private static Block registerItem(Identifier id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties,
									  BiFunction<Item.Properties, Block, Item> itemFactory, Item.Properties itemProperties) {
		Block block = register(id, factory, properties);
		// ModItems.register...
		throw new UnsupportedOperationException("Function not written");
	}


	public static final Block SAPLING_INFUSER = registerI(
		Identifier.fromNamespaceAndPath(ID,"sapling_infusion"),
		SaplingInfusionBlock::new,
		BlockBehaviour.Properties.of()
			.mapColor(MapColor.GOLD).instrument(NoteBlockInstrument.BELL)
			.strength(3.0F, 6.0F).sound(SoundType.METAL));

	/** @noinspection SameParameterValue, SameParameterValue , SameParameterValue , SameParameterValue */
	private static ProductiveCherryType registerCherry(String id, ProducedResources producedResources, double dropPetalsChance, MapColor logSideColor, MapColor logTopColor, MapColor leafsColor, MapColor petalsColor) {
		Block log = registerI(Identifier.tryBuild(ID, id+"_log"), RotatedPillarBlock::new,
			BlockBehaviour.Properties.of().sound(SoundType.WOOD).ignitedByLava().strength(2f)
				.mapColor((state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? logTopColor : logSideColor)));
		ProductivePetalsBlock petals = (ProductivePetalsBlock) registerI(Identifier.tryBuild(ID, id+"_petals"),
			(p) -> new ProductivePetalsBlock(p, producedResources),
			BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_PETALS).strength(.3f).mapColor(petalsColor));
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
		new ProducedResources().with(Items.RAW_GOLD,3).with(Items.GOLD_INGOT,1).with(Items.GOLD_NUGGET,5),
		0.05f,MapColor.GOLD,MapColor.TERRACOTTA_WHITE,MapColor.GOLD,MapColor.GOLD);

	public static void register(){}
}
