package com.mervyn.newblahaj.net;

import com.mervyn.newblahaj.NewBlahaj;
import com.mervyn.newblahaj.bed.BedPlushState;

import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;

import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

/**
 * No attachment/data-component system exists on Fabric, so plush placement has to be
 * pushed to clients explicitly: a full dump on join (small data set, see BED_PLUSH_SPEC.md)
 * plus a targeted update to whoever currently has the affected bed's chunk loaded whenever
 * it changes.
 */
public final class BedPlushNetworking {
    public static final Identifier CHANNEL = NewBlahaj.id("bed_plush_sync");

    private BedPlushNetworking() {}

    public static void registerServer() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.player;
            ServerWorld world = player.getServerWorld();
            BedPlushState.get(world).forEach((pos, stack) -> ServerPlayNetworking.send(player, CHANNEL, writeBuf(pos, stack)));
        });
    }

    public static void broadcastPlacement(ServerWorld world, BlockPos headPos, ItemStack stack) {
        broadcast(world, headPos, stack);
    }

    public static void broadcastRemoval(ServerWorld world, BlockPos headPos) {
        broadcast(world, headPos, ItemStack.EMPTY);
    }

    private static void broadcast(ServerWorld world, BlockPos headPos, ItemStack stack) {
        for (ServerPlayerEntity player : PlayerLookup.tracking(world, headPos)) {
            ServerPlayNetworking.send(player, CHANNEL, writeBuf(headPos, stack));
        }
    }

    private static PacketByteBuf writeBuf(BlockPos pos, ItemStack stack) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBlockPos(pos);
        buf.writeItemStack(stack);
        return buf;
    }
}
