package io.github.tommyrobot666.productivecherrytrees.rrv;

import cc.cassian.rrv.api.ReliableRecipeViewerClientPlugin;
import cc.cassian.rrv.api.recipe.ItemView;
import cc.cassian.rrv.client.recipe.ClientRecipeManager;
import io.github.tommyrobot666.productivecherrytrees.recipes.ModRecipeTypes;

public class ModClientIntegration implements ReliableRecipeViewerClientPlugin {
	@Override
	public void onIntegrationInitialize() {
		ItemView.addClientRecipeProvider(rls ->
			ClientRecipeManager.INSTANCE.getRecipesForType(ModRecipeTypes.PETAL_FUSION_TYPE)
				.forEach((r) -> rls.add(
					new PetalFusionReliableClientRecipe(r.id().identifier(), r.value())
				)));
		ItemView.addClientRecipeProvider(rls ->
			ClientRecipeManager.INSTANCE.getRecipesForType(ModRecipeTypes.SAPLING_INFUSION_TYPE)
				.forEach((r) -> rls.add(
					new SaplingInfusionReliableClientRecipe(r.id().identifier(), r.value())
				)));
	}
}
