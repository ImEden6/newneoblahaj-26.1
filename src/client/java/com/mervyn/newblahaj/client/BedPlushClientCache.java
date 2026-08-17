package com.mervyn.newblahaj.client;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Client only ever renders one world at a time, so this doesn't need to be keyed by
 * dimension - it just needs to be emptied on disconnect so a bed doesn't keep rendering
 * a plush left over from a previous session/server (see BED_PLUSH_SPEC.md test checklist).
 */
public final class BedPlushClientCache {
    private static final Map<BlockPos, ItemStack> PLUSHES = new ConcurrentHashMap<>();

    private BedPlushClientCache() {}

    public static ItemStack get(BlockPos headPos) {
        return PLUSHES.getOrDefault(headPos, ItemStack.EMPTY);
    }

    public static void put(BlockPos headPos, ItemStack stack) {
        if (stack.isEmpty()) {
            PLUSHES.remove(headPos);
        } else {
            PLUSHES.put(headPos, stack);
        }
    }

    public static void clear() {
        PLUSHES.clear();
    }
}
