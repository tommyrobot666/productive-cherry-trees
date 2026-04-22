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

public class SaplingInfusionRecipeBuilder implements RecipeBuilder {
	final Block sapling;
	final Block petals;
	final Block output;
	String group = "";
	RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();

	public SaplingInfusionRecipeBuilder(Block sapling, Block petals, Block output){
		this.sapling = sapling;
		this.petals = petals;
		this.output = output;
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
			BuiltInRegistries.BLOCK.getKey(output).getNamespace(),
			BuiltInRegistries.BLOCK.getKey(sapling).getPath()+"_and_"
				+BuiltInRegistries.BLOCK.getKey(petals).getPath()+"_to_"
				+BuiltInRegistries.BLOCK.getKey(output).getPath()
		));
	}

	@Override
	public void save(RecipeOutput output, @NotNull ResourceKey<Recipe<?>> id) {
		advancementBuilder.unlockedBy("impossible_trigger", CriteriaTriggers.IMPOSSIBLE.createCriterion(new ImpossibleTrigger.TriggerInstance()));
		output.accept(id, new SaplingInfusionRecipe(sapling, petals,this.output,group), advancementBuilder.build(output,id, RecipeCategory.MISC));
	}
}
