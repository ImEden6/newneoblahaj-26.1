package com.mervyn.newblahaj.block;

import com.mervyn.newblahaj.NewBlahaj;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.ArrayList;
import java.util.List;

public final class ModBlocks {
    public static final Block GRAY_SHARK_BLOCK = registerBlock("gray_shark", new CuddlyBlock(BlockBehaviour.Properties.copy(Blocks.LIGHT_GRAY_WOOL).noOcclusion()));
    public static final Item GRAY_SHARK_ITEM = registerItem("gray_shark", new CuddlyItem(GRAY_SHARK_BLOCK, new Item.Properties().stacksTo(1), "block.newblahaj.gray_shark.tooltip"));

    public static final Block BLAHAJ_BLOCK = registerBlock("blue_shark", new CuddlyBlock(BlockBehaviour.Properties.copy(Blocks.CYAN_WOOL).noOcclusion()));
    public static final Item BLAHAJ_ITEM = registerItem("blue_shark", new CuddlyItem(BLAHAJ_BLOCK, new Item.Properties().stacksTo(1), "block.newblahaj.blue_shark.tooltip"));

    public static final Block BLAVINGAD_BLOCK = registerBlock("blue_whale", new CuddlyBlock(BlockBehaviour.Properties.copy(Blocks.BLUE_WOOL).noOcclusion()));
    public static final Item BLAVINGAD_ITEM = registerItem("blue_whale", new CuddlyItem(BLAVINGAD_BLOCK, new Item.Properties().stacksTo(1), "block.newblahaj.blue_whale.tooltip"));

    public static final Block BREAD_BLOCK = registerBlock("bread", new CuddlyBlock(BlockBehaviour.Properties.copy(Blocks.ORANGE_WOOL).noOcclusion()));
    public static final Item BREAD_ITEM = registerItem("bread", new CuddlyItem(BREAD_BLOCK, new Item.Properties().stacksTo(1), null));

    public static final Block BROWN_BEAR_BLOCK = registerBlock("brown_bear", new CuddlyBlock(BlockBehaviour.Properties.copy(Blocks.BROWN_WOOL).noOcclusion()));
    public static final Item BROWN_BEAR_ITEM = registerItem("brown_bear", new CuddlyItem(BROWN_BEAR_BLOCK, new Item.Properties().stacksTo(1), "block.newblahaj.brown_bear.tooltip"));

    public static final Block CAPYBARA_BLOCK = registerBlock("capybara", new CuddlyBlock(BlockBehaviour.Properties.copy(Blocks.BROWN_WOOL).noOcclusion()));
    public static final Item CAPYBARA_ITEM = registerItem("capybara", new CuddlyItem(CAPYBARA_BLOCK, new Item.Properties().stacksTo(1), "block.newblahaj.capybara.tooltip"));

    public static final List<String> PRIDE_NAMES = List.of(
        "ace", "agender", "aro", "aroace", "bi", "demiboy", "demigirl",
        "demi_r", "demi_s", "enby", "gay", "genderfluid", "genderqueer", "greyrose",
        "grey_r", "grey_s", "intersex", "lesbian", "pan", "poly", "pride", "trans");

    public static final List<Block> PRIDE_BLOCKS = new ArrayList<>();
    public static final List<Item> PRIDE_ITEMS = new ArrayList<>();

    static {
        for (String name : PRIDE_NAMES) {
            Block block = registerBlock(name + "_shark", new CuddlyBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).noOcclusion()));
            Item item = registerItem(name + "_shark", new CuddlyItem(block, new Item.Properties().stacksTo(1), "block.newblahaj.blue_shark.tooltip"));
            PRIDE_BLOCKS.add(block);
            PRIDE_ITEMS.add(item);
        }
    }

    private ModBlocks() {}

    private static Block registerBlock(String name, Block block) {
        return Registry.register(BuiltInRegistries.BLOCK, NewBlahaj.id(name), block);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(BuiltInRegistries.ITEM, NewBlahaj.id(name), item);
    }

    public static void register() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(content -> {
            content.accept(GRAY_SHARK_ITEM);
            content.accept(BLAHAJ_ITEM);
            content.accept(BLAVINGAD_ITEM);
            content.accept(BREAD_ITEM);
            content.accept(BROWN_BEAR_ITEM);
            content.accept(CAPYBARA_ITEM);
            for (Item item : PRIDE_ITEMS) {
                content.accept(item);
            }
        });
    }
}
