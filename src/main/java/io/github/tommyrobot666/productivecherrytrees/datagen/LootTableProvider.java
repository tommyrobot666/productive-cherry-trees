package io.github.tommyrobot666.productivecherrytrees.datagen;

import io.github.tommyrobot666.productivecherrytrees.ProductiveCherryTrees;
import io.github.tommyrobot666.productivecherrytrees.blocks.ModBlocks;
import io.github.tommyrobot666.productivecherrytrees.blocks.cherry.ProductiveCherryType;
import io.github.tommyrobot666.productivecherrytrees.blocks.cherry.ProductivePetalsBlock;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.advancements.criterion.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolSingletonContainer;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.functions.*;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class LootTableProvider extends FabricBlockLootSubProvider {
	protected LootTableProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(packOutput, registriesFuture);
	}

	public static final double BIG_NUMBER_CHANCE_TO_INT = 100000000;
	void petalsDrops(ProductiveCherryType type){
		ProductivePetalsBlock petals = type.petals;

		if (type.datagenSettings.productiveCherryLoot.dropSelf){
			this.add(petals,createSegmentedBlockDrops(petals));
			return;
		}

		double totalValue = type.datagenSettings.productiveCherryLoot.totalValue();

		LootTable.Builder table = LootTable.lootTable()
			.withPool(LootPool.lootPool().when(hasShearsOrSilkTouch())
				.setRolls(new ConstantValue(1))
				.add(NestedLootTable.inlineLootTable(createSegmentedBlockDrops(petals).build())));

		for (int i = 1; i <5; i++) {
			LootItemBlockStatePropertyCondition.Builder poolBlockState =
				new LootItemBlockStatePropertyCondition.Builder(petals).setProperties(
				StatePropertiesPredicate.Builder.properties()
					.hasProperty(ProductivePetalsBlock.AMOUNT, i));

			LootPool.Builder pool = LootPool.lootPool().when(doesNotHaveShearsOrSilkTouch())
				.when(poolBlockState);

			for (ProductiveCherryLoot.ProducedResource producedResource : type.datagenSettings.productiveCherryLoot.v) {
				LootPoolSingletonContainer.Builder<?> item = LootItem.lootTableItem(producedResource.item())
					.setWeight((int) (BIG_NUMBER_CHANCE_TO_INT*((producedResource.value()*i)/totalValue)))
					.apply(SetItemCountFunction.setCount(new UniformGenerator(new ConstantValue(0), new ConstantValue(producedResource.count()*i))));

				if (producedResource.components().isPresent()) {
					//noinspection unchecked
					List<? extends LootItemConditionalFunction.Builder<?>> componentSetters =
						producedResource.components().orElseThrow().stream().map(
							(comp) -> SetComponentsFunction.setComponent((DataComponentType<Object>) comp.type(), comp.value())
						).toList();
					componentSetters.forEach(item::apply);
				}

				pool.add(item);
			}

			table = table.withPool(pool);
		}

		this.add(petals, table);
	}

	void cherryDrops(ProductiveCherryType type){
		dropSelf(type.log);
		add(type.leafs,createLeavesDrops(type.leafs,type.sapling,0.1f));
		dropSelf(type.sapling);
		petalsDrops(type);
		dropSelf(type.planks);
		dropSelf(type.stairs);
		add(type.slab,createSlabItemTable(type.slab));
		dropSelf(type.fence);
		dropSelf(type.fenceGate);
		dropSelf(type.button);
		dropSelf(type.pressurePlate);
	}

	@Override
	public void generate() {
		ProductiveCherryTrees.CHERRY_TYPES.forEach((t) -> {
			if (t.datagenSettings.genLoot) cherryDrops(t);
		});

		dropSelf(ModBlocks.SAPLING_INFUSER);
	}
}
