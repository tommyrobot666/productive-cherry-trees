package io.github.tommyrobot666.productivecherrytrees.blocks;

import io.github.tommyrobot666.productivecherrytrees.recipes.ModRecipeTypes;
import io.github.tommyrobot666.productivecherrytrees.recipes.SaplingInfusionRecipe;
import io.github.tommyrobot666.productivecherrytrees.recipes.TwoBlocksInput;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class SaplingInfusionBlockEntity extends BlockEntity {
	public static final int TICKS_TO_CRAFT = 20;
	public int ticksPassed = 0;
	public boolean crafting;


	public SaplingInfusionBlockEntity(BlockPos pos, BlockState state) {
		super(ModBlocks.SAPLING_INFUSER_ENTITY, pos, state);
	}

	public static void tick(ServerLevel level, BlockPos pos, BlockState state, SaplingInfusionBlockEntity e){
		Block result = getResult(level,pos);
		if (result == null){
			e.crafting = false;
			e.ticksPassed = 0;
		} else {
			e.crafting = true;
			e.ticksPassed++;
		}

		if (e.crafting && e.ticksPassed>TICKS_TO_CRAFT){
			clearInputAndPlaceOutput(level,pos,result);
		}

		if (state.getValue(SaplingInfusionBlock.CRAFTING) != e.crafting){
			level.setBlockAndUpdate(pos,ModBlocks.SAPLING_INFUSER.defaultBlockState()
				.setValue(SaplingInfusionBlock.CRAFTING,e.crafting));
		}
	}

	private static void clearInputAndPlaceOutput(Level level, BlockPos pos, Block result) {
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (i == j && j == 0) continue;
				level.setBlockAndUpdate(pos.offset(i,0,j), Blocks.AIR.defaultBlockState());
			}
		}
		level.setBlockAndUpdate(pos.above(), result.defaultBlockState());
	}

	private static Block getResult(ServerLevel level, BlockPos pos) {
		Block petals;
		if ((petals = getSameBlockPlacedAllAround(level, pos)) == null) return null;
		Optional<RecipeHolder<@NotNull SaplingInfusionRecipe>> recipe = level.recipeAccess().getRecipeFor(
			ModRecipeTypes.SAPLING_INFUSION_TYPE,
			new TwoBlocksInput(level.getBlockState(pos.above()).getBlock(),petals),
			level);
		if (recipe.isEmpty()) return null;

		return recipe.orElseThrow().value().getOutput();
	}

	private static Block getSameBlockPlacedAllAround(Level level, BlockPos pos){
		Block testingState = null;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if (i == j && j == 0) continue;
				BlockPos testingPos = pos.offset(i,0,j);
				if (testingState == null){
					testingState = level.getBlockState(testingPos).getBlock();
				} else {
					if (level.getBlockState(testingPos).getBlock() != testingState){
						return null;
					} else if (testingState instanceof ProductivePetalsBlock) {
						if (level.getBlockState(testingPos).getValue(ProductivePetalsBlock.AMOUNT) < 4)
							return null;
					}
				}
			}
		}
		if (testingState == Blocks.AIR) return null;
		return testingState;
	}

	public static void ticker(Level level, BlockPos pos, BlockState state, @NotNull SaplingInfusionBlockEntity saplingInfusionBlockEntity) {
		if (level.isClientSide()) return;
		tick((ServerLevel) level,pos,state,saplingInfusionBlockEntity);
	}
}
