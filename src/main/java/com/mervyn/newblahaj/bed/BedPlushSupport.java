package com.mervyn.newblahaj.bed;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.enums.BedPart;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;

import java.util.Optional;

public final class BedPlushSupport {
    private BedPlushSupport() {}

    /**
     * We keep the plushie at the head of the bed (near the pillow), so every lookup is
     * normalized to the head position regardless of which half was interacted with.
     */
    public static Optional<BlockPos> getBedHeadPos(WorldAccess world, BlockPos pos, BlockState state) {
        if (!(state.getBlock() instanceof BedBlock)) {
            return Optional.empty();
        }
        BedPart part = state.get(BedBlock.PART);
        Direction facing = state.get(HorizontalFacingBlock.FACING);
        BlockPos headPos = part == BedPart.HEAD ? pos : pos.offset(facing);
        if (!(world.getBlockState(headPos).getBlock() instanceof BedBlock)) {
            return Optional.empty();
        }
        return Optional.of(headPos);
    }
}
