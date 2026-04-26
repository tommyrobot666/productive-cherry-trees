package io.github.tommyrobot666.productivecherrytrees.datagen;

import io.github.tommyrobot666.productivecherrytrees.blocks.ModBlocks;
import io.github.tommyrobot666.productivecherrytrees.blocks.ProductiveCherryType;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static io.github.tommyrobot666.productivecherrytrees.ProductiveCherryTrees.ID;

public class EnglishLanguageProvider extends FabricLanguageProvider {
	protected EnglishLanguageProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
		super(packOutput, "en_us", registryLookup);
	}

	void cherryLang(ProductiveCherryType type, TranslationBuilder tb){
		String capitalName = type.id.toUpperCase().substring(0,1).concat(type.id.substring(1));
		tb.add(type.leafs, capitalName+" Cherry Leafs");
		tb.add(type.log, capitalName+" Cherry Log");
		tb.add(type.petals, capitalName+" Cherry Petals");
		tb.add(type.sapling, capitalName+" Cherry Sapling");
		tb.add(type.leafs.asItem(), capitalName+" Cherry Leafs");
		tb.add(type.log.asItem(), capitalName+" Cherry Log");
		tb.add(type.petals.asItem(), capitalName+" Cherry Petals");
		tb.add(type.sapling.asItem(), capitalName+" Cherry Sapling");
	}

	@Override
	public void generateTranslations(HolderLookup.@NotNull Provider reg, @NotNull TranslationBuilder tb) {
		cherryLang(ModBlocks.TEST_CHERRY,tb);
		DataGen.genCherryDefaultAssets.forEach((t) -> cherryLang(t,tb));

		tb.add(ModBlocks.SAPLING_INFUSER,"Sapling Infuser");
		tb.add(ModBlocks.SAPLING_INFUSER.asItem(),"Sapling Infuser");

		tb.add("tab."+ID,"Productive Cherry Trees");
	}
}
