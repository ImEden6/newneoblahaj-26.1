package com.mervyn.newblahaj;

import com.mervyn.newblahaj.block.ModBlocks;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;

public class NewBlahajClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        NewBlahaj.LOGGER.info("New Blåhaj client setup");
        System.out.println("[NewBlahaj] NewBlahajClient: onInitializeClient called");

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
        BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout());
    }
}
