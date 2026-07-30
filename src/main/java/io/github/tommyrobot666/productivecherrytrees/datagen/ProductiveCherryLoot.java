package io.github.tommyrobot666.productivecherrytrees.datagen;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductiveCherryLoot {
	public final List<ProducedResource> v = new ArrayList<>();
	public boolean dropSelf = false;

	public ProductiveCherryLoot with(double countChance, Item... items){
		for (Item item : items) {
			with(item,countChance);
		}
		return this;
	}

	public ProductiveCherryLoot with(Item item, double countChance){
		if (countChance<1){
			return with(item,1,countChance);
		} else {
			return with(item,((int) countChance),countChance);
		}
	}

	public ProductiveCherryLoot with(Item item, int count, double chance){
		v.add(new ProducedResource(item,count,chance,Optional.empty()));
		return this;
	}

	public ProductiveCherryLoot with(Item item, int count, double chance, DataComponentMap components){
		v.add(new ProducedResource(item,count,chance,Optional.of(components)));
		return this;
	}

	public ProductiveCherryLoot dropSelf() {
		dropSelf = true;
		return this;
	}

	public double totalValue(){
		double i = 0;
		for (ProducedResource resource : v) {
			i += resource.value();
		}
		return i;
	}

	public record ProducedResource(Item item, int count, double chance, Optional<DataComponentMap> components){
		public double value(){return chance*count;}
	}
}
