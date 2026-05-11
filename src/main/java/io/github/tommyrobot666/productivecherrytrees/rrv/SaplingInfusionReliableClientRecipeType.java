package io.github.tommyrobot666.productivecherrytrees.rrv;

import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import io.github.tommyrobot666.productivecherrytrees.blocks.ModBlocks;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.List;

import static io.github.tommyrobot666.productivecherrytrees.ProductiveCherryTrees.ID;

public class SaplingInfusionReliableClientRecipeType implements ReliableClientRecipeType {
	public final static SaplingInfusionReliableClientRecipeType INSTANCE = new SaplingInfusionReliableClientRecipeType();


	@Override
	public Component getDisplayName() {
		return Component.translatableWithFallback(ID+".rrv.sapling_infusion","Sapling Infusion");
	}

	@Override
	public int getDisplayWidth() {
		return 90;
	}

	@Override
	public int getDisplayHeight() {
		return 50;
	}

	@Override
	public @Nullable Identifier getGuiTexture() {
		return Identifier.fromNamespaceAndPath(ID,"textures/gui/type/sapling_infusion.png");
	}

	@Override
	public int getSlotCount() {
		return 3;
	}

	@Override
	public void placeSlots(RecipeViewMenu.SlotDefinition sd) {
		sd.addItemSlot(0,30,10);
		sd.addItemSlot(1,25-10,15+10);
		sd.addItemSlot(2,60,20);
	}

	@Override
	public Identifier getId() {
		return Identifier.fromNamespaceAndPath(ID, "sapling_infusion");
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(ModBlocks.SAPLING_INFUSER.asItem());
	}

	@Override
	public List<ItemStack> getCraftReferences() {
		return List.of(new ItemStack(ModBlocks.SAPLING_INFUSER.asItem()));
	}
}
