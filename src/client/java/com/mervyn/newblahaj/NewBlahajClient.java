package com.mervyn.newblahaj;

import com.mervyn.newblahaj.block.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;

public class NewBlahajClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        NewBlahaj.LOGGER.info("New Blåhaj client setup");

        registerCutout(ModBlocks.GRAY_SHARK_BLOCK);
        registerCutout(ModBlocks.BLAHAJ_BLOCK);
        registerCutout(ModBlocks.BLAVINGAD_BLOCK);
        registerCutout(ModBlocks.BREAD_BLOCK);
        registerCutout(ModBlocks.BROWN_BEAR_BLOCK);
        registerCutout(ModBlocks.CAPYBARA_BLOCK);
        for (Block block : ModBlocks.PRIDE_BLOCKS) {
            registerCutout(block);
        }
    }

    private void registerCutout(Block block) {
        BlockRenderLayerMap.INSTANCE.putBlock(block, RenderType.cutout());
    }
}
