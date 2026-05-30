package com.mervyn.newblahaj.registry;

import com.mervyn.newblahaj.NewBlahaj;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;

public final class ModItemTags {
    public static final TagKey<Item> PLUSHIES = TagKey.of(Registries.ITEM.getKey(), NewBlahaj.id("plushies"));

    private ModItemTags() {}
}
