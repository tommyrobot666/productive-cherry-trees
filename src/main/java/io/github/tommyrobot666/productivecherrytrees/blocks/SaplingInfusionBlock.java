package io.github.tommyrobot666.productivecherrytrees.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction8;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.Nullable;

public class SaplingInfusionBlock extends BaseEntityBlock {
	public static final BooleanProperty CRAFTING = BlockStateProperties.CRAFTING;
	public static final ParticleOptions PARTICLE = ParticleTypes.HAPPY_VILLAGER;
	public static final double PARTICLES_DISTANCE = 1.5;
	public static final int TOTAL_PARTICLES = 8*11;

	public SaplingInfusionBlock(Properties properties) {
		super(properties);
	}

	@Override
	protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
		return simpleCodec(SaplingInfusionBlock::new);
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, @NotNull BlockState> builder) {
		builder.add(CRAFTING);
	}

	double animateCurve(double x){
		return 1-(x*x*x);
	}

	@Override
	public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
		if (!state.getValue(CRAFTING)) return;

		for (Direction8 dir : Direction8.values()){
			Vec2 dirNormalized = new Vec2(dir.getStepX(), dir.getStepZ()).normalized();
			for (int i = 0; i < TOTAL_PARTICLES/8; i++) {
				double distance = random.nextDouble()*PARTICLES_DISTANCE;
				Vec3 position = new Vec3(dirNormalized.x * distance, animateCurve(distance), dirNormalized.y * distance).add(pos.getCenter());
				level.addParticle(PARTICLE, position.x, position.y, position.z, 0, 0, 0);
			}
		}
	}

	@Override
	public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
		return new SaplingInfusionBlockEntity(pos,state);
	}

	@Override
	public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NotNull Level level, @NotNull BlockState state, @NotNull BlockEntityType<T> type) {
		return createTickerHelper(type,ModBlocks.SAPLING_INFUSER_ENTITY,SaplingInfusionBlockEntity::ticker);
	}
}
