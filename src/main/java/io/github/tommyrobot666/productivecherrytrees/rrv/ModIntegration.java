package io.github.tommyrobot666.productivecherrytrees.rrv;

import cc.cassian.rrv.api.ReliableRecipeViewerPlugin;
import cc.cassian.rrv.common.recipe.ServerRecipeManager;
import io.github.tommyrobot666.productivecherrytrees.recipes.ModRecipeTypes;
import io.github.tommyrobot666.productivecherrytrees.recipes.PetalFusionRecipe;
import io.github.tommyrobot666.productivecherrytrees.recipes.SaplingInfusionRecipe;

public class ModIntegration implements ReliableRecipeViewerPlugin {
	@Override
	public void onIntegrationInitialize() {
		ServerRecipeManager.INSTANCE.synchronizeRecipeType(PetalFusionRecipe.SERIALIZER, ModRecipeTypes.PETAL_FUSION_TYPE);
		ServerRecipeManager.INSTANCE.synchronizeRecipeType(SaplingInfusionRecipe.SERIALIZER, ModRecipeTypes.SAPLING_INFUSION_TYPE);
	}
}
