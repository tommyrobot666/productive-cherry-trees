package io.github.tommyrobot666.productivecherrytrees.blocks.cherry;

import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;

import java.util.HashSet;
import java.util.List;

public class ModCherryEssences {

	public static final ProducedResources.Essence ENERGY = new ProducedResources.Essence("energy");
	public static final ProducedResources.Essence NO_ENERGY = new ProducedResources.Essence("no_energy");
	public static final ProducedResources.Essence FIRE = new ProducedResources.Essence("fire")
		.addAssociatedFluid(Fluids.LAVA);
	public static final ProducedResources.Essence WATER = new ProducedResources.Essence("water")
		.addAssociatedFluid(Fluids.WATER);
	public static final ProducedResources.Essence STONE = new ProducedResources.Essence("stone")
		.addAssociatedItem(Items.STONE)
		.addWayOfCrafting(new HashSet<>(List.of(WATER,FIRE)));
	public static final ProducedResources.Essence GOLD = new ProducedResources.Essence("gold")
		.addAssociatedItem(Items.GOLD_INGOT);
	public static final ProducedResources.Essence GRAVEL = new ProducedResources.Essence("gravel")
		.addAssociatedItem(Items.GRAVEL)
		.addWayOfCrafting(new HashSet<>(List.of(STONE,ENERGY)));
	public static final ProducedResources.Essence SAND = new ProducedResources.Essence("sand")
		.addAssociatedItem(Items.SAND)
		.addWayOfCrafting(new HashSet<>(List.of(GRAVEL,ENERGY)));
	public static final ProducedResources.Essence IRON = new ProducedResources.Essence("iron")
		.addAssociatedItem(Items.IRON_INGOT);
	public static final ProducedResources.Essence ICE = new ProducedResources.Essence("ice")
		.addAssociatedItem(Items.ICE)
		.addWayOfCrafting(new HashSet<>(List.of(WATER,NO_ENERGY)));
	public static final ProducedResources.Essence TRUE_MAGIC_OF_ULTIMATE_POWER = new ProducedResources.Essence("idk");
}
