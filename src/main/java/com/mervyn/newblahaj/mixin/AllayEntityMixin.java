package com.mervyn.newblahaj.mixin;

import com.mervyn.newblahaj.block.CuddlyItem;
import com.mervyn.newblahaj.registry.ModItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.allay.Allay;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Allay.class)
public class AllayEntityMixin {

    @Inject(method = "mobInteract", at = @At("HEAD"), cancellable = true)
    public void preventTakePlush(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> info) {
        ItemStack held = player.getItemInHand(hand);
        if (held.getItem() instanceof CuddlyItem || held.is(ModItemTags.PLUSHIES)) {
            info.setReturnValue(InteractionResult.PASS);
            info.cancel();
        }
    }
}
