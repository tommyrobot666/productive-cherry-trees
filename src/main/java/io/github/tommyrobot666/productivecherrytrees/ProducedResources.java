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

	public ProducedResources with(Item item, int count){
		v.add(new ProducedResource(item,count,Optional.empty()));
		return this;
	}

	/** @noinspection unused*/
	public ProducedResources with(Item item, int count, DataComponentMap components){
		v.add(new ProducedResource(item,count, Optional.of(components)));
		return this;
	}

	public ProducedResources placeBlock(Block placedBlock){
		this.placedBlock = placedBlock;
		return this;
	}

	public record ProducedResource(Item item, int count, Optional<DataComponentMap> components){}
}
