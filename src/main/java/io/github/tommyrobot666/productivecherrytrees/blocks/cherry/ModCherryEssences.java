package io.github.tommyrobot666.productivecherrytrees.blocks.cherry;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class ModCherryEssences {
	static ProducedResources.Essence create(Item... items){
		ProducedResources.Essence essence = new ProducedResources.Essence();
		for (var item : items){
			essence.addAssociatedItem(item);
		}
		return essence;
	}

	public static final ProducedResources.Essence FIRE = create(Items.BLAZE_POWDER);
	public static final ProducedResources.Essence WATER = create(Items.WATER_BUCKET);
	public static final ProducedResources.Essence STONE = create(Items.STONE);
	public static final ProducedResources.Essence GOLD = create(Items.GOLD_INGOT);
	public static final ProducedResources.Essence GRAVEL = create(Items.GRAVEL);
	public static final ProducedResources.Essence SAND = create(Items.SAND);
	public static final ProducedResources.Essence IRON = create(Items.IRON_INGOT);
	public static final ProducedResources.Essence ICE = create(Items.ICE);
	public static final ProducedResources.Essence ENERGY = create(Items.FIRE_CHARGE);
	public static final ProducedResources.Essence NO_ENERGY = create(Items.BLUE_ICE);
	public static final ProducedResources.Essence TRUE_MAGIC_OF_ULTIMATE_POWER = create(Items.COMMAND_BLOCK_MINECART);
}
