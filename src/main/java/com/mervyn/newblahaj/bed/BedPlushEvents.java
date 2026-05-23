package com.mervyn.newblahaj.bed;

import com.mervyn.newblahaj.block.CuddlyItem;
import com.mervyn.newblahaj.registry.ModItemTags;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Optional;

public final class BedPlushEvents {
    private BedPlushEvents() {}

    public static InteractionResult onRightClickBlock(Player player, Level level, InteractionHand hand, BlockHitResult hitResult) {
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = level.getBlockState(pos);
        if (!(state.getBlock() instanceof BedBlock)) {
            return InteractionResult.PASS;
        }
        Optional<BedBlockEntity> bedOpt = BedPlushSupport.getBedBlockEntity(level, pos, state);
        if (bedOpt.isEmpty()) {
            return InteractionResult.PASS;
        }
        BedBlockEntity bed = bedOpt.get();
        ItemStack held = player.getItemInHand(hand);
        ItemStack onBed = ((BedPlushHolder) bed).newblahaj$getPlushItem();

        if (player.isShiftKeyDown() && held.isEmpty() && !onBed.isEmpty()) {
            if (!level.isClientSide()) {
                giveOrDrop(level, pos, player, onBed.copy());
                ((BedPlushHolder) bed).newblahaj$setPlushItem(ItemStack.EMPTY);
                bed.setChanged();
                BlockPos headPos = bed.getBlockPos();
                level.sendBlockUpdated(headPos, level.getBlockState(headPos), level.getBlockState(headPos), 3);
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        if (held.getItem() instanceof CuddlyItem || held.is(ModItemTags.PLUSHIES)) {
            if (!level.isClientSide()) {
                if (!onBed.isEmpty()) {
                    giveOrDrop(level, pos, player, onBed.copy());
                }
                ItemStack toPlace = held.split(1);
                ((BedPlushHolder) bed).newblahaj$setPlushItem(toPlace);
                bed.setChanged();
                BlockPos headPos = bed.getBlockPos();
                level.sendBlockUpdated(headPos, level.getBlockState(headPos), level.getBlockState(headPos), 3);
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        return InteractionResult.PASS;
    }

    private static void giveOrDrop(Level level, BlockPos pos, Player player, ItemStack stack) {
        if (!player.getInventory().add(stack)) {
            Block.popResource(level, pos, stack);
        }
    }
}
