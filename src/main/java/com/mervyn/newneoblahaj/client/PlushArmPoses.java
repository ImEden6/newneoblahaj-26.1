package com.mervyn.newneoblahaj.client;

import net.minecraft.client.model.HumanoidModel;

/**
 * Arm pose used when holding {@link com.mervyn.newneoblahaj.registry.ModItemTags#PLUSHIES}.
 * Minecraft 26.1 does not expose {@code ArmPose.create} on this classpath; the hug tuck is applied in
 * {@link com.mervyn.newneoblahaj.mixin.client.HumanoidModelPlushHugMixin} after crossbow-hold setup.
 */
public final class PlushArmPoses {
    private PlushArmPoses() {}

    /** Baseline pose; {@link com.mervyn.newneoblahaj.mixin.client.HumanoidModelPlushHugMixin} adds the cradle. */
    public static final HumanoidModel.ArmPose HELD_PLUSH = HumanoidModel.ArmPose.CROSSBOW_HOLD;
}
