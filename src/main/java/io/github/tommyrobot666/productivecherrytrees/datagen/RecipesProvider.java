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
import net.minecraft.world.level.block.Block;
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
		saplingInfusion(Blocks.CHERRY_SAPLING,type.petals,type,o);
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



				// Stone recipes
				petalFusionReverse(ModBlocks.STONE_CHERRY,ModBlocks.STONE_CHERRY,ModBlocks.GOLD_CHERRY,0.0001,1,o);
				petalFusion(ModBlocks.STONE_CHERRY,ModBlocks.STONE_CHERRY,Blocks.STONE_SLAB,0.0001,o);

				// Gold recipes
				saplingInfusion(ModBlocks.STONE_CHERRY.sapling,Blocks.GOLD_BLOCK,ModBlocks.GOLD_CHERRY,o);
				saplingInfusion(ModBlocks.STONE_CHERRY.sapling,Blocks.RAW_GOLD_BLOCK,ModBlocks.GOLD_CHERRY,o);

				// Fire recipes
//				petalFusion(Blocks.WATER,ModBlocks.FIRE_CHERRY.petals,Blocks.COBBLESTONE,1,o);
				saplingInfusion(Blocks.CHERRY_SAPLING,Blocks.LAVA,ModBlocks.FIRE_CHERRY,o);
				saplingInfusion(ModBlocks.STONE_CHERRY.sapling,Blocks.FIRE,ModBlocks.FIRE_CHERRY,o);
				petalFusion(ModBlocks.FIRE_CHERRY,ModBlocks.WATER_CHERRY,Blocks.OBSIDIAN,0.05,o);

				// Water recipes
				petalFusion(ModBlocks.WATER_CHERRY,ModBlocks.FIRE_CHERRY,ModBlocks.STONE_CHERRY,0.1,o);
				saplingInfusion(Blocks.CHERRY_SAPLING,Blocks.WATER,ModBlocks.WATER_CHERRY,o);
			}
		};
	}

	@Override
	public @NotNull String getName() {
		return "RecipesProvider";
	}
}
