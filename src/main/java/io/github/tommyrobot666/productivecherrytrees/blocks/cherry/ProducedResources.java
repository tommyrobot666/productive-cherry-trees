package io.github.tommyrobot666.productivecherrytrees.blocks.cherry;

import net.minecraft.world.item.Item;

import java.util.HashSet;

public class ProducedResources {
	/** Used for when machines process this cherry
	 * Recipe gen: full essence
	 * (produced tree may have this essence) */
	HashSet<Essence> essences = new HashSet<>();
	/** Recipe gen: half essence
	 * (will produce trees with this essence if other is half or full essence) */
	HashSet<Essence> secondaryEssences = new HashSet<>();
	/** Recipe gen: not enough
	 * (can be transformed into this using custom recipe (think stone to gold recipe))*/
	HashSet<Essence> traceElements = new HashSet<>();

	public HashSet<Essence> getEssences(){
		return essences;
	}

	public HashSet<Essence> getSecondaryEssences(){
		return secondaryEssences;
	}

	/** Used for both creating ProducedResources and
	 for other mods to add to essences */
	ProducedResources addEssence(Essence essence){
		essences.add(essence);
		return this;
	}

	/** Used for both creating ProducedResources and
	 for other mods to add to secondaryEssences */
	ProducedResources addSecondaryEssence(Essence essence){
		secondaryEssences.add(essence);
		return this;
	}

	/** Used for both creating ProducedResources and
	 for other mods to add to traceElements */
	ProducedResources addTraceElements(Essence essence){
		traceElements.add(essence);
		return this;
	}

	public static class Essence {
		/// Used for when machines process magic essence
		HashSet<Item> associatedItems = new HashSet<>();

		public HashSet<Item> getAssociatedItems(){
			return associatedItems;
		}

		/** Used for both creating Essence and
		 for other mods to add to associatedItems */
		Essence addAssociatedItem(Item item){
			associatedItems.add(item);
			return this;
		}

	}
}
