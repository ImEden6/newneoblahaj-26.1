package com.mervyn.newneoblahaj.bed;

import com.mervyn.newneoblahaj.NewNeoBlahaj;

import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public final class ModAttachments {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
        DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, NewNeoBlahaj.MODID);

    public static final Supplier<AttachmentType<ItemStack>> BED_PLUSH = ATTACHMENT_TYPES.register(
        "bed_plush",
        () -> AttachmentType.builder(() -> ItemStack.EMPTY)
            .serialize(ItemStack.CODEC.fieldOf("stack"))
            .sync(ItemStack.OPTIONAL_STREAM_CODEC)
            .build()
    );

    private ModAttachments() {}

    public static void register(IEventBus modBus) {
        ATTACHMENT_TYPES.register(modBus);
    }
}
