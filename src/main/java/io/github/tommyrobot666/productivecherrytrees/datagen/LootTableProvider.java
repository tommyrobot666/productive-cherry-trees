package io.github.tommyrobot666.productivecherrytrees.datagen;

import io.github.tommyrobot666.productivecherrytrees.ProducedResources;
import io.github.tommyrobot666.productivecherrytrees.blocks.ModBlocks;
import io.github.tommyrobot666.productivecherrytrees.blocks.ProductiveCherryType;
import io.github.tommyrobot666.productivecherrytrees.blocks.ProductivePetalsBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.SequenceFunction;
import net.minecraft.world.level.storage.loot.functions.SetComponentsFunction;
import net.minecraft.world.level.storage.loot.predicates.AnyOfCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.concurrent.CompletableFuture;

public class LootTableProvider extends FabricBlockLootSubProvider {
	protected LootTableProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(packOutput, registriesFuture);
	}

	void petalsDrops(ProductivePetalsBlock petals){
		LootTable.Builder table = LootTable.lootTable()
			.withPool(LootPool.lootPool().when(hasShearsOrSilkTouch())
				.setRolls(new ConstantValue(1))
				.add(NestedLootTable.inlineLootTable(createSegmentedBlockDrops(petals).build())));

		// it would make more sense to me for every resource to not have it's own pool,
		// but setRolls and SetComponentsFunction applies to the whole pool
		for (ProducedResources.ProducedResource producedResource : petals.producedResources.v) {
			// add a pool for each segmented block segment
			for (int i = 2; i <6; i++) {
				AnyOfCondition.Builder allPoolBlockStates = new AnyOfCondition.Builder();
				for (int j = 1; j < i; j++) {
					// use .or(Builder) for one that returns self
					allPoolBlockStates.addTerm(new LootItemBlockStatePropertyCondition.Builder(petals).setProperties(
						StatePropertiesPredicate.Builder.properties()
						.hasProperty(ProductivePetalsBlock.AMOUNT,j)));
				}

				LootPool.Builder pool = LootPool.lootPool().when(doesNotHaveShearsOrSilkTouch())
					.when(allPoolBlockStates)
					.add(LootItem.lootTableItem(producedResource.item()))
					.setRolls(new UniformGenerator(new ConstantValue(producedResource.count()-1), new ConstantValue(producedResource.count())));

				if (producedResource.components().isPresent()) {
					pool.apply(SequenceFunction.of(
						producedResource.components().orElseThrow().stream().map(
							(comp) -> SetComponentsFunction.setComponent((DataComponentType<Object>) comp.type(), comp.value()).build()
						).toList()
					));
				}

				table = table.withPool(pool);
			}
		}

		this.add(petals, table);
	}

	void cherryDrops(ProductiveCherryType type){
		dropSelf(type.log);
		add(type.leafs,createLeavesDrops(type.leafs,type.sapling,0.1f));
		dropSelf(type.sapling);
		petalsDrops(type.petals);
	}

	@Override
	public void generate() {
		cherryDrops(ModBlocks.TEST_CHERRY);
		DataGen.genCherryDefaultAssets.forEach(this::cherryDrops);
	}
}
