package io.github.tommyrobot666.productivecherrytrees.blocks.cherry;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluid;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

//TODO maybe add excluded/incompatable essences
public class ProducedResources {
	public ProducedResources(){};

	public ProducedResources(List<Essence> essences, List<Essence> secondaryEssences){
		this.essences.addAll(essences);
		this.secondaryEssences.addAll(secondaryEssences);
	};

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
		HashSet<Fluid> associatedFluids = new HashSet<>();
		/// used for recipe gen
		ArrayList<HashSet<Essence>> waysOfCrafting = new ArrayList<>(List.of(new HashSet<>(List.of(this))));
		public final String name;

		public Essence(String name){
			this.name = name;
		}

		public String translationKey(){
			return "productive_cherry_essence."+name;
		}

		public boolean canCraft(HashSet<Essence> materials){
			for (HashSet<Essence> way : waysOfCrafting) {
				if (materials.containsAll(way)){
					return true;
				}
			}
			return false;
		}
		
		public HashSet<Item> getAssociatedItems(){
			return associatedItems;
		}

		/** Used for both creating Essence and
		 for other mods to add to associatedItems */
		Essence addAssociatedItem(Item item){
			associatedItems.add(item);
			return this;
		}
		Essence addAssociatedFluid(Fluid fluid){
			associatedFluids.add(fluid);
			return this;
		}

		Essence addWayOfCrafting(HashSet<Essence> materials){
			waysOfCrafting.add(materials);
			return this;
		}

		@Override
		public String toString() {
			return "Essence["+name+']';
		}
	}
}
