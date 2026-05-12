package com.mervyn.newneoblahaj.client;

import com.mervyn.newneoblahaj.block.ModBlocks;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;

public final class ClientExtensionRegistrar {
    private ClientExtensionRegistrar() {}

    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        CuddlyItemClientExtensions ext = new CuddlyItemClientExtensions();
        event.registerItem(ext,
            ModBlocks.GRAY_SHARK_ITEM.get(),
            ModBlocks.BLAHAJ_ITEM.get(),
            ModBlocks.BLAVINGAD_ITEM.get(),
            ModBlocks.BREAD_ITEM.get(),
            ModBlocks.BROWN_BEAR_ITEM.get(),
            ModBlocks.CAPYBARA_ITEM.get());
        for (DeferredHolder<Item, ?> item : ModBlocks.PRIDE_ITEMS) {
            event.registerItem(ext, item.get());
        }
    }
}
