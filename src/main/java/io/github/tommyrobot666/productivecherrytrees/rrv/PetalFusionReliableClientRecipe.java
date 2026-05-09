package io.github.tommyrobot666.productivecherrytrees.rrv;

import cc.cassian.rrv.api.recipe.ReliableClientRecipe;
import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewScreen;
import cc.cassian.rrv.common.recipe.inventory.SlotContent;
import io.github.tommyrobot666.productivecherrytrees.recipes.PetalFusionRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;

import java.util.List;

public class PetalFusionReliableClientRecipe implements ReliableClientRecipe {
	private final Identifier id;
	private final SlotContent baseItem, fusionItem, output;
	private final double chance;

	public PetalFusionReliableClientRecipe(Identifier id, PetalFusionRecipe recipe) {
		this.id = id;
		this.baseItem = SlotContent.of(recipe.getInputsAsItem().getFirst().display());
		this.fusionItem = SlotContent.of(recipe.getInputsAsItem().getLast().display());
		this.output = SlotContent.of(recipe.getOutput().asItem());
		this.chance = recipe.getChance();
	}

	@Override
	public void bindSlots(RecipeViewMenu.SlotFillContext c) {
		c.bindSlot(0,baseItem);
		c.bindSlot(1,fusionItem);
		c.bindSlot(2,baseItem);
	}

	@Override
	public List<SlotContent> getIngredients() {
		return List.of(baseItem,fusionItem);
	}

	@Override
	public List<SlotContent> getResults() {
		return List.of(output);
	}

	@Override
	public ReliableClientRecipeType getType() {
		return PetalFusionReliableClientRecipeType.INSTANCE;
	}

	@Override
	public Identifier getId() {
		return id;
	}

	@Override
	public void renderRecipe(RecipeViewScreen screen, RecipePosition recipePosition, GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float partialTicks) {
		guiGraphics.text(Minecraft.getInstance().font, "Fusion chance: "+chance,20,20,256*256*256*256);
	}
}
