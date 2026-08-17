package com.mervyn.newblahaj.mixin.client;

import com.mervyn.newblahaj.client.BedPlushClientCache;

import net.minecraft.block.BedBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.block.enums.BedPart;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BedBlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BedBlockEntityRenderer.class)
public class BedRendererMixin {

    @Inject(method = "render", at = @At("TAIL"))
    private void newblahaj$renderPlush(BedBlockEntity entity, float tickDelta, MatrixStack matrices,
            VertexConsumerProvider vertexConsumers, int light, int overlay, CallbackInfo ci) {
        World world = entity.getWorld();
        if (world == null) {
            return;
        }
        BlockPos pos = entity.getPos();
        BlockState state = world.getBlockState(pos);
        if (!(state.getBlock() instanceof BedBlock) || state.get(BedBlock.PART) != BedPart.HEAD) {
            return;
        }
        ItemStack plush = BedPlushClientCache.get(pos);
        if (plush.isEmpty()) {
            return;
        }

        Direction facing = state.get(HorizontalFacingBlock.FACING);
        boolean occupied = state.get(BedBlock.OCCUPIED);

        matrices.push();

        // Base translation to block center and 12px height (to clear the pillow).
        matrices.translate(0.5, 0.75, 0.5);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-facing.asRotation()));

        // Offset onto the pillow (0.25 units toward the head). FIXED display context
        // applies a 180-degree rotation, so +Z moves toward the head here.
        matrices.translate(0, 0, 0.25);

        // Nestle under the sleeper's head when occupied, instead of clipping into it.
        if (occupied) {
            matrices.translate(0, -0.125, 0.1);
        }

        MinecraftClient.getInstance().getItemRenderer().renderItem(
                plush, ModelTransformationMode.FIXED, light, overlay, matrices, vertexConsumers, world, 0);

        matrices.pop();
    }
}
