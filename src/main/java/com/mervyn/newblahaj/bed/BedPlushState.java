package com.mervyn.newblahaj.bed;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.PersistentState;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * One instance per {@link ServerWorld}, so a bed at the same {@link BlockPos} in two
 * different dimensions never shares plush data. Only ever holds entries for beds that
 * currently have a plush on them - empty stacks are never stored.
 */
public final class BedPlushState extends PersistentState {
    private static final String ID = "newblahaj_bed_plushes";

    private final Map<BlockPos, ItemStack> plushes = new HashMap<>();

    public static BedPlushState get(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(BedPlushState::fromNbt, BedPlushState::new, ID);
    }

    public ItemStack getPlush(BlockPos headPos) {
        return plushes.getOrDefault(headPos, ItemStack.EMPTY);
    }

    public void setPlush(BlockPos headPos, ItemStack stack) {
        if (stack.isEmpty()) {
            removePlush(headPos);
            return;
        }
        plushes.put(headPos, stack.copy());
        markDirty();
    }

    public void removePlush(BlockPos headPos) {
        if (plushes.remove(headPos) != null) {
            markDirty();
        }
    }

    public void forEach(BiConsumer<BlockPos, ItemStack> consumer) {
        plushes.forEach(consumer);
    }

    private static BedPlushState fromNbt(NbtCompound tag) {
        BedPlushState state = new BedPlushState();
        NbtList list = tag.getList("Plushes", NbtElement.COMPOUND_TYPE);
        for (int i = 0; i < list.size(); i++) {
            NbtCompound entry = list.getCompound(i);
            BlockPos pos = new BlockPos(entry.getInt("X"), entry.getInt("Y"), entry.getInt("Z"));
            ItemStack stack = ItemStack.fromNbt(entry.getCompound("Item"));
            if (!stack.isEmpty()) {
                state.plushes.put(pos, stack);
            }
        }
        return state;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        NbtList list = new NbtList();
        plushes.forEach((pos, stack) -> {
            NbtCompound entry = new NbtCompound();
            entry.putInt("X", pos.getX());
            entry.putInt("Y", pos.getY());
            entry.putInt("Z", pos.getZ());
            entry.put("Item", stack.writeNbt(new NbtCompound()));
            list.add(entry);
        });
        tag.put("Plushes", list);
        return tag;
    }
}
