package io.github.tommyrobot666.productivecherrytrees.blocks;

import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.List;
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

	private static Block registerItem(Identifier id, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties,
									  BiFunction<Item.Properties, Block, Item> itemFactory, Item.Properties itemProperties) {
		Block block = register(id, factory, properties);
		// ModItems.register...
		throw new UnsupportedOperationException("Function not written");
	}

	public static Block TEST_LOG = registerI(Identifier.tryBuild(ID,"test_log"), RotatedPillarBlock::new,
		BlockBehaviour.Properties.of().sound(SoundType.WOOD).ignitedByLava()
			.mapColor((state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? MapColor.TERRACOTTA_WHITE:MapColor.TERRACOTTA_LIGHT_GREEN)));

	public static Block TEST_LEAFS = registerI(Identifier.tryBuild(ID,"test_leafs"),
		(p) -> new UntintedParticleLeavesBlock(0.1F,ParticleTypes.CHERRY_LEAVES, p),
		BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_LEAVES));

	public static Block TEST_LITTER = registerI(Identifier.tryBuild(ID,"test_litter"),
		(p) -> new ProductiveCherryLeafLitterBlock(p, List.of()),
		BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_PETALS).mapColor(MapColor.COLOR_PINK));

	public static void register(){}
}
