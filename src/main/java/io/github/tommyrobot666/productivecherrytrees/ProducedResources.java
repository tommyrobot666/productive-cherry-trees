package io.github.tommyrobot666.productivecherrytrees;

import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProducedResources {
	public List<ProducedResource> v = new ArrayList<>();

	public ProducedResources with(Item item, int count){
		v.add(new ProducedResource(item,count,Optional.empty()));
		return this;
	}

	public ProducedResources with(Item item, int count, DataComponentMap components){
		v.add(new ProducedResource(item,count, Optional.of(components)));
		return this;
	}

	public record ProducedResource(Item item, int count, Optional<DataComponentMap> components){}
}
