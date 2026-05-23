package com.mervyn.newblahaj.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mervyn.newblahaj.bed.BedPlushHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BedRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.core.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BedRenderer.class)
public class BedRendererMixin {

    @Inject(method = "render", at = @At("TAIL"))
    private void newblahaj$renderBedPlush(BedBlockEntity bed, float partialTick, PoseStack poseStack,
            MultiBufferSource bufferSource, int combinedLight, int combinedOverlay, CallbackInfo ci) {
        BlockState stateHere = bed.getBlockState();
        if (!(stateHere.getBlock() instanceof BedBlock) || stateHere.getValue(BedBlock.PART) != BedPart.HEAD) {
            return;
        }
        ItemStack plush = ((BedPlushHolder) bed).newblahaj$getPlushItem();
        if (plush == null || plush.isEmpty()) {
            return;
        }
        Direction facing = stateHere.getValue(BedBlock.FACING);
        poseStack.pushPose();

        poseStack.translate(0.5, 0.75, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(-facing.toYRot()));
        poseStack.translate(0, 0, 0.25);

        boolean occupied = stateHere.getValue(BedBlock.OCCUPIED);
        if (occupied) {
            poseStack.translate(0, -0.125, 0.1);
        }

        Minecraft.getInstance().getItemRenderer().renderStatic(plush, ItemDisplayContext.FIXED, combinedLight,
                combinedOverlay, poseStack, bufferSource, bed.getLevel(), 0);

        poseStack.popPose();
    }
}
