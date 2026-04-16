package io.github.tommyrobot666.productivecherrytrees.blocks;

import io.github.tommyrobot666.productivecherrytrees.ProductiveCherryTrees;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.UntintedParticleLeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class ProductiveLeafsBlock extends UntintedParticleLeavesBlock {
	final Block droppedPetals;
	final double dropPetalsChance;

	public ProductiveLeafsBlock(float leafParticleChance, ParticleOptions leafParticle, double dropPetalsChance, Block droppedPetals, Properties properties) {
		super(leafParticleChance, leafParticle, properties);
		this.droppedPetals = droppedPetals;
		this.dropPetalsChance = dropPetalsChance;
	}

	@Override
	protected void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (random.nextDouble() < dropPetalsChance) {
			BlockPos.MutableBlockPos searchDown = pos.mutable();
			do {
				searchDown.move(0,-1,0);
			} while (level.isInsideBuildHeight(searchDown) && (level.getBlockState(searchDown).canBeReplaced() || level.getBlockState(searchDown).is(droppedPetals)));

			// stopped after one step, can't replace block directly below
			if (searchDown.equals(pos.below())){
				return;
			}

			// reusing searchDown, but renaming the var
			BlockPos placeLocation = searchDown.move(0,1,0);
			if (level.getBlockState(placeLocation).is(droppedPetals)){
				level.setBlockAndUpdate(placeLocation,droppedPetals.defaultBlockState()
					.setValue(ProductivePetalsBlock.AMOUNT,
						Math.min(4,level.getBlockState(placeLocation).getValue(ProductivePetalsBlock.AMOUNT)+1)));
			} else {
				level.setBlockAndUpdate(placeLocation,droppedPetals.defaultBlockState());
			}
		}
	}
}
