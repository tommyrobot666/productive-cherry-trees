package io.github.tommyrobot666.productivecherrytrees.blocks;

import io.github.tommyrobot666.productivecherrytrees.ProductiveCherryTrees;
import io.github.tommyrobot666.productivecherrytrees.recipes.ModRecipeTypes;
import io.github.tommyrobot666.productivecherrytrees.recipes.SaplingInfusionRecipe;
import io.github.tommyrobot666.productivecherrytrees.recipes.TwoBlocksInput;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.redstone.Orientation;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class SaplingInfusionBlock extends Block {
	public static final BooleanProperty CRAFTING = BlockStateProperties.CRAFTING;

	public SaplingInfusionBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, @NotNull BlockState> builder) {
		builder.add(CRAFTING);
	}

	@Override
	protected void neighborChanged(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Block block, @Nullable Orientation orientation, boolean movedByPiston) {
		if (!state.getValue(CRAFTING)) level.scheduleTick(pos, ModBlocks.SAPLING_INFUSER, 1);
	}

	@Override
	protected void tick(BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (state.getValue(CRAFTING)){
			Block result = getResult(level, pos);
			ProductiveCherryTrees.LOGGER.error("{}",result);
			if (result == null){
				state.setValue(CRAFTING,false);
				return;
			}

			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					if (i == j && j == 0) continue;
					level.setBlockAndUpdate(pos.offset(i,0,j),Blocks.AIR.defaultBlockState());
				}
			}
			level.setBlockAndUpdate(pos.above(),result.defaultBlockState());
		} else {
			Block petals;
			if ((petals = getSameBlockPlacedAllAround(level,pos)) == null) return;
			if (level.recipeAccess().getRecipeFor(
				ModRecipeTypes.SAPLING_INFUSION_TYPE,
				new TwoBlocksInput(level.getBlockState(pos.above()).getBlock(),petals),
				level).isPresent()) state.setValue(CRAFTING,true);
			ProductiveCherryTrees.LOGGER.error("{}",petals);
			level.scheduleTick(pos, ModBlocks.SAPLING_INFUSER, 20);
		}
	}

	Block getResult(ServerLevel level, BlockPos pos) {
		Block petals;
		if ((petals = getSameBlockPlacedAllAround(level, pos)) == null) return null;
		Optional<RecipeHolder<@NotNull SaplingInfusionRecipe>> recipe = level.recipeAccess().getRecipeFor(
			ModRecipeTypes.SAPLING_INFUSION_TYPE,
			new TwoBlocksInput(level.getBlockState(pos.above()).getBlock(),petals),
			level);
		if (recipe.isEmpty()) return null;

		return recipe.orElseThrow().value().getOutput();
	}

	Block getSameBlockPlacedAllAround(Level level, BlockPos pos){
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

	@Override
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		super.animateTick(state, level, pos, random);
	}
}
