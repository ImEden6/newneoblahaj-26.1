package com.mervyn.newblahaj.client;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Client only ever renders one world at a time, so this doesn't need to be keyed by
 * dimension. It just needs to be emptied whenever the current world's plush data goes
 * stale, on disconnect or on a dimension change (see BedPlushNetworking's clear channel),
 * so a bed doesn't keep rendering a plush left over from a previous world.
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
