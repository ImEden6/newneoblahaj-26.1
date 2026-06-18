package com.mervyn.newneoblahaj.bed;

import com.mervyn.newneoblahaj.block.CuddlyItem;
import com.mervyn.newneoblahaj.registry.ModItemTags;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;

import java.util.Optional;
import java.util.Map;

public final class BedPlushEvents {
    private BedPlushEvents() {}

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        if (level.isClientSide() || !(level instanceof ServerLevel serverLevel)) {
            return;
        }
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        if (!(state.getBlock() instanceof BedBlock)) {
            return;
        }
        Optional<BlockPos> headPosOpt = BedPlushSupport.getBedHeadPos(level, pos, state);
        if (headPosOpt.isEmpty()) {
            return;
        }
        BlockPos headPos = headPosOpt.get();
        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        ItemStack held = player.getItemInHand(hand);

        BedPlushData data = level.getData(ModAttachments.BED_PLUSHES.get());
        Map<BlockPos, ItemStack> plushes = data.plushes();
        ItemStack onBed = plushes.getOrDefault(headPos, ItemStack.EMPTY);

        if (player.isShiftKeyDown() && held.isEmpty() && !onBed.isEmpty()) {
            if (!player.mayInteract(serverLevel, pos)) {
                return;
            }
            giveOrDrop(level, pos, player, onBed.copy());
            
            plushes.remove(headPos);
            level.setData(ModAttachments.BED_PLUSHES.get(), data);
            
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
            return;
        }

        if (held.getItem() instanceof CuddlyItem || held.is(ModItemTags.PLUSHIES)) {
            if (!player.mayInteract(serverLevel, pos)) {
                return;
            }
            if (!onBed.isEmpty()) {
                giveOrDrop(level, pos, player, onBed.copy());
            }
            ItemStack toPlace = held.split(1);
            
            plushes.put(headPos, toPlace);
            level.setData(ModAttachments.BED_PLUSHES.get(), data);
            
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.SUCCESS);
        }
    }

    private static void giveOrDrop(Level level, BlockPos pos, Player player, ItemStack stack) {
        if (!player.getInventory().add(stack)) {
            Block.popResource(level, pos, stack);
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BreakBlockEvent event) {
        if (!(event.getLevel() instanceof ServerLevel level)) {
            return;
        }
        BlockState state = event.getState();
        if (!(state.getBlock() instanceof BedBlock)) {
            return;
        }
        Optional<BlockPos> headPosOpt = BedPlushSupport.getBedHeadPos(level, event.getPos(), state);
        if (headPosOpt.isEmpty()) {
            return;
        }
        BlockPos headPos = headPosOpt.get();
        BedPlushData data = level.getData(ModAttachments.BED_PLUSHES.get());
        Map<BlockPos, ItemStack> plushes = data.plushes();
        ItemStack plush = plushes.get(headPos);
        if (plush == null || plush.isEmpty()) {
            return;
        }
        Block.popResource(level, headPos, plush.copy());
        
        plushes.remove(headPos);
        level.setData(ModAttachments.BED_PLUSHES.get(), data);
    }
}
