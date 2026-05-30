package com.mervyn.newblahaj.mixin.client;

import com.mervyn.newblahaj.client.PlushArmPoseHelper;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Runs after player-animation-lib's {@code PlayerModelMixin#setEmote} (priority 2000) so plush
 * cuddle arm rotations are not overwritten when Player Animator is installed.
 */
@Mixin(value = PlayerEntityModel.class, priority = 2100)
public class PlayerEntityModelMixin {

    @Inject(
        method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V",
        at = @At("TAIL")
    )
    private void newblahaj$applyPlushArmsAfterAnimator(LivingEntity entity, float limbAngle, float limbDistance,
            float animationProgress, float headYaw, float headPitch, CallbackInfo ci) {
        if (!PlushArmPoseHelper.isHoldingPlush(entity)) {
            return;
        }
        @SuppressWarnings("unchecked")
        BipedEntityModel<LivingEntity> model = (BipedEntityModel<LivingEntity>) (Object) this;
        PlushArmPoseHelper.applyCuddleArmRotations(model.rightArm, model.leftArm);
    }
}
