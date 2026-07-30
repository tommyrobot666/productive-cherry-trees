package io.github.tommyrobot666.productivecherrytrees.datagen;

import io.github.tommyrobot666.productivecherrytrees.ProductiveCherryTrees;
import io.github.tommyrobot666.productivecherrytrees.blocks.ModBlocks;
import io.github.tommyrobot666.productivecherrytrees.blocks.cherry.CherryEssenceCombinations;
import io.github.tommyrobot666.productivecherrytrees.blocks.cherry.ProducedResources;
import io.github.tommyrobot666.productivecherrytrees.blocks.cherry.ProductiveCherryType;
import io.github.tommyrobot666.productivecherrytrees.recipes.PetalFusionRecipeBuilder;
import io.github.tommyrobot666.productivecherrytrees.recipes.SaplingInfusionRecipeBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.impl.resource.conditions.conditions.AllModsLoadedResourceCondition;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class RecipesProvider extends FabricRecipeProvider {
	public RecipesProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	void producedEssencesToPetalFusion(ProductiveCherryType type, RecipeOutput o){
		// for every way this types petals may fall onto another
		ProductiveCherryTrees.LOGGER.error(type.name+type.name+type.name);
		for (ProductiveCherryType other : ProductiveCherryTrees.CHERRY_TYPES){
			if (other == type) continue;
			ProductiveCherryTrees.LOGGER.error(other.name);
			// combine essences
			HashSet<ProducedResources.Essence> combinedEssences = new HashSet<>(type.producedResources.getEssences());
			combinedEssences.addAll(other.producedResources.getEssences());
			// combine secondaryEssences
			for (ProducedResources.Essence essence : type.producedResources.getSecondaryEssences()){
				// only compare secondaryEssences because when other has full essence is added in first step
				if (other.producedResources.getSecondaryEssences().contains(essence)) combinedEssences.add(essence);
			}
			// do special combination reactions
			CherryEssenceCombinations.applyAll(combinedEssences);

			ProductiveCherryTrees.LOGGER.warn(Arrays.toString(combinedEssences.stream().map((e)->e.name).toArray()));


			// figure out what it makes
			for (ProductiveCherryType result : ProductiveCherryTrees.CHERRY_TYPES){
				HashSet<ProducedResources.Essence> allEssencesInResult = new HashSet<>(result.producedResources.getEssences());
				allEssencesInResult.addAll(result.producedResources.getSecondaryEssences());
				// filter out the ones that do nothing
				if (result == type || result == other) continue;
				// check if every essence in result can be created
				boolean oneDoesntMatch = false;
				for (ProducedResources.Essence gettingCrafted : allEssencesInResult){
					if (!gettingCrafted.canCraft(combinedEssences)) {
						oneDoesntMatch = true;
						break;
					};
				}
				if (oneDoesntMatch) continue;

				// add recipe
				ProductiveCherryTrees.LOGGER.warn(result.name);
				petalFusion(other,type,result,other.dropPetalsChance*result.dropPetalsChance/type.dropPetalsChance,o);
			}
		}
	}

	void cherryRecipes(ProductiveCherryType type, RecipeProvider g, RecipeOutput o){
		g.shapeless(RecipeCategory.BUILDING_BLOCKS, type.planks, 4)
			.requires(type.log)
			.group("cherry_planks")
			.unlockedBy("has_the_log",g.has(type.log))
			.save(o,type.id+"_cherry_log_to_planks");
		saplingInfusion(Blocks.CHERRY_SAPLING,type.petals,type,o);
		g.slab(RecipeCategory.BUILDING_BLOCKS,type.slab, type.planks);
		g.stairBuilder(type.stairs, Ingredient.of(type.planks)).unlockedBy("got_wood",g.has(type.planks)).save(o);
		g.fenceBuilder(type.fence,Ingredient.of(type.planks)).unlockedBy("got_wood",g.has(type.planks)).save(o);
		g.fenceGateBuilder(type.fenceGate,Ingredient.of(type.planks)).unlockedBy("got_wood",g.has(type.planks)).save(o);
		g.buttonBuilder(type.button, Ingredient.of(type.planks)).unlockedBy("got_wood",g.has(type.planks)).save(o);
		g.pressurePlate(type.pressurePlate, type.planks);

		producedEssencesToPetalFusion(type, o);
	}

	void petalFusion(ProductiveCherryType org, ProductiveCherryType comb, ProductiveCherryType out, double chance, RecipeOutput o){
		new PetalFusionRecipeBuilder(org.petals,comb.petals,out.petals,chance).save(o);
	}
	void petalFusion(ProductiveCherryType org, ProductiveCherryType comb, Block out, double chance, RecipeOutput o){
		new PetalFusionRecipeBuilder(org.petals,comb.petals,out,chance).save(o);
	}
	void petalFusionReverse(ProductiveCherryType org, ProductiveCherryType comb, ProductiveCherryType out, double chance, double revChance, RecipeOutput o){
		petalFusion(org, comb, out, chance, o);
		petalFusion(out, comb, org, revChance, o);
	}
	void petalFusionEither(ProductiveCherryType org, ProductiveCherryType comb, ProductiveCherryType out, double chance, RecipeOutput o){
		petalFusion(org, comb, out, chance, o);
		petalFusion(comb, org, out, chance, o);
	}
	void saplingInfusion(Block sapling, Block combining, ProductiveCherryType out, RecipeOutput o){
		new SaplingInfusionRecipeBuilder(sapling,combining,out.sapling)
			.save(o);
	}
	// https://wiki.fabricmc.net/drafts:resourceconditions use for compat with other mods
	void petalFusionWithConditions(ProductiveCherryType org, ProductiveCherryType comb, ProductiveCherryType out, double chance, RecipeOutput o, List<String> modids){
		new PetalFusionRecipeBuilder(org.petals,comb.petals,out.petals,chance).save(withConditions(o, new AllModsLoadedResourceCondition(modids)));
	}
	void saplingInfusionWithConditions(Block sapling, Block combining, ProductiveCherryType out, RecipeOutput o, List<String> modids){
		new SaplingInfusionRecipeBuilder(sapling,combining,out.sapling)
			.save(withConditions(o, new AllModsLoadedResourceCondition(modids)));
	}

	@Override
	protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.@NotNull Provider reg, @NotNull RecipeOutput o) {
		return new RecipeProvider(reg,o) {
			@Override
			public void buildRecipes() {
				ProductiveCherryTrees.CHERRY_TYPES.forEach((t) -> {
					if (t.datagenSettings.genRecipes) cherryRecipes(t,this,o);
				});

				shaped(RecipeCategory.MISC, ModBlocks.SAPLING_INFUSER)
					.pattern("iii")
					.pattern("ici")
					.pattern("isi")
					.define('i', Items.IRON_BLOCK)
					.define('c',Items.CHERRY_SAPLING)
					.define('s', Items.SCULK)
					.group("")
					.unlockedBy(getHasName(ModBlocks.SAPLING_INFUSER),has(ModBlocks.SAPLING_INFUSER))
					.save(o);



				// Stone recipes
				// this recipe could be added for every traceElements of stone
				petalFusionReverse(ModBlocks.STONE_CHERRY,ModBlocks.STONE_CHERRY,ModBlocks.GOLD_CHERRY,0.0001,1,o);
				petalFusion(ModBlocks.STONE_CHERRY,ModBlocks.STONE_CHERRY,Blocks.STONE_SLAB,0.0001,o);

				// Gold recipes
				saplingInfusion(ModBlocks.STONE_CHERRY.sapling,Blocks.GOLD_BLOCK,ModBlocks.GOLD_CHERRY,o);
				saplingInfusion(ModBlocks.STONE_CHERRY.sapling,Blocks.RAW_GOLD_BLOCK,ModBlocks.GOLD_CHERRY,o);

				// Fire recipes
				saplingInfusion(Blocks.CHERRY_SAPLING,Blocks.LAVA,ModBlocks.FIRE_CHERRY,o);
				saplingInfusion(ModBlocks.STONE_CHERRY.sapling,Blocks.FIRE,ModBlocks.FIRE_CHERRY,o);
				petalFusion(ModBlocks.FIRE_CHERRY,ModBlocks.WATER_CHERRY,Blocks.OBSIDIAN,0.05,o);

				// Water recipes
				saplingInfusion(Blocks.CHERRY_SAPLING,Blocks.WATER,ModBlocks.WATER_CHERRY,o);
				petalFusion(ModBlocks.WATER_CHERRY,ModBlocks.FIRE_CHERRY,Blocks.COBBLESTONE,1,o);
			}
		};
	}

	@Override
	public @NotNull String getName() {
		return "RecipesProvider";
	}
}
