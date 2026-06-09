package com.mervyn.newblahaj.block;

import com.mervyn.newblahaj.NewBlahaj;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public final class ModBlocks {
    public static final Block GRAY_SHARK_BLOCK = registerBlock("gray_shark", new CuddlyBlock(AbstractBlock.Settings.copy(Blocks.LIGHT_GRAY_WOOL).nonOpaque()));
    public static final Item GRAY_SHARK_ITEM = registerItem("gray_shark", new CuddlyItem(GRAY_SHARK_BLOCK, new Item.Settings().maxCount(1), "block.newblahaj.gray_shark.tooltip"));

    public static final Block BLAHAJ_BLOCK = registerBlock("blue_shark", new CuddlyBlock(AbstractBlock.Settings.copy(Blocks.CYAN_WOOL).nonOpaque()));
    public static final Item BLAHAJ_ITEM = registerItem("blue_shark", new CuddlyItem(BLAHAJ_BLOCK, new Item.Settings().maxCount(1), "block.newblahaj.blue_shark.tooltip"));

    public static final Block BLAVINGAD_BLOCK = registerBlock("blue_whale", new CuddlyBlock(AbstractBlock.Settings.copy(Blocks.BLUE_WOOL).nonOpaque()));
    public static final Item BLAVINGAD_ITEM = registerItem("blue_whale", new CuddlyItem(BLAVINGAD_BLOCK, new Item.Settings().maxCount(1), "block.newblahaj.blue_whale.tooltip"));

    public static final Block BREAD_BLOCK = registerBlock("bread", new CuddlyBlock(AbstractBlock.Settings.copy(Blocks.ORANGE_WOOL).nonOpaque()));
    public static final Item BREAD_ITEM = registerItem("bread", new CuddlyItem(BREAD_BLOCK, new Item.Settings().maxCount(1), null));

    public static final Block BROWN_BEAR_BLOCK = registerBlock("brown_bear", new CuddlyBlock(AbstractBlock.Settings.copy(Blocks.BROWN_WOOL).nonOpaque()));
    public static final Item BROWN_BEAR_ITEM = registerItem("brown_bear", new CuddlyItem(BROWN_BEAR_BLOCK, new Item.Settings().maxCount(1), "block.newblahaj.brown_bear.tooltip"));

    public static final Block CAPYBARA_BLOCK = registerBlock("capybara", new CuddlyBlock(AbstractBlock.Settings.copy(Blocks.BROWN_WOOL).nonOpaque()));
    public static final Item CAPYBARA_ITEM = registerItem("capybara", new CuddlyItem(CAPYBARA_BLOCK, new Item.Settings().maxCount(1), "block.newblahaj.capybara.tooltip"));

    public static final Block CREATURE_BLOCK = registerBlock("creature", new CuddlyBlock(AbstractBlock.Settings.copy(Blocks.BLACK_WOOL).nonOpaque()));
    public static final Item CREATURE_ITEM = registerItem("creature", new CuddlyItem(CREATURE_BLOCK, new Item.Settings().maxCount(1), "block.newblahaj.creature.tooltip"));


    public static final List<String> PRIDE_NAMES = List.of(
        "ace", "agender", "aro", "aroace", "bi", "demiboy", "demigirl",
        "demi_r", "demi_s", "enby", "gay", "genderfluid", "genderqueer", "greyrose",
        "grey_r", "grey_s", "intersex", "lesbian", "pan", "poly", "pride", "trans");

    public static final List<Block> PRIDE_BLOCKS = new ArrayList<>();
    public static final List<Item> PRIDE_ITEMS = new ArrayList<>();

    static {
        for (String name : PRIDE_NAMES) {
            Block block = registerBlock(name + "_shark", new CuddlyBlock(AbstractBlock.Settings.copy(Blocks.WHITE_WOOL).nonOpaque()));
            Item item = registerItem(name + "_shark", new CuddlyItem(block, new Item.Settings().maxCount(1), "block.newblahaj.blue_shark.tooltip"));
            PRIDE_BLOCKS.add(block);
            PRIDE_ITEMS.add(item);
        }
    }

    private ModBlocks() {}

    private static Block registerBlock(String name, Block block) {
        return Registry.register(Registries.BLOCK, NewBlahaj.id(name), block);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, NewBlahaj.id(name), item);
    }

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.add(GRAY_SHARK_ITEM);
            content.add(BLAHAJ_ITEM);
            content.add(BLAVINGAD_ITEM);
            content.add(BREAD_ITEM);
            content.add(BROWN_BEAR_ITEM);
            content.add(CAPYBARA_ITEM);
            content.add(CREATURE_ITEM);

            for (Item item : PRIDE_ITEMS) {
                content.add(item);
            }
        });
    }
}
