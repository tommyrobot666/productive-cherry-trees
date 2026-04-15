package io.github.tommyrobot666.productivecherrytrees.blocks;

import io.github.tommyrobot666.productivecherrytrees.ItemOrStackList;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

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

	private static ProductiveCherryType registerCherry(String id, Supplier<List<ItemStack>> producedResources, MapColor logSideColor, MapColor logTopColor, MapColor leafsColor, MapColor petalsColor) {
		Block log = registerI(Identifier.tryBuild(ID, id+"_log"), RotatedPillarBlock::new,
			BlockBehaviour.Properties.of().sound(SoundType.WOOD).ignitedByLava()
				.mapColor((state -> state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? logTopColor : logSideColor)));
		Block leafs = registerI(Identifier.tryBuild(ID, id+"_leafs"),
			(p) -> new UntintedParticleLeavesBlock(0.1F, ParticleTypes.CHERRY_LEAVES, p),
			BlockBehaviour.Properties.ofFullCopy(Blocks.CHERRY_LEAVES).mapColor(leafsColor));
		Block petals = registerI(Identifier.tryBuild(ID, id+"_petals"),
			(p) -> new ProductivePetalsBlock(p, producedResources),
			BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_PETALS).mapColor(petalsColor));
		TreeGrower treeGrower = new TreeGrower(
			id+"_productive_cherry_tree",
			Optional.empty(),
			Optional.of(ResourceKey.create(Registries.CONFIGURED_FEATURE,
				Identifier.tryBuild(ID,id+"_productive_cherry_tree"))),
			Optional.empty()
		);
		Block sapling = registerI(Identifier.tryBuild(ID, id+"_sapling"),
			(p) -> new SaplingBlock(treeGrower,p),
			BlockBehaviour.Properties.ofFullCopy(Blocks.PINK_PETALS).mapColor(petalsColor));
		return new ProductiveCherryType(log,leafs, (ProductivePetalsBlock) petals,sapling,producedResources,id);
	}

	public static final ProductiveCherryType TEST_CHERRY = registerCherry("test",() -> new ItemOrStackList().with(Items.PINK_CONCRETE,2),
		MapColor.TERRACOTTA_WHITE,MapColor.COLOR_LIGHT_GREEN,MapColor.COLOR_PINK,MapColor.COLOR_PINK);

	public static void register(){}
}
