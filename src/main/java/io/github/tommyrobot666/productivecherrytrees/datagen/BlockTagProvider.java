package io.github.tommyrobot666.productivecherrytrees.datagen;

import io.github.tommyrobot666.productivecherrytrees.blocks.ModBlocks;
import io.github.tommyrobot666.productivecherrytrees.blocks.ProductiveCherryType;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class BlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
	public static final TagKey<Block> MINEABLE_AXE = TagKey.create(Registries.BLOCK, Identifier.withDefaultNamespace("mineable/axe"));
	public static final TagKey<Block> MINEABLE_HOE = TagKey.create(Registries.BLOCK, Identifier.withDefaultNamespace("mineable/hoe"));
	public static final TagKey<Block> MINEABLE_PICKAXE = TagKey.create(Registries.BLOCK, Identifier.withDefaultNamespace("mineable/pickaxe"));
	public static final TagKey<Block> NO_LEAF_DECAY = TagKey.create(Registries.BLOCK, Identifier.withDefaultNamespace("prevents_nearby_leaf_decay"));

	public BlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
		super(output, registryLookupFuture);
	}

	void cherryTags(ProductiveCherryType type){
		valueLookupBuilder(MINEABLE_AXE)
			.add(type.log);
		valueLookupBuilder(MINEABLE_HOE)
			.add(type.leafs)
			.add(type.petals);
		valueLookupBuilder(NO_LEAF_DECAY)
			.add(type.log);
	}

	@Override
	protected void addTags(HolderLookup.@NotNull Provider reg) {
		cherryTags(ModBlocks.TEST_CHERRY);
		DataGen.genCherryDefaultAssets.forEach(this::cherryTags);

		valueLookupBuilder(MINEABLE_PICKAXE)
			.add(ModBlocks.SAPLING_INFUSER);
	}
}
