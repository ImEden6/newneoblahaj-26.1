package com.mervyn.newblahaj.mixin;

import com.mervyn.newblahaj.ModSounds;
import com.mervyn.newblahaj.block.CuddlyBlock;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockBehaviour.BlockStateBase.class)
public class CuddlyBlockProjectileMixin {

    @Inject(method = "onProjectileHit", at = @At("HEAD"))
    private void newblahaj$cuddlyProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile, CallbackInfo ci) {
        if (state.getBlock() instanceof CuddlyBlock) {
            level.playSound(null, hit.getBlockPos(), ModSounds.BLOCK_CUDDLY_ITEM_HIT, SoundSource.BLOCKS, 0.5f, 1.0f);
        }
    }
}
