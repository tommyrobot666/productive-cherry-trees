package io.github.tommyrobot666.productivecherrytrees;

import io.github.tommyrobot666.productivecherrytrees.blocks.ModBlocks;
import io.github.tommyrobot666.productivecherrytrees.blocks.cherry.ProductiveCherryType;
import io.github.tommyrobot666.productivecherrytrees.items.ModTabs;
import io.github.tommyrobot666.productivecherrytrees.recipes.ModRecipeTypes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductiveCherryTrees implements ModInitializer {
	public static final String ID = "productive_cherry_trees";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);
	public static final ResourceKey<Registry<ProductiveCherryType>> CHERRY_TYPES_KEY = ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(ID,"cherry_types"));
	public static final Registry<ProductiveCherryType> CHERRY_TYPES = FabricRegistryBuilder.create(CHERRY_TYPES_KEY).buildAndRegister();

	@Override
	public void onInitialize() {
		LOGGER.info("Very productive fallen petals");
		ModBlocks.register();
		ModRecipeTypes.register();
		ModTabs.register();
	}
}
