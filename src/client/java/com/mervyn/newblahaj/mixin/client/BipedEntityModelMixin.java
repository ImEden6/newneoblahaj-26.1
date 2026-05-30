package com.mervyn.newblahaj.mixin.client;

import com.mervyn.newblahaj.client.PlushArmPoseHelper;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public class BipedEntityModelMixin {
    @Shadow
    public BipedEntityModel.ArmPose leftArmPose;

    @Shadow
    public BipedEntityModel.ArmPose rightArmPose;

    @Shadow
    public @Final ModelPart rightArm;

    @Shadow
    public @Final ModelPart leftArm;

    @Inject(
        method = "positionRightArm(Lnet/minecraft/entity/LivingEntity;)V",
        at = @At("TAIL"),
        require = 0
    )
    private void newblahaj$poseRightArm(LivingEntity entity, CallbackInfo ci) {
        if (!PlushArmPoseHelper.isHoldingPlush(entity) || this.rightArmPose != BipedEntityModel.ArmPose.CROSSBOW_HOLD) {
            return;
        }
        this.rightArm.pitch = -0.95F;
        this.rightArm.yaw = (float) (-Math.PI / 8);
    }

    @Inject(
        method = "positionLeftArm(Lnet/minecraft/entity/LivingEntity;)V",
        at = @At("TAIL"),
        require = 0
    )
    private void newblahaj$poseLeftArm(LivingEntity entity, CallbackInfo ci) {
        if (!PlushArmPoseHelper.isHoldingPlush(entity) || this.leftArmPose != BipedEntityModel.ArmPose.CROSSBOW_HOLD) {
            return;
        }
        this.leftArm.pitch = -0.90F;
        this.leftArm.yaw = (float) (Math.PI / 8);
    }
}
