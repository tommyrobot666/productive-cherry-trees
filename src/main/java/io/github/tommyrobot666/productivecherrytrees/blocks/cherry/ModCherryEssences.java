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
}
