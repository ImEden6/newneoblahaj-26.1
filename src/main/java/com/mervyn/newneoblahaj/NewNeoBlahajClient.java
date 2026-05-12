package com.mervyn.newneoblahaj;

import com.mervyn.newneoblahaj.client.ClientExtensionRegistrar;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = NewNeoBlahaj.MODID, value = Dist.CLIENT)
public final class NewNeoBlahajClient {

    private NewNeoBlahajClient() {}

    @SubscribeEvent
    public static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
        ClientExtensionRegistrar.onRegisterClientExtensions(event);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        NewNeoBlahaj.LOGGER.info("New Neo Blåhaj client setup complete");
    }
}
