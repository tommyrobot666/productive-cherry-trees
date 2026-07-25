package io.github.tommyrobot666.productivecherrytrees.datagen;

import io.github.tommyrobot666.productivecherrytrees.ProductiveCherryTrees;
import io.github.tommyrobot666.productivecherrytrees.blocks.cherry.ProductiveCherryType;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
	public static final TagKey<Item> PLANKS = TagKey.create(Registries.ITEM, Identifier.withDefaultNamespace("planks"));

	public ItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
		super(output, registryLookupFuture);
	}

	void cherryTags(ProductiveCherryType type){
		valueLookupBuilder(PLANKS)
			.add(type.planks.asItem());
	}

	@Override
	protected void addTags(HolderLookup.@NonNull Provider registries) {
		ProductiveCherryTrees.CHERRY_TYPES.forEach((t) -> {
			if (t.datagenSettings.genTags) cherryTags(t);
		});
	}
}
