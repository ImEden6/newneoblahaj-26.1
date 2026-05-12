package com.mervyn.newneoblahaj;

import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModDataComponents {
    private static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
        DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, NewNeoBlahaj.MODID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<Component>> OWNER =
        DATA_COMPONENT_TYPES.register("owner", () -> DataComponentType.<Component>builder()
            .persistent(ComponentSerialization.CODEC)
            .networkSynchronized(ComponentSerialization.STREAM_CODEC)
            .cacheEncoding()
            .build());

    private ModDataComponents() {}

    public static void register(IEventBus modBus) {
        DATA_COMPONENT_TYPES.register(modBus);
    }
}
