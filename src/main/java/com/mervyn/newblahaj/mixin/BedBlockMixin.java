package com.mervyn.newblahaj.mixin;

import com.mervyn.newblahaj.bed.BedPlushState;
import com.mervyn.newblahaj.net.BedPlushNetworking;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BedBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.BedPart;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * {@code onStateReplaced} is declared on {@link AbstractBlock}, not {@link Block} or
 * {@link BedBlock} - {@code BedBlock} does not override it in 1.20.1, so this has to
 * mixin into the declaring class and instance-check, rather than mixing into BedBlock
 * directly (which would fail to apply: the method isn't present in BedBlock's own
 * bytecode to inject into).
 */
@Mixin(AbstractBlock.class)
public class BedBlockMixin {

    @Inject(method = "onStateReplaced", at = @At("HEAD"))
    private void newblahaj$dropPlushOnRemoval(BlockState state, World world, BlockPos pos,
            BlockState newState, boolean moved, CallbackInfo ci) {
        if (world.isClient() || !(state.getBlock() instanceof BedBlock) || state.isOf(newState.getBlock())) {
            return;
        }
        if (state.get(BedBlock.PART) != BedPart.HEAD) {
            // Only the head half stops carrying a plush; the foot alone being removed
            // leaves the head (and its plush) standing, matching vanilla's behavior of
            // not cascading a bed's two halves together on removal.
            return;
        }
        // pos IS the head here, straight from the pre-removal state - do not re-resolve it
        // through BedPlushSupport.getBedHeadPos(), which re-checks world.getBlockState(pos):
        // by the time onStateReplaced fires, the chunk storage already reflects newState
        // (air) at pos, so that check would always fail here and silently no-op.
        BlockPos headPos = pos;
        ServerWorld serverWorld = (ServerWorld) world;
        BedPlushState plushState = BedPlushState.get(serverWorld);
        ItemStack plush = plushState.getPlush(headPos);
        if (!plush.isEmpty()) {
            Block.dropStack(world, headPos, plush);
            plushState.removePlush(headPos);
            BedPlushNetworking.broadcastRemoval(serverWorld, headPos);
        }
    }
}
