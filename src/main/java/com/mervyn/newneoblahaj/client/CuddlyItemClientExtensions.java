package com.mervyn.newneoblahaj.client;

import com.mervyn.newneoblahaj.registry.ModItemTags;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.Nullable;

public final class CuddlyItemClientExtensions implements IClientItemExtensions {

    @Override
    @Nullable
    public HumanoidModel.ArmPose getArmPose(LivingEntity entity, InteractionHand hand, ItemStack stack) {
        if (!stack.is(ModItemTags.PLUSHIES)) {
            return null;
        }
        return PlushArmPoses.HELD_PLUSH;
    }
}
