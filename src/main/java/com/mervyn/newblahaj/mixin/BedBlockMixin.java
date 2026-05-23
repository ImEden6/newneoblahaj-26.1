package com.mervyn.newblahaj.mixin;

import com.mervyn.newblahaj.bed.BedPlushSupport;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BedBlockMixin {

    @Inject(method = "onRemove", at = @At("HEAD"))
    private void newblahaj$dropPlushOnRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving, CallbackInfo ci) {
        if (state.getBlock() instanceof BedBlock) {
            BedPlushSupport.dropPlushIfPresent(level, pos, state);
        }
    }
}
