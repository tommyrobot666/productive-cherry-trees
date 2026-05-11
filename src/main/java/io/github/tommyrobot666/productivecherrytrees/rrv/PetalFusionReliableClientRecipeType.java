package io.github.tommyrobot666.productivecherrytrees.rrv;

import cc.cassian.rrv.api.recipe.ReliableClientRecipeType;
import cc.cassian.rrv.common.recipe.inventory.RecipeViewMenu;
import io.github.tommyrobot666.productivecherrytrees.blocks.ModBlocks;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

import static io.github.tommyrobot666.productivecherrytrees.ProductiveCherryTrees.ID;

public class PetalFusionReliableClientRecipeType implements ReliableClientRecipeType {
	public final static PetalFusionReliableClientRecipeType INSTANCE = new PetalFusionReliableClientRecipeType();

	@Override
	public Component getDisplayName() {
		return Component.translatableWithFallback(ID+".rrv.petal_fusion","Petal Fusion");
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
		return Identifier.fromNamespaceAndPath(ID,"textures/gui/type/petal_fusion.png");
	}

	@Override
	public int getSlotCount() {
		return 3;
	}

	@Override
	public void placeSlots(RecipeViewMenu.SlotDefinition sd) {
		sd.addItemSlot(0,30,10);
		sd.addItemSlot(1,30,30);
		sd.addItemSlot(2,70,20);
 	}

	@Override
	public Identifier getId() {
		return Identifier.fromNamespaceAndPath(ID, "petal_fusion");
	}

	@Override
	public ItemStack getIcon() {
		return new ItemStack(ModBlocks.TEST_CHERRY.petals);
	}

	// Causes an list index error somewhere?
	@Override
	public List<ItemStack> getCraftReferences() {
		return Stream.of(ModBlocks.TEST_CHERRY,ModBlocks.FIRE_CHERRY,ModBlocks.GOLD_CHERRY,ModBlocks.STONE_CHERRY,
				ModBlocks.WATER_CHERRY)
			.map((t) -> new ItemStack(t.petals.asItem())).toList();
	}
}
