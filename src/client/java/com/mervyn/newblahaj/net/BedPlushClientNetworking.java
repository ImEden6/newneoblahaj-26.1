package com.mervyn.newblahaj.net;

import com.mervyn.newblahaj.bed.BedPlushEvents;
import com.mervyn.newblahaj.client.BedPlushClientCache;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;

public final class BedPlushClientNetworking {
    private BedPlushClientNetworking() {}

    public static void registerClient() {
        ClientPlayNetworking.registerGlobalReceiver(BedPlushNetworking.CHANNEL, (client, handler, buf, sender) -> {
            BlockPos pos = buf.readBlockPos();
            ItemStack stack = buf.readItemStack();
            client.execute(() -> BedPlushClientCache.put(pos, stack));
        });

        ClientPlayNetworking.registerGlobalReceiver(BedPlushNetworking.CLEAR_CHANNEL, (client, handler, buf, sender) ->
            client.execute(BedPlushClientCache::clear));

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> BedPlushClientCache.clear());

        BedPlushEvents.setClientPlushLookup(BedPlushClientCache::get);
    }
}
