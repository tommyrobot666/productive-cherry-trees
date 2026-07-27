package io.github.tommyrobot666.productivecherrytrees.blocks.cherry;

import java.util.Collection;
import java.util.List;

public class CherryEssenceCombinations {
	// This is cooler than a Consumer<>
	@FunctionalInterface
	interface Combination {
		void apply(Collection<ProducedResources.Essence> essences);
	}

	static void waterAndFireToStone(Collection<ProducedResources.Essence> essences){
		if (essences.contains(ModCherryEssences.FIRE) && essences.contains(ModCherryEssences.WATER)){
			essences.add(ModCherryEssences.STONE);
			//TODO should the fire and water be removed too?
		}
	}

	static final List<Combination> allCombinations = List.of(CherryEssenceCombinations::waterAndFireToStone);

	public static void applyAll(Collection<ProducedResources.Essence> essences){
		allCombinations.forEach((c)-> c.apply(essences));
	}
}
