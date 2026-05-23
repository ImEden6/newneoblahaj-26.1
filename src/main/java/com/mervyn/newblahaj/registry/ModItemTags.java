package com.mervyn.newblahaj.registry;

import com.mervyn.newblahaj.NewBlahaj;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class ModItemTags {
    public static final TagKey<Item> PLUSHIES = TagKey.create(Registries.ITEM, NewBlahaj.id("plushies"));

    private ModItemTags() {}
}
