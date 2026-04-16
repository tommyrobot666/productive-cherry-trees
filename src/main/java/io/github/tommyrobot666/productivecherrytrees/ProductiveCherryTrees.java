package io.github.tommyrobot666.productivecherrytrees;

import io.github.tommyrobot666.productivecherrytrees.blocks.ModBlocks;
import io.github.tommyrobot666.productivecherrytrees.recipes.ModRecipeTypes;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProductiveCherryTrees implements ModInitializer {
	public static final String ID = "productive_cherry_trees";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Very productive fallen petals");
		ModBlocks.register();
		ModRecipeTypes.register();
	}
}
