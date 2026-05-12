package com.mervyn.newneoblahaj;

import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModSounds {
    private static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Registries.SOUND_EVENT, NewNeoBlahaj.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> CUDDLY_USE_1 = registerUse(1);
    public static final DeferredHolder<SoundEvent, SoundEvent> CUDDLY_USE_2 = registerUse(2);
    public static final DeferredHolder<SoundEvent, SoundEvent> CUDDLY_USE_3 = registerUse(3);
    public static final DeferredHolder<SoundEvent, SoundEvent> CUDDLY_USE_4 = registerUse(4);
    public static final DeferredHolder<SoundEvent, SoundEvent> CUDDLY_USE_5 = registerUse(5);

    public static final DeferredHolder<SoundEvent, SoundEvent> BLOCK_CUDDLY_ITEM_HIT =
        SOUND_EVENTS.register("block.newneoblahaj.cuddly_item.hit", () -> SoundEvent.createVariableRangeEvent(NewNeoBlahaj.id("block.newneoblahaj.cuddly_item.hit")));

    private ModSounds() {}

    private static DeferredHolder<SoundEvent, SoundEvent> registerUse(int n) {
        return SOUND_EVENTS.register("block.newneoblahaj.cuddly_item.use." + n, () -> SoundEvent.createVariableRangeEvent(NewNeoBlahaj.id("block.newneoblahaj.cuddly_item.use." + n)));
    }

    public static void register(IEventBus modBus) {
        SOUND_EVENTS.register(modBus);
    }

    public static SoundEvent getRandomSqueak(RandomSource random) {
        return switch (random.nextInt(5)) {
            case 0 -> CUDDLY_USE_1.get();
            case 1 -> CUDDLY_USE_2.get();
            case 2 -> CUDDLY_USE_3.get();
            case 3 -> CUDDLY_USE_4.get();
            default -> CUDDLY_USE_5.get();
        };
    }
}
