package io.github.tommyrobot666.productivecherrytrees.datagen;

import io.github.tommyrobot666.productivecherrytrees.blocks.ModBlocks;
import io.github.tommyrobot666.productivecherrytrees.blocks.ProductiveCherryType;
import io.github.tommyrobot666.productivecherrytrees.blocks.SaplingInfusionBlock;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.DelegatedModel;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.client.renderer.block.BuiltInBlockModels;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

import static io.github.tommyrobot666.productivecherrytrees.ProductiveCherryTrees.ID;

public class ModelProvider extends FabricModelProvider {
	public ModelProvider(FabricPackOutput output) {
		super(output);
	}

	void cherryModels(ProductiveCherryType type, BlockModelGenerators g){
		g.createTrivialBlock(type.log, TexturedModel.COLUMN);
		g.createLeafLitter(type.petals);
		g.createCrossBlockWithDefaultItem(type.sapling, BlockModelGenerators.PlantType.EMISSIVE_NOT_TINTED);
		g.createTrivialCube(type.leafs);
	}

	void createSaplingInfuser(BlockModelGenerators g){
		MultiVariant notCrafting = BlockModelGenerators.plainVariant(
			ModelLocationUtils.getModelLocation(ModBlocks.SAPLING_INFUSER));
		MultiVariant crafting = BlockModelGenerators.plainVariant(
			ModelLocationUtils.getModelLocation(ModBlocks.SAPLING_INFUSER,"_crafting")
		);
		g.blockStateOutput.accept(MultiVariantGenerator.dispatch(ModBlocks.SAPLING_INFUSER)
			.with(PropertyDispatch.initial(SaplingInfusionBlock.CRAFTING)
				.generate((c) -> c?crafting:notCrafting)));
	}

	@Override
	public void generateBlockStateModels(@NotNull BlockModelGenerators g) {
		cherryModels(ModBlocks.TEST_CHERRY,g);
		DataGen.genCherryDefaultAssets.forEach((t) -> cherryModels(t,g));

		createSaplingInfuser(g);
	}

	@Override
	public void generateItemModels(@NotNull ItemModelGenerators g) {

	}
}
