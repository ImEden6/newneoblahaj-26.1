package com.mervyn.newblahaj.mixin;

import com.mervyn.newblahaj.block.CuddlyItem;
import com.mervyn.newblahaj.registry.ModItemTags;
import net.minecraft.entity.passive.AllayEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AllayEntity.class)
public class AllayEntityMixin {

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    public void preventTakePlush(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> info) {
        ItemStack held = player.getStackInHand(hand);
        if (held.getItem() instanceof CuddlyItem || held.isIn(ModItemTags.PLUSHIES)) {
            info.setReturnValue(ActionResult.PASS);
            info.cancel();
        }
    }
}
