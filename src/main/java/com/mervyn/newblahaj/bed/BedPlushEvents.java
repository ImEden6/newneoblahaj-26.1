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

public final class BedPlushEvents {
    private BedPlushEvents() {}

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
            boolean wouldAct = (player.isSneaking() && held.isEmpty())
                || held.getItem() instanceof CuddlyItem || held.isIn(ModItemTags.PLUSHIES);
            return wouldAct ? ActionResult.SUCCESS : ActionResult.PASS;
        }

        ServerWorld serverWorld = (ServerWorld) world;
        BedPlushState plushState = BedPlushState.get(serverWorld);
        ItemStack onBed = plushState.getPlush(headPos);

        if (player.isSneaking() && held.isEmpty() && !onBed.isEmpty()) {
            giveOrDrop(world, headPos, player, onBed.copy());
            plushState.removePlush(headPos);
            BedPlushNetworking.broadcastRemoval(serverWorld, headPos);
            return ActionResult.success(false);
        }

        if (held.getItem() instanceof CuddlyItem || held.isIn(ModItemTags.PLUSHIES)) {
            if (!onBed.isEmpty()) {
                giveOrDrop(world, headPos, player, onBed.copy());
            }
            ItemStack toPlace = held.split(1);
            plushState.setPlush(headPos, toPlace);
            BedPlushNetworking.broadcastPlacement(serverWorld, headPos, toPlace);
            return ActionResult.success(false);
        }

        return ActionResult.PASS;
    }

    private static void giveOrDrop(World world, BlockPos pos, PlayerEntity player, ItemStack stack) {
        if (!player.getInventory().insertStack(stack)) {
            Block.dropStack(world, pos, stack);
        }
    }
}
