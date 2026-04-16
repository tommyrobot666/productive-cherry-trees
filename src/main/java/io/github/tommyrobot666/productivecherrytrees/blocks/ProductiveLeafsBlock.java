package io.github.tommyrobot666.productivecherrytrees.blocks;

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
	final float dropPetalsChance;

	public ProductiveLeafsBlock(float leafParticleChance, ParticleOptions leafParticle, float dropPetalsChance, Block droppedPetals, Properties properties) {
		super(leafParticleChance, leafParticle, properties);
		this.droppedPetals = droppedPetals;
		this.dropPetalsChance = dropPetalsChance;
	}

	@Override
	protected void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		//TODO: if ground under and random < chance, place petals
	}
}
