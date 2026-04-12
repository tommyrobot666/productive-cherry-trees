package io.github.tommyrobot666.productivecherrytrees.blocks;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.LeafLitterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ProductivePetalsBlock extends LeafLitterBlock {
	/* If there are mods that add items that have different uses depending on stored data,
	 then change this from Item to ItemStack */
	public final List<Item> producedResources;

	public ProductivePetalsBlock(Properties properties, List<Item> producedResources) {
		super(properties);
		this.producedResources = producedResources;
	}

	// I'm too lazy to datagen loot tables
	@Override
	protected @NotNull List<ItemStack> getDrops(@NotNull BlockState state, LootParams.@NotNull Builder params) {
		if (params.getOptionalParameter(LootContextParams.TOOL).is(Items.SHEARS)){
			return List.of(new ItemStack(this));
		}

		ArrayList<ItemStack> drops = new ArrayList<>(super.getDrops(state, params));
		for (Item producedResource : producedResources) {
			// If producedResources is changed to ItemStack, use count as max for nextInt
			drops.add(new ItemStack(producedResource, params.getLevel().getRandom().nextInt(0,2)));
		}
		return drops;
	}
}
