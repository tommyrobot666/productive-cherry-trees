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

public class PetalFusionRecipe implements Recipe<@NotNull TwoBlocksInput> {
	public static final MapCodec<PetalFusionRecipe> MAP_CODEC = RecordCodecBuilder.mapCodec(
		(instance) -> instance.group(
			Identifier.CODEC.fieldOf("original")
				.forGetter((r) -> BuiltInRegistries.BLOCK.getKey(r.original)),
			Identifier.CODEC.fieldOf("combining")
				.forGetter((r) -> BuiltInRegistries.BLOCK.getKey(r.combining)),
			Identifier.CODEC.fieldOf("output")
				.forGetter((r) -> BuiltInRegistries.BLOCK.getKey(r.output)),
			Codec.STRING.fieldOf("group").forGetter((r) -> r.group),
			Codec.DOUBLE.fieldOf("chance").forGetter((r) -> r.chance)
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

	final Block original;
	final Block combining;
	final Block output;
	final String group;
	final double chance;

	public PetalFusionRecipe(Block original, Block combining, Block output, String group, double chance) {
		this.original = original;
		this.combining = combining;
		this.output = output;
		this.group = group;
		this.chance = chance;
	}

	public PetalFusionRecipe(Identifier original, Identifier combining, Identifier output, String group, double chance) {
		this(BuiltInRegistries.BLOCK.getValue(original),BuiltInRegistries.BLOCK.getValue(combining),
			BuiltInRegistries.BLOCK.getValue(output),group,chance);
	}

	@Override
	public boolean matches(TwoBlocksInput input, @NotNull Level level) {
		return input.original() == original && input.combining() == combining;
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
		return PlacementInfo.NOT_PLACEABLE;/*PlacementInfo.create(List.of(
			Ingredient.of(original),
			Ingredient.of(combining)
		));*/
	}

	/** @noinspection DataFlowIssue*/
	@Override
	public @NotNull RecipeBookCategory recipeBookCategory() {
		return null;//RecipeBookCategories.;
	}

	public double getChance() {
		return chance;
	}

	public Block getOutput() {
		return output;
	}
}
