package com.mervyn.newneoblahaj.registry;

import com.mervyn.newneoblahaj.NewNeoBlahaj;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class ModItemTags {
    public static final TagKey<Item> PLUSHIES = TagKey.create(Registries.ITEM, NewNeoBlahaj.id("plushies"));

    private ModItemTags() {}
}
