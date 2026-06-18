package com.mervyn.newneoblahaj.bed;

import com.mervyn.newneoblahaj.NewNeoBlahaj;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;
import java.util.concurrent.ConcurrentHashMap;

public final class ModAttachments {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
        DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, NewNeoBlahaj.MODID);

    public static final Supplier<AttachmentType<BedPlushData>> BED_PLUSHES = ATTACHMENT_TYPES.register(
        "bed_plushes",
        () -> AttachmentType.builder(() -> new BedPlushData(new ConcurrentHashMap<>()))
            .serialize(BedPlushData.CODEC.fieldOf("plushes"))
            .sync(BedPlushData.STREAM_CODEC)
            .build()
    );

    private ModAttachments() {}

    public static void register(IEventBus modBus) {
        ATTACHMENT_TYPES.register(modBus);
    }
}
