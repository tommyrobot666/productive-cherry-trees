package io.github.tommyrobot666.productivecherrytrees.datagen;

import io.github.tommyrobot666.productivecherrytrees.ProductiveCherryTrees;
import io.github.tommyrobot666.productivecherrytrees.blocks.ModBlocks;
import io.github.tommyrobot666.productivecherrytrees.blocks.ProductiveCherryType;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.CherryFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.CherryTrunkPlacer;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ConfiguredFeatureProvider extends FabricDynamicRegistryProvider {
	public ConfiguredFeatureProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	public static final ResourceKey<ConfiguredFeature<?, ?>> TEST_TREE_KEY =
		ResourceKey.create(
			Registries.CONFIGURED_FEATURE,
			Identifier.tryBuild(ProductiveCherryTrees.ID, "test_productive_cherry_tree")
		);

	static TreeConfiguration createCherryTree(ProductiveCherryType type){
//		WeightedList<IntProvider> weightedList = WeightedList.builder().add(new ConstantInt(1),1).build();
		WeightedList<IntProvider> weightedList = WeightedList.of(
			List.of(new Weighted<>(new ConstantInt(1),1),
				new Weighted<>(new ConstantInt(2),1),
				new Weighted<>(new ConstantInt(3),1)));

		return new TreeConfiguration.TreeConfigurationBuilder(
			BlockStateProvider.simple(type.log),
			new CherryTrunkPlacer(7,1,0,
				new WeightedListInt(weightedList),
				UniformInt.of(4,2),
				UniformInt.of(-3,-4),
				UniformInt.of(0,-1)),
			BlockStateProvider.simple(type.petals),
			new CherryFoliagePlacer(new ConstantInt(4),
				new ConstantInt(0),
				new ConstantInt(5),
				.25f,.25f,1.0f/6,1.0f/3),
			new TwoLayersFeatureSize(1,0, 2)
		).ignoreVines().build();
	}

	@Override
	protected void configure(HolderLookup.Provider provider, Entries entries) {

	}

	@Override
	public String getName() {
		return "";
	}

	public static void bootStrap(BootstrapContext<ConfiguredFeature<?,?>> cfContext) {
		cfContext.register(TEST_TREE_KEY, new ConfiguredFeature<>(Feature.TREE,createCherryTree(ModBlocks.TEST_CHERRY)));
	}
}
