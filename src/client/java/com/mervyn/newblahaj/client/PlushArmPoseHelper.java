package com.mervyn.newblahaj.client;

import com.mervyn.newblahaj.block.CuddlyItem;
import com.mervyn.newblahaj.registry.ModItemTags;
import net.minecraft.client.model.ModelPart;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public final class PlushArmPoseHelper {
    private PlushArmPoseHelper() {}

    public static boolean isHoldingPlush(LivingEntity entity) {
        return isPlushStack(entity.getMainHandStack()) || isPlushStack(entity.getOffHandStack());
    }

    public static boolean isPlushStack(ItemStack stack) {
        return !stack.isEmpty() && (stack.getItem() instanceof CuddlyItem || stack.isIn(ModItemTags.PLUSHIES));
    }

    public static void applyCuddleArmRotations(ModelPart rightArm, ModelPart leftArm) {
        rightArm.pitch = -0.95F;
        rightArm.yaw = (float) (-Math.PI / 8);
        leftArm.pitch = -0.90F;
        leftArm.yaw = (float) (Math.PI / 8);
    }
}
