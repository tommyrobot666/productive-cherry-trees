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

	void petalsDrops(ProductivePetalsBlock petals){
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

			for (ProducedResources.ProducedResource producedResource : petals.producedResources.v) {
				LootPoolSingletonContainer.Builder<?> item = LootItem.lootTableItem(producedResource.item())
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
		petalsDrops(type.petals);
	}

	@Override
	public void generate() {
		cherryDrops(ModBlocks.TEST_CHERRY);
		DataGen.genCherryDefaultAssets.forEach(this::cherryDrops);

		dropSelf(ModBlocks.SAPLING_INFUSER);
	}
}
