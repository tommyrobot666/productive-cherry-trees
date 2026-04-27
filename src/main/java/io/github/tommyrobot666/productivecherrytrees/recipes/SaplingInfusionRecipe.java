package io.github.tommyrobot666.productivecherrytrees.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SaplingInfusionRecipe implements Recipe<@NotNull TwoBlocksInput> {
	public static final MapCodec<SaplingInfusionRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
		(instance) -> instance.group(
			Identifier.CODEC.fieldOf("sapling")
				.forGetter((r) -> BuiltInRegistries.BLOCK.getKey(r.sapling)),
			Identifier.CODEC.fieldOf("petals")
				.forGetter((r) -> BuiltInRegistries.BLOCK.getKey(r.petals)),
			Identifier.CODEC.fieldOf("output")
				.forGetter((r) -> BuiltInRegistries.BLOCK.getKey(r.output)),
			Codec.STRING.fieldOf("group").forGetter((r) -> r.group)
		).apply(instance, SaplingInfusionRecipe::new));
	public static final Codec<SaplingInfusionRecipe> CODEC = MAP_CODEC.codec();
	public static final StreamCodec<RegistryFriendlyByteBuf, SaplingInfusionRecipe> STREAM_CODEC = StreamCodec.of(
		SaplingInfusionRecipe::streamEncode, SaplingInfusionRecipe::streamDecode
	);

	private static SaplingInfusionRecipe streamDecode(RegistryFriendlyByteBuf buf) {
		return buf.readLenientJsonWithCodec(CODEC);
	}

	private static void streamEncode(RegistryFriendlyByteBuf buf, SaplingInfusionRecipe r) {
		// this is STILL a bad way to send the data
		buf.writeJsonWithCodec(CODEC, r);
	}

	public static final RecipeSerializer<@NotNull SaplingInfusionRecipe> SERIALIZER = new RecipeSerializer<>(MAP_CODEC, STREAM_CODEC);

	final Block sapling;
	final Block petals;
	final Block output;
	final String group;

	public SaplingInfusionRecipe(Block sapling, Block petals, Block output, String group) {
		this.sapling = sapling;
		this.petals = petals;
		this.output = output;
		this.group = group;
	}

	public SaplingInfusionRecipe(Identifier sapling, Identifier petals, Identifier output, String group) {
		this(BuiltInRegistries.BLOCK.getValue(sapling),BuiltInRegistries.BLOCK.getValue(petals),
			BuiltInRegistries.BLOCK.getValue(output),group);
	}

	@Override
	public boolean matches(TwoBlocksInput input, @NotNull Level level) {
		return input.original() == sapling && input.combining() == petals;
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
		return ModRecipeTypes.SAPLING_INFUSION_TYPE;
	}

	@Override
	public @NotNull PlacementInfo placementInfo() {
		return PlacementInfo.NOT_PLACEABLE;/*PlacementInfo.create(List.of(
			Ingredient.of(sapling),
			Ingredient.of(petals)
		));*/
	}

	/** @noinspection DataFlowIssue*/
	@Override
	public @NotNull RecipeBookCategory recipeBookCategory() {
		return null;//RecipeBookCategories.;
	}

	public Block getOutput() {
		return output;
	}
}
