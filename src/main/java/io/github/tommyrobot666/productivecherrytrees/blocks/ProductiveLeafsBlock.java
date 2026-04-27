package io.github.tommyrobot666.productivecherrytrees.blocks;

import io.github.tommyrobot666.productivecherrytrees.ProductiveCherryTrees;
import io.github.tommyrobot666.productivecherrytrees.recipes.ModRecipeTypes;
import io.github.tommyrobot666.productivecherrytrees.recipes.PetalFusionRecipe;
import io.github.tommyrobot666.productivecherrytrees.recipes.TwoBlocksInput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.UntintedParticleLeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ProductiveLeafsBlock extends UntintedParticleLeavesBlock {
	static final int FUSION_PARTICLES = 23;
	static final int PLACE_PARTICLES = 10;
	final ProductivePetalsBlock droppedPetals;
	final double dropPetalsChance;

	public ProductiveLeafsBlock(float leafParticleChance, ParticleOptions leafParticle, double dropPetalsChance, ProductivePetalsBlock droppedPetals, Properties properties) {
		super(leafParticleChance, leafParticle, properties);
		this.droppedPetals = droppedPetals;
		this.dropPetalsChance = dropPetalsChance;
	}

	@Override
	protected void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		super.randomTick(state, level, pos, random);

		if (random.nextDouble() < dropPetalsChance) {
			BlockPos.MutableBlockPos searchDown = pos.mutable();
			do {
				searchDown.move(0,-1,0);
			} while (level.isInsideBuildHeight(searchDown) &&
				(level.getBlockState(searchDown).canBeReplaced() || level.getBlockState(searchDown).getBlock() instanceof ProductivePetalsBlock));

			// stopped after one step, can't replace block directly below
			if (searchDown.equals(pos.below())){
				return;
			}

			// reusing searchDown, but renaming the var
			BlockPos placeLocation = searchDown.move(0,1,0);
			BlockState stateAtPlaceLocation = level.getBlockState(placeLocation);
			if (stateAtPlaceLocation.getBlock() instanceof ProductivePetalsBlock){
				Block fusedPetals = attemptPetalFusion(level,stateAtPlaceLocation.getBlock(),random);
				if (fusedPetals == null) return;
				for (int i = 0; i < FUSION_PARTICLES; i++) {
					level.addParticle(ParticleTypes.HAPPY_VILLAGER,
						random.nextDouble() + pos.getX(),
						random.nextDouble() + pos.getY(),
						random.nextDouble() + pos.getZ(),
						random.nextDouble(), random.nextDouble(), random.nextDouble());
				}
				if (fusedPetals instanceof ProductivePetalsBlock) {
					level.setBlockAndUpdate(placeLocation, fusedPetals.defaultBlockState()
						.setValue(ProductivePetalsBlock.AMOUNT,
							Math.min(4, level.getBlockState(placeLocation).getValue(ProductivePetalsBlock.AMOUNT) + 1)));
				} else {
					level.setBlockAndUpdate(placeLocation, fusedPetals.defaultBlockState());
				}
			} else {
				for (int i = 0; i < PLACE_PARTICLES; i++) {
					level.addParticle(ParticleTypes.HAPPY_VILLAGER,
						random.nextDouble() + pos.getX(),
						random.nextDouble() + pos.getY(),
						random.nextDouble() + pos.getZ(),
						random.nextDouble(), random.nextDouble(), random.nextDouble());
				}
				level.setBlockAndUpdate(placeLocation,droppedPetals.defaultBlockState());
			}
		}
	}

	private Block attemptPetalFusion(@NotNull ServerLevel level, Block original, @NotNull RandomSource random) {
		// the .sorted randomizes it
		List<@NotNull PetalFusionRecipe> matchingFusions = level.recipeAccess().getAllMatches(ModRecipeTypes.PETAL_FUSION_TYPE,
			new TwoBlocksInput(original,droppedPetals),level).map(RecipeHolder::value)
			.sorted((x,y) -> random.nextInt()).toList();

		for (@NotNull PetalFusionRecipe fusion : matchingFusions) {
			if (fusion.getChance() > random.nextDouble()){
				return fusion.getOutput();
			}
		}

		// don't replace other petal types when fusion fails
		if (original != droppedPetals){
			return null;
		}
		return droppedPetals;
	}

	@Override
	protected boolean isRandomlyTicking(@NotNull BlockState state) {
		return true;
	}
}
