package io.github.tommyrobot666.productivecherrytrees.recipes;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

/** @noinspection unused*/
public record TwoBlocksInput(Block original, Block combining) implements RecipeInput {
	@Override
	public @NotNull ItemStack getItem(int index) {
		return index == 2 ? combining.asItem().getDefaultInstance() : original.asItem().getDefaultInstance();
	}

	@Override
	public int size() {
		return 2;
	}

	public List<ItemStack> inputAsStacks(){
		return Stream.of(original,combining).map((b) -> b.asItem().getDefaultInstance()).toList();
	}
}
