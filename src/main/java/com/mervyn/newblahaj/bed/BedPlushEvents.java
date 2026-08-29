package com.mervyn.newblahaj.bed;

import com.mervyn.newblahaj.block.CuddlyItem;
import com.mervyn.newblahaj.net.BedPlushNetworking;
import com.mervyn.newblahaj.registry.ModItemTags;

import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;
import java.util.function.Function;

public final class BedPlushEvents {
    private BedPlushEvents() {}

    // Common code can't depend on the client source set. The client entrypoint
    // (BedPlushClientNetworking.registerClient) injects the real cache lookup here so the
    // client-side prediction branch below can mirror the server's eligibility checks exactly
    // instead of guessing at them. Never consulted on a dedicated server.
    private static Function<BlockPos, ItemStack> clientPlushLookup = pos -> ItemStack.EMPTY;

    public static void setClientPlushLookup(Function<BlockPos, ItemStack> lookup) {
        clientPlushLookup = lookup;
    }

    public static ActionResult onUseBlock(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = world.getBlockState(pos);
        if (!(state.getBlock() instanceof BedBlock)) {
            return ActionResult.PASS;
        }
        Optional<BlockPos> headPosOpt = BedPlushSupport.getBedHeadPos(world, pos, state);
        if (headPosOpt.isEmpty()) {
            return ActionResult.PASS;
        }
        BlockPos headPos = headPosOpt.get();
        ItemStack held = player.getStackInHand(hand);

        if (world.isClient()) {
            // Real mutation happens server-side only; avoid double-applying on the client's
            // predicted side of the interaction.
            ItemStack onBed = clientPlushLookup.apply(headPos);
            boolean wouldAct = isRemovalAttempt(player, held, onBed) || isPlacementAttempt(held);
            return wouldAct ? ActionResult.SUCCESS : ActionResult.PASS;
        }

        ServerWorld serverWorld = (ServerWorld) world;
        BedPlushState plushState = BedPlushState.get(serverWorld);
        ItemStack onBed = plushState.getPlush(headPos);

        if (isRemovalAttempt(player, held, onBed)) {
            giveOrDrop(world, headPos, player, onBed.copy());
            plushState.removePlush(headPos);
            BedPlushNetworking.broadcastRemoval(serverWorld, headPos);
            return ActionResult.success(false);
        }

        if (isPlacementAttempt(held)) {
            if (!onBed.isEmpty()) {
                giveOrDrop(world, headPos, player, onBed.copy());
            }
            ItemStack toPlace = player.getAbilities().creativeMode ? held.copyWithCount(1) : held.split(1);
            plushState.setPlush(headPos, toPlace);
            BedPlushNetworking.broadcastPlacement(serverWorld, headPos, toPlace);
            return ActionResult.success(false);
        }

        return ActionResult.PASS;
    }

    private static boolean isRemovalAttempt(PlayerEntity player, ItemStack held, ItemStack onBed) {
        return player.isSneaking() && held.isEmpty() && !onBed.isEmpty();
    }

    private static boolean isPlacementAttempt(ItemStack held) {
        return held.getItem() instanceof CuddlyItem || held.isIn(ModItemTags.PLUSHIES);
    }

    private static void giveOrDrop(World world, BlockPos pos, PlayerEntity player, ItemStack stack) {
        if (!player.getInventory().insertStack(stack)) {
            Block.dropStack(world, pos, stack);
        }
    }
}
