package io.github.tommyrobot666.productivecherrytrees.blocks.cherry;

import java.util.Collection;
import java.util.List;

public class CherryEssenceCombinations {
	// This is cooler than a Consumer<>
	@FunctionalInterface
	interface Combination {
		void apply(Collection<ProducedResources.Essence> essences);
	}

	static void energyAmounts(Collection<ProducedResources.Essence> essences){
		if (essences.contains(ModCherryEssences.ENERGY) && essences.contains(ModCherryEssences.NO_ENERGY)){
			// cancel out
			essences.remove(ModCherryEssences.ENERGY);
			essences.remove(ModCherryEssences.NO_ENERGY);
		}
	}

	static final List<Combination> allCombinations = List.of(CherryEssenceCombinations::energyAmounts);

	public static void applyAll(Collection<ProducedResources.Essence> essences){
		allCombinations.forEach((c)-> c.apply(essences));
	}
}
