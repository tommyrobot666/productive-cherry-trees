package io.github.tommyrobot666.productivecherrytrees.items;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.function.Function;

public class ModItems {
	public static Item register(Identifier id, Function<Item.Properties, Item> factory, Item.Properties properties) {
		Item item = factory.apply(properties.setId(ResourceKey.create(Registries.ITEM,id)));
		return Registry.register(BuiltInRegistries.ITEM, id, item);
	}

	public static void register(){}
}
