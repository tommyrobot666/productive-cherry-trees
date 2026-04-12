package io.github.tommyrobot666.productivecherrytrees.datagen;

import io.github.tommyrobot666.productivecherrytrees.blocks.ModBlocks;
import io.github.tommyrobot666.productivecherrytrees.blocks.ProductiveCherryType;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class LootTableProvider extends FabricBlockLootSubProvider {
	protected LootTableProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(packOutput, registriesFuture);
	}

	void cherryDrops(ProductiveCherryType type){
		dropSelf(type.log);
		add(type.leafs,createLeavesDrops(type.leafs,type.sapling,0.1f));
		dropSelf(type.sapling);
		// petals drops will be set in getDrops (for now)
	}

	@Override
	public void generate() {
		cherryDrops(ModBlocks.TEST_CHERRY);
	}
}
