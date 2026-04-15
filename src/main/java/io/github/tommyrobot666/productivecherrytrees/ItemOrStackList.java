package io.github.tommyrobot666.productivecherrytrees;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

public class ItemOrStackList extends ArrayList<ItemStack> {
	public ItemOrStackList with(Item item, int count){
		add(item.getDefaultInstance().copyWithCount(count));
		return this;
	}

	public ItemOrStackList with(ItemStack item){
		add(item);
		return this;
	}
}
