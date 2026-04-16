package io.github.tommyrobot666.productivecherrytrees.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PetalFusionRecipe implements Recipe<@NotNull TwoBlocksInput> {
	public static final MapCodec<PetalFusionRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
		(instance) -> instance.group(
			Block.CODEC.codec().listOf().fieldOf("input").forGetter((r) -> r.input),
			Block.CODEC.codec().fieldOf("output").forGetter((r) -> r.output),
			Codec.STRING.fieldOf("group").forGetter((r) -> r.group)
		).apply(instance, PetalFusionRecipe::new));
	public static final Codec<PetalFusionRecipe> CODEC = MAP_CODEC.codec();
	public static final StreamCodec<RegistryFriendlyByteBuf, PetalFusionRecipe> STREAM_CODEC = StreamCodec.of(
		PetalFusionRecipe::streamEncode, PetalFusionRecipe::streamDecode
	);

	private static PetalFusionRecipe streamDecode(RegistryFriendlyByteBuf buf) {
		return buf.readLenientJsonWithCodec(CODEC);
	}

	private static void streamEncode(RegistryFriendlyByteBuf buf, PetalFusionRecipe r) {
		// this is a bad way to send the data
		buf.writeJsonWithCodec(CODEC, r);
	}

	public static final RecipeSerializer<@NotNull PetalFusionRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

	final List<Block> input;
	final Block output;
	final String group;

	/** @noinspection unused*/
	public PetalFusionRecipe(List<Block> input, Block output, String group) {
		if (input.size() != 2){
			throw new IllegalArgumentException("Recipe should have just 2 inputs");
		}

		this.input = input;
		this.output = output;
		this.group = group;
	}

	public List<ItemStack> inputAsStacks(){
		return input.stream().map((b) -> b.asItem().getDefaultInstance()).toList();
	}

	/** @noinspection SlowListContainsAll*/
	@Override
	public boolean matches(TwoBlocksInput input, @NotNull Level level) {
		return inputAsStacks().containsAll(input.inputAsStacks());
	}

	@Override
	public @NotNull ItemStack assemble(TwoBlocksInput input) {
		return output.asItem().getDefaultInstance();
	}

	@Override
	public boolean showNotification() {
		return false;
	}

	@Override
	public @NotNull String group() {
		return group;
	}

	@Override
	public @NotNull RecipeSerializer<? extends @NotNull Recipe<@NotNull TwoBlocksInput>> getSerializer() {
		return SERIALIZER;
	}

	@Override
	public @NotNull RecipeType<? extends @NotNull Recipe<@NotNull TwoBlocksInput>> getType() {
		return ModRecipeTypes.PETAL_FUSION_TYPE;
	}

	@Override
	public @NotNull PlacementInfo placementInfo() {
		return PlacementInfo.create(input.stream().map(
			(b) -> Ingredient.of(b.asItem())
		).toList());
	}

	/** @noinspection DataFlowIssue*/
	@Override
	public @NotNull RecipeBookCategory recipeBookCategory() {
		return null;//RecipeBookCategories.;
	}
}
