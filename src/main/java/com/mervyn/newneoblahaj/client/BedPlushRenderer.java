package com.mervyn.newneoblahaj.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mervyn.newneoblahaj.NewNeoBlahaj;
import com.mervyn.newneoblahaj.bed.BedPlushData;
import com.mervyn.newneoblahaj.bed.ModAttachments;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.LightCoordsUtil;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ExtractLevelRenderStateEvent;
import net.neoforged.neoforge.client.event.SubmitCustomGeometryEvent;
import net.neoforged.neoforge.event.level.LevelEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@EventBusSubscriber(modid = NewNeoBlahaj.MODID, value = Dist.CLIENT)
public final class BedPlushRenderer {
    public static final ContextKey<BedPlushRenderState> BED_PLUSHES_KEY = new ContextKey<>(NewNeoBlahaj.id("bed_plushes"));
    private static final Map<BlockPos, ItemStackRenderState> STATE_CACHE = new ConcurrentHashMap<>();
    private static final Map<BlockPos, ItemStack> STACK_CACHE = new ConcurrentHashMap<>();

    private BedPlushRenderer() {}

    @SubscribeEvent
    public static void onExtractLevelRenderState(ExtractLevelRenderStateEvent event) {
        ClientLevel level = event.getLevel();
        if (level == null) {
            return;
        }

        BedPlushData data = level.getData(ModAttachments.BED_PLUSHES.get());
        if (data == null || data.plushes().isEmpty()) {
            STATE_CACHE.clear();
            STACK_CACHE.clear();
            return;
        }

        // Remove cached states for positions that no longer have plushies
        STATE_CACHE.keySet().removeIf(pos -> {
            if (!data.plushes().containsKey(pos)) {
                STACK_CACHE.remove(pos);
                return true;
            }
            return false;
        });

        BedPlushRenderState renderState = new BedPlushRenderState();
        Minecraft mc = Minecraft.getInstance();

        for (Map.Entry<BlockPos, ItemStack> entry : data.plushes().entrySet()) {
            BlockPos headPos = entry.getKey();
            ItemStack plush = entry.getValue();
            if (plush.isEmpty()) {
                continue;
            }

            BlockState state = level.getBlockState(headPos);
            if (!(state.getBlock() instanceof BedBlock) || state.getValue(BedBlock.PART) != BedPart.HEAD) {
                continue;
            }

            Direction facing = state.getValue(HorizontalDirectionalBlock.FACING);
            boolean occupied = state.hasProperty(BedBlock.OCCUPIED) && state.getValue(BedBlock.OCCUPIED);
            int lightCoords = LightCoordsUtil.getLightCoords(level, headPos);

            // Fetch or create the ItemStackRenderState from cache
            ItemStackRenderState itemState = STATE_CACHE.computeIfAbsent(headPos, k -> new ItemStackRenderState());

            ItemStack lastKnown = STACK_CACHE.get(headPos);
            if (lastKnown == null || !ItemStack.matches(lastKnown, plush)) {
                mc.getItemModelResolver().updateForTopItem(
                    itemState,
                    plush,
                    ItemDisplayContext.FIXED,
                    level,
                    null,
                    0
                );
                STACK_CACHE.put(headPos, plush.copy());
            }

            BedPlushRenderState.PlushEntry plushEntry = new BedPlushRenderState.PlushEntry(headPos, facing, occupied, lightCoords, itemState);
            renderState.entries.add(plushEntry);
        }

        if (!renderState.entries.isEmpty()) {
            event.getRenderState().setRenderData(BED_PLUSHES_KEY, renderState);
        }
    }

    @SubscribeEvent
    public static void onSubmitCustomGeometry(SubmitCustomGeometryEvent event) {
        LevelRenderState levelRenderState = event.getLevelRenderState();
        BedPlushRenderState renderState = levelRenderState.getRenderData(BED_PLUSHES_KEY);
        if (renderState == null || renderState.entries.isEmpty()) {
            return;
        }

        PoseStack poseStack = event.getPoseStack();
        SubmitNodeCollector submitNodeCollector = event.getSubmitNodeCollector();
        Vec3 camPos = levelRenderState.cameraRenderState.pos;
        double camX = camPos.x;
        double camY = camPos.y;
        double camZ = camPos.z;

        for (BedPlushRenderState.PlushEntry entry : renderState.entries) {
            poseStack.pushPose();

            // Translate relative to camera pos
            poseStack.translate(entry.headPos().getX() - camX, entry.headPos().getY() - camY, entry.headPos().getZ() - camZ);

            // Base translation to block center and height
            poseStack.translate(0.5, 0.75, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(-entry.facing().toYRot()));

            // Offset on the pillow (0.25 units toward the head)
            poseStack.translate(0, 0, 0.25);

            // Nestle under sleeper head when bed is occupied
            if (entry.occupied()) {
                poseStack.translate(0, -0.125, 0.1);
            }

            // Submit the item state
            entry.itemRenderState().submit(
                poseStack,
                submitNodeCollector,
                entry.lightCoords(),
                OverlayTexture.NO_OVERLAY,
                0
            );

            poseStack.popPose();
        }
    }

    @SubscribeEvent
    public static void onLevelUnload(LevelEvent.Unload event) {
        if (!(event.getLevel() instanceof ClientLevel)) {
            return;
        }
        STATE_CACHE.clear();
        STACK_CACHE.clear();
    }

    static class BedPlushRenderState {
        final List<PlushEntry> entries = new ArrayList<>();

        record PlushEntry(BlockPos headPos, Direction facing, boolean occupied, int lightCoords, ItemStackRenderState itemRenderState) {}
    }
}
