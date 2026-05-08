package io.github.tommyrobot666.productivecherrytrees;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProducedResources {
	public final List<ProducedResource> v = new ArrayList<>();
	public Block placedBlock = null;
	public boolean dropSelf = false;

	public ProducedResources with(double countChance, Item... items){
		for (Item item : items) {
			with(item,countChance);
		}
		return this;
	}

	public ProducedResources with(Item item, double countChance){
		if (countChance<1){
			return with(item,1,countChance);
		} else {
			return with(item,((int) countChance),countChance);
		}
	}

	public ProducedResources with(Item item, int count, double chance){
		v.add(new ProducedResource(item,count,chance,Optional.empty()));
		return this;
	}

	/** @noinspection unused*/
	public ProducedResources with(Item item, int count, double chance, DataComponentMap components){
		v.add(new ProducedResource(item,count,chance,Optional.of(components)));
		return this;
	}

	public ProducedResources placeBlock(Block placedBlock){
		this.placedBlock = placedBlock;
		return this;
	}

	public ProducedResources dropSelf() {
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
