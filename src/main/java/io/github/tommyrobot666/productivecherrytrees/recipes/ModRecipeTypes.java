package io.github.tommyrobot666.productivecherrytrees.recipes;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.NotNull;

import static io.github.tommyrobot666.productivecherrytrees.ProductiveCherryTrees.ID;

public class ModRecipeTypes {

	public static final RecipeType<@NotNull PetalFusionRecipe> PETAL_FUSION_TYPE =
		Registry.register(BuiltInRegistries.RECIPE_TYPE,
			Identifier.fromNamespaceAndPath(ID, "petal_fusion"),
			new RecipeType<@NotNull PetalFusionRecipe>() {
				@Override
				public String toString() {
					return "petal_fusion";
				}
			});

	public static void register(){
		Registry.register(BuiltInRegistries.RECIPE_SERIALIZER,
			Identifier.fromNamespaceAndPath(ID, "petal_fusion"),
			PetalFusionRecipe.SERIALIZER);
	}
}
