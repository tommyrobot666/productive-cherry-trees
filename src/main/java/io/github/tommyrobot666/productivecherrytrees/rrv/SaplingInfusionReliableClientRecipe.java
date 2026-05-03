package io.github.tommyrobot666.productivecherrytrees.rrv;

import cc.cassian.rrv.api.recipe.ReliableClientRecipe;
import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import cc.cassian.rrv.common.recipe.inventory.SlotContent;
import io.github.tommyrobot666.productivecherrytrees.recipes.SaplingInfusionRecipe;
import net.minecraft.resources.Identifier;

import java.util.List;

public class SaplingInfusionReliableClientRecipe implements ReliableClientRecipe {
	private final Identifier id;
	private final SlotContent sapling, petals, output;

	public SaplingInfusionReliableClientRecipe(Identifier id, SaplingInfusionRecipe recipe) {
		this.id = id;
		this.sapling = SlotContent.of(recipe.getInputsAsItem()[0].display());
		this.petals = SlotContent.of(recipe.getInputsAsItem()[1].display());
		this.output = SlotContent.of(recipe.getOutput().asItem());
	}

	@Override
	public ReliableClientRecipeType getType() {
		return SaplingInfusionReliableClientRecipeType.INSTANCE;
	}

	@Override
	public void bindSlots(RecipeViewMenu.SlotFillContext c) {
		c.bindSlot(0,sapling);
		c.bindSlot(1,petals);
		c.bindSlot(2,output);
	}

	@Override
	public List<SlotContent> getIngredients() {
		return List.of(sapling,petals);
	}

	@Override
	public List<SlotContent> getResults() {
		return List.of(output);
	}

	@Override
	public Identifier getId() {
		return id;
	}
}
