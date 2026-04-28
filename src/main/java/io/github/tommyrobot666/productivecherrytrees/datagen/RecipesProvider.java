package io.github.tommyrobot666.productivecherrytrees.datagen;

import io.github.tommyrobot666.productivecherrytrees.blocks.ModBlocks;
import io.github.tommyrobot666.productivecherrytrees.blocks.ProductiveCherryType;
import io.github.tommyrobot666.productivecherrytrees.recipes.PetalFusionRecipeBuilder;
import io.github.tommyrobot666.productivecherrytrees.recipes.SaplingInfusionRecipeBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class RecipesProvider extends FabricRecipeProvider {
	public RecipesProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
		super(output, registriesFuture);
	}

	void cherryRecipes(ProductiveCherryType type, RecipeProvider g, RecipeOutput o){
		g.shapeless(RecipeCategory.BUILDING_BLOCKS, Items.CHERRY_PLANKS, 4)
			.requires(type.log)
			.group("cherry_planks")
			.unlockedBy("has_the_log",g.has(type.log))
			.save(o,type.id+"_cherry_log_to_planks");
		new SaplingInfusionRecipeBuilder(Blocks.CHERRY_SAPLING,type.petals,type.sapling)
			.save(o);
	}

	void petalFusion(ProductiveCherryType org, ProductiveCherryType comb, ProductiveCherryType out, double chance, RecipeOutput o){
		new PetalFusionRecipeBuilder(org.petals,comb.petals,out.petals,chance).save(o);
	}
	void petalFusionReverse(ProductiveCherryType org, ProductiveCherryType comb, ProductiveCherryType out, double chance, double revChance, RecipeOutput o){
		petalFusion(org, comb, out, chance, o);
		petalFusion(out, comb, org, revChance, o);
	}
	void petalFusionEither(ProductiveCherryType org, ProductiveCherryType comb, ProductiveCherryType out, double chance, RecipeOutput o){
		petalFusion(org, comb, out, chance, o);
		petalFusion(comb, org, out, chance, o);
	}

	@Override
	protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.@NotNull Provider reg, @NotNull RecipeOutput o) {
		return new RecipeProvider(reg,o) {
			@Override
			public void buildRecipes() {
				cherryRecipes(ModBlocks.TEST_CHERRY,this,o);
				DataGen.genCherryDefaultAssets.forEach((t) -> cherryRecipes(t,this,o));

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

				new PetalFusionRecipeBuilder(Blocks.YELLOW_STAINED_GLASS,ModBlocks.TEST_CHERRY.petals,Blocks.GOLD_BLOCK,0.25)
					.save(o);

				petalFusionReverse(ModBlocks.STONE_CHERRY,ModBlocks.STONE_CHERRY,ModBlocks.GOLD_CHERRY,0.001,0.2,o);

				new PetalFusionRecipeBuilder(Blocks.WATER,ModBlocks.FIRE_CHERRY.petals,Blocks.COBBLESTONE,1).save(o);
				new SaplingInfusionRecipeBuilder(Blocks.CHERRY_SAPLING,Blocks.LAVA,ModBlocks.FIRE_CHERRY.sapling)
					.save(o);
				petalFusion(ModBlocks.WATER_CHERRY,ModBlocks.FIRE_CHERRY,ModBlocks.STONE_CHERRY,0.1,o);
				new PetalFusionRecipeBuilder(ModBlocks.FIRE_CHERRY.petals,ModBlocks.WATER_CHERRY.petals,Blocks.OBSIDIAN,0.05).save(o);
				new SaplingInfusionRecipeBuilder(Blocks.CHERRY_SAPLING,Blocks.WATER,ModBlocks.WATER_CHERRY.sapling)
					.save(o);
			}
		};
	}

	@Override
	public @NotNull String getName() {
		return "RecipesProvider";
	}
}
