package io.github.tommyrobot666.productivecherrytrees.recipes;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.ImpossibleTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import static io.github.tommyrobot666.productivecherrytrees.ProductiveCherryTrees.ID;

public class PetalFusionRecipeBuilder implements RecipeBuilder {
	final Block original;
	final Block combining;
	final Block output;
	String group = "";
	final double chance;
	RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();

	public PetalFusionRecipeBuilder(Block original, Block combining, Block output, double chance){
		this.original = original;
		this.combining = combining;
		this.output = output;
		this.chance = chance;
	}

	@Override
	public @NotNull RecipeBuilder unlockedBy(@NotNull String name, @NotNull Criterion<?> criterion) {
		advancementBuilder.unlockedBy(name, criterion);
		return this;
	}

	@Override
	public @NotNull RecipeBuilder group(@Nullable String group) {
		this.group = group;
		return this;
	}

	@Override
	public @NotNull ResourceKey<Recipe<?>> defaultId() {
		return ResourceKey.create(Registries.RECIPE, Identifier.fromNamespaceAndPath(
			ID,//BuiltInRegistries.BLOCK.getKey(output).getNamespace(),
			BuiltInRegistries.BLOCK.getKey(original).getPath()+"_and_"
				+BuiltInRegistries.BLOCK.getKey(combining).getPath()+"_to_"
				+BuiltInRegistries.BLOCK.getKey(output).getPath()
		));
	}

	@Override
	public void save(RecipeOutput output, @NotNull ResourceKey<Recipe<?>> id) {
		advancementBuilder.unlockedBy("impossible_trigger", CriteriaTriggers.IMPOSSIBLE.createCriterion(new ImpossibleTrigger.TriggerInstance()));
		output.accept(id, new PetalFusionRecipe(original,combining,this.output,group,chance), advancementBuilder.build(output,id, RecipeCategory.MISC));
	}
}
