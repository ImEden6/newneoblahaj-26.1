package com.mervyn.newblahaj.mixin;

import com.mervyn.newblahaj.ModSounds;
import com.mervyn.newblahaj.block.CuddlyBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractBlock.AbstractBlockState.class)
public class CuddlyBlockProjectileMixin {

    @Inject(method = "onProjectileHit", at = @At("HEAD"))
    private void newblahaj$cuddlyProjectileHit(World world, BlockState state, BlockHitResult hit, ProjectileEntity projectile, CallbackInfo ci) {
        if (state.getBlock() instanceof CuddlyBlock) {
            BlockPos pos = hit.getBlockPos();
            world.playSound(null, pos, ModSounds.BLOCK_CUDDLY_ITEM_HIT, SoundCategory.BLOCKS, 0.5f, 1.0f);
        }
    }
}
