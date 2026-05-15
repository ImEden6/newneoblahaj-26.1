package com.mervyn.newneoblahaj.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import com.mervyn.newneoblahaj.bed.BedPlushSupport;
import com.mervyn.newneoblahaj.bed.ModAttachments;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BedRenderer;
import net.minecraft.client.renderer.blockentity.state.BedRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BedRenderer.class)
public class BedRendererMixin {

    @Inject(method = "submit", at = @At("TAIL"))
    private void newneoblahaj$submitBedPlush(BedRenderState state, PoseStack poseStack,
            SubmitNodeCollector submitNodeCollector, CameraRenderState camera, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.level == null) {
            return;
        }
        BlockPos pos = state.blockPos;
        if (pos == null) {
            return;
        }
        BlockState stateHere = mc.level.getBlockState(pos);
        if (!(stateHere.getBlock() instanceof BedBlock) || stateHere.getValue(BedBlock.PART) != BedPart.HEAD) {
            return;
        }
        Optional<BedBlockEntity> bedOpt = BedPlushSupport.getBedBlockEntity(mc.level, pos, stateHere);
        if (bedOpt.isEmpty()) {
            return;
        }
        BedBlockEntity bed = bedOpt.get();
        ItemStack plush = bed.getData(ModAttachments.BED_PLUSH.get());
        if (plush.isEmpty()) {
            return;
        }
        Direction facing = state.facing;
        poseStack.pushPose();

        // Base translation to block center and 12px height (to clear pillow + account
        // for 0.5x scale)
        poseStack.translate(0.5, 0.75, 0.5);
        poseStack.mulPose(Axis.YP.rotationDegrees(-facing.toYRot()));

        // Offset on the pillow (0.25 units toward the head)
        // Note: ItemDisplayContext.FIXED applies a 180-degree rotation, so Z+ moves
        // toward the head.
        poseStack.translate(0, 0, 0.25);

        // Nestle under sleeper head when bed is occupied (same flag as
        // BedBlock.OCCUPIED).
        BlockPos headPos = bed.getBlockPos();
        BlockState headState = mc.level.getBlockState(headPos);
        boolean occupied = headState.hasProperty(BedBlock.OCCUPIED) && headState.getValue(BedBlock.OCCUPIED);
        if (occupied) {
            poseStack.translate(0, -0.125, 0.1);
        }

        // Scale of 1.0f results in a final visual size of 0.5 blocks (8x8x8 px)
        // because FIXED applies an internal 0.5x multiplier.
        poseStack.scale(1.0f, 1.0f, 1.0f);

        // Per-submit state: a shared ItemStackRenderState can be overwritten by another
        // bed before queued
        // geometry is built, which shows the wrong plush (often whatever stack was
        // resolved last).
        ItemStackRenderState itemRenderState = new ItemStackRenderState();
        mc.getItemModelResolver().updateForTopItem(itemRenderState, plush, ItemDisplayContext.FIXED, bed.getLevel(),
                null, 0);
        itemRenderState.submit(poseStack, submitNodeCollector, state.lightCoords, OverlayTexture.NO_OVERLAY, 0);

        poseStack.popPose();
    }
}
