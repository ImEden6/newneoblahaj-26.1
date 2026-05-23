package com.mervyn.newblahaj;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;

public final class ModSounds {
    public static final SoundEvent CUDDLY_USE_1 = register("block.newblahaj.cuddly_item.use.1");
    public static final SoundEvent CUDDLY_USE_2 = register("block.newblahaj.cuddly_item.use.2");
    public static final SoundEvent CUDDLY_USE_3 = register("block.newblahaj.cuddly_item.use.3");
    public static final SoundEvent CUDDLY_USE_4 = register("block.newblahaj.cuddly_item.use.4");
    public static final SoundEvent CUDDLY_USE_5 = register("block.newblahaj.cuddly_item.use.5");

    public static final SoundEvent BLOCK_CUDDLY_ITEM_HIT = register("block.newblahaj.cuddly_item.hit");

    private ModSounds() {}

    private static SoundEvent register(String name) {
        SoundEvent sound = SoundEvent.createVariableRangeEvent(NewBlahaj.id(name));
        return Registry.register(BuiltInRegistries.SOUND_EVENT, NewBlahaj.id(name), sound);
    }

    public static void register() {
        // Class loading registers sounds
    }

    public static SoundEvent getRandomSqueak(RandomSource random) {
        return switch (random.nextInt(5)) {
            case 0 -> CUDDLY_USE_1;
            case 1 -> CUDDLY_USE_2;
            case 2 -> CUDDLY_USE_3;
            case 3 -> CUDDLY_USE_4;
            default -> CUDDLY_USE_5;
        };
    }
}
