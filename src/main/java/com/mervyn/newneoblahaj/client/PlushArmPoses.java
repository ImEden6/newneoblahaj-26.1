package com.mervyn.newneoblahaj.client;

import net.minecraft.client.model.HumanoidModel;

/**
 * Baseline arm pose for items in {@link com.mervyn.newneoblahaj.registry.ModItemTags#PLUSHIES}, applied from
 * {@link com.mervyn.newneoblahaj.client.CuddlyItemClientExtensions}. This mod does not register a separate
 * client mixin for an extra hug tuck; {@code CROSSBOW_HOLD} is the full pose here.
 */
public final class PlushArmPoses {
    private PlushArmPoses() {}

    /** See class Javadoc. */
    public static final HumanoidModel.ArmPose HELD_PLUSH = HumanoidModel.ArmPose.CROSSBOW_HOLD;
}
