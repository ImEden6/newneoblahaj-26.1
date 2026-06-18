package com.mervyn.newneoblahaj.bed;

import java.util.Optional;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;

public final class BedPlushSupport {
    private BedPlushSupport() {}

    /**
     * We prefer keeping the plushie at the head of the bed (near the pillow).
     */
    public static Optional<BlockPos> getBedHeadPos(LevelAccessor level, BlockPos pos, BlockState state) {
        if (!(state.getBlock() instanceof BedBlock)) {
            return Optional.empty();
        }
        BedPart part = state.getValue(BedBlock.PART);
        Direction facing = state.getValue(HorizontalDirectionalBlock.FACING);
        // In vanilla BedBlock, the foot part's FACING direction points toward the head part.
        // So from the foot, pos.relative(facing) always yields the head position.
        BlockPos headPos = part == BedPart.HEAD ? pos : pos.relative(facing);
        if (!(level.getBlockState(headPos).getBlock() instanceof BedBlock)) {
            return Optional.empty();
        }
        return Optional.of(headPos);
    }

    public static Optional<BlockPos> getBedHeadPos(LevelAccessor level, BlockPos pos) {
        return getBedHeadPos(level, pos, level.getBlockState(pos));
    }
}
