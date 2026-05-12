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
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;
import java.util.Optional;

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
        Optional<BedBlockEntity> bedOpt = BedPlushSupport.getBedBlockEntity(level, pos, state);
        if (bedOpt.isEmpty()) {
            return;
        }
        BedBlockEntity bed = bedOpt.get();
        Player player = event.getEntity();
        InteractionHand hand = event.getHand();
        ItemStack held = player.getItemInHand(hand);
        ItemStack onBed = bed.getData(ModAttachments.BED_PLUSH.get());

        if (player.isShiftKeyDown() && held.isEmpty() && !onBed.isEmpty()) {
            if (!player.mayInteract(serverLevel, pos)) {
                return;
            }
            giveOrDrop(level, pos, player, onBed.copy());
            bed.removeData(ModAttachments.BED_PLUSH.get());
            bed.setChanged();
            BlockPos headPos = bed.getBlockPos();
            level.sendBlockUpdated(headPos, level.getBlockState(headPos), level.getBlockState(headPos), 3);
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
            bed.setData(ModAttachments.BED_PLUSH.get(), toPlace);
            bed.setChanged();
            BlockPos headPos = bed.getBlockPos();
            level.sendBlockUpdated(headPos, level.getBlockState(headPos), level.getBlockState(headPos), 3);
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
        Optional<BedBlockEntity> bedOpt = BedPlushSupport.getBedBlockEntity(level, event.getPos(), state);
        if (bedOpt.isEmpty()) {
            return;
        }
        BedBlockEntity bed = bedOpt.get();
        ItemStack plush = bed.getData(ModAttachments.BED_PLUSH.get());
        if (plush.isEmpty()) {
            return;
        }
        BlockPos dropPos = bed.getBlockPos();
        Block.popResource(level, dropPos, plush.copy());
        bed.removeData(ModAttachments.BED_PLUSH.get());
    }
}
