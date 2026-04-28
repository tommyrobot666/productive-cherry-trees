package io.github.tommyrobot666.productivecherrytrees.items;

import io.github.tommyrobot666.productivecherrytrees.blocks.ModBlocks;
import io.github.tommyrobot666.productivecherrytrees.blocks.ProductiveCherryType;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import static io.github.tommyrobot666.productivecherrytrees.ProductiveCherryTrees.ID;

public class ModTabs {
	static void addCherryItems(ProductiveCherryType type, CreativeModeTab.Output o){
		o.accept(type.sapling);
		o.accept(type.petals);
		o.accept(type.leafs);
		o.accept(type.log);
	}

	public static final CreativeModeTab TAB = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
		Identifier.fromNamespaceAndPath(ID,"the_tab"),
		FabricCreativeModeTab.builder()
		.icon(() -> new ItemStack(ModBlocks.TEST_CHERRY.sapling.asItem()))
		.title(Component.translatable("tab."+ID))
		.alignedRight()
		.displayItems((p,o) ->{
			addCherryItems(ModBlocks.TEST_CHERRY,o);
			addCherryItems(ModBlocks.STONE_CHERRY,o);
			addCherryItems(ModBlocks.GOLD_CHERRY,o);
			addCherryItems(ModBlocks.FIRE_CHERRY,o);
			addCherryItems(ModBlocks.WATER_CHERRY,o);
			o.accept(ModBlocks.SAPLING_INFUSER);
		}).build());


	public static void register(){}
}
