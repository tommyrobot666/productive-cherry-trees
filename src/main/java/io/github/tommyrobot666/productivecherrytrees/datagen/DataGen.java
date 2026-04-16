package io.github.tommyrobot666.productivecherrytrees.datagen;

import io.github.tommyrobot666.productivecherrytrees.ProductiveCherryTrees;
import io.github.tommyrobot666.productivecherrytrees.blocks.ModBlocks;
import io.github.tommyrobot666.productivecherrytrees.blocks.ProductiveCherryType;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DataGen implements DataGeneratorEntrypoint {
	public static final List<ProductiveCherryType> genCherryDefault = new ArrayList<>();

	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fdg) {
		addCherryDefaults();
		FabricDataGenerator.Pack pack = fdg.createPack();
		pack.addProvider(ModelProvider::new);
		pack.addProvider(LootTableProvider::new);
		pack.addProvider(ConfiguredFeatureProvider::new);
		pack.addProvider(EnglishLanguageProvider::new);
	}

	private void addCherryDefaults() {
		genCherryDefault.add(ModBlocks.GOLD_CHERRY);
	}

	@Override
	public @Nullable String getEffectiveModId() {
		return ProductiveCherryTrees.ID;
	}

	@Override
	public void buildRegistry(RegistrySetBuilder rb) {
		rb.add(Registries.CONFIGURED_FEATURE, ConfiguredFeatureProvider::bootStrap);
	}
}
