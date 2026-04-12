package io.github.tommyrobot666.productivecherrytrees.datagen;

import io.github.tommyrobot666.productivecherrytrees.blocks.ModBlocks;
import io.github.tommyrobot666.productivecherrytrees.blocks.ProductiveCherryType;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.TexturedModel;
import org.jetbrains.annotations.NotNull;

public class ModelProvider extends FabricModelProvider {
	public ModelProvider(FabricPackOutput output) {
		super(output);
	}

	void cherryModels(ProductiveCherryType type, BlockModelGenerators g){
		g.createTrivialBlock(type.log, TexturedModel.COLUMN);
//		g.createFlowerBed(type.petals);
		g.createLeafLitter(type.petals);
		g.createCrossBlock(type.sapling, BlockModelGenerators.PlantType.EMISSIVE_NOT_TINTED);
		g.createTrivialCube(type.leafs);
	}

	@Override
	public void generateBlockStateModels(@NotNull BlockModelGenerators g) {
		cherryModels(ModBlocks.TEST_CHERRY,g);
	}

	@Override
	public void generateItemModels(@NotNull ItemModelGenerators g) {

	}
}
