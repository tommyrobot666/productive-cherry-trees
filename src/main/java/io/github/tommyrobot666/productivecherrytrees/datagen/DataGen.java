package io.github.tommyrobot666.productivecherrytrees.datagen;

import io.github.tommyrobot666.productivecherrytrees.ProductiveCherryTrees;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import org.jspecify.annotations.Nullable;

public class DataGen implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fdg) {
		FabricDataGenerator.Pack pack = fdg.createPack();
		pack.addProvider(ModelProvider::new);
		pack.addProvider(LootTableProvider::new);
		pack.addProvider(ConfiguredFeatureProvider::new);
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
