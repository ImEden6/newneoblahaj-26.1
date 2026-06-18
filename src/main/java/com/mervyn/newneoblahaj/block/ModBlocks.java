package com.mervyn.newneoblahaj.block;

import java.util.ArrayList;
import java.util.List;

import com.mervyn.newneoblahaj.NewNeoBlahaj;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModBlocks {
    private static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(NewNeoBlahaj.MODID);
    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NewNeoBlahaj.MODID);

    public static final DeferredBlock<CuddlyBlock> GRAY_SHARK_BLOCK = BLOCKS.register("gray_shark", () -> new CuddlyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WOOL.lightGray()).noOcclusion()
            .setId(ResourceKey.create(Registries.BLOCK, NewNeoBlahaj.id("gray_shark")))));
    public static final DeferredHolder<Item, CuddlyItem> GRAY_SHARK_ITEM = ITEMS.register("gray_shark", () -> new CuddlyItem(GRAY_SHARK_BLOCK.get(), new Item.Properties().useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, NewNeoBlahaj.id("gray_shark"))).stacksTo(1).attributes(CuddlyItem.createAttributeModifiers()), "block.newneoblahaj.gray_shark.tooltip"));

    public static final DeferredBlock<CuddlyBlock> BLAHAJ_BLOCK = BLOCKS.register("blue_shark", () -> new CuddlyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WOOL.cyan()).noOcclusion()
            .setId(ResourceKey.create(Registries.BLOCK, NewNeoBlahaj.id("blue_shark")))));
    public static final DeferredHolder<Item, CuddlyItem> BLAHAJ_ITEM = ITEMS.register("blue_shark", () -> new CuddlyItem(BLAHAJ_BLOCK.get(), new Item.Properties().useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, NewNeoBlahaj.id("blue_shark"))).stacksTo(1).attributes(CuddlyItem.createAttributeModifiers()), "block.newneoblahaj.blue_shark.tooltip"));

    public static final DeferredBlock<CuddlyBlock> BLAVINGAD_BLOCK = BLOCKS.register("blue_whale", () -> new CuddlyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WOOL.blue()).noOcclusion()
            .setId(ResourceKey.create(Registries.BLOCK, NewNeoBlahaj.id("blue_whale")))));
    public static final DeferredHolder<Item, CuddlyItem> BLAVINGAD_ITEM = ITEMS.register("blue_whale", () -> new CuddlyItem(BLAVINGAD_BLOCK.get(), new Item.Properties().useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, NewNeoBlahaj.id("blue_whale"))).stacksTo(1).attributes(CuddlyItem.createAttributeModifiers()), "block.newneoblahaj.blue_whale.tooltip"));

    public static final DeferredBlock<CuddlyBlock> BREAD_BLOCK = BLOCKS.register("bread", () -> new CuddlyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WOOL.orange()).noOcclusion()
            .setId(ResourceKey.create(Registries.BLOCK, NewNeoBlahaj.id("bread")))));
    public static final DeferredHolder<Item, CuddlyItem> BREAD_ITEM = ITEMS.register("bread", () -> new CuddlyItem(BREAD_BLOCK.get(), new Item.Properties().useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, NewNeoBlahaj.id("bread"))).stacksTo(1).attributes(CuddlyItem.createAttributeModifiers()), null));

    public static final DeferredBlock<CuddlyBlock> BROWN_BEAR_BLOCK = BLOCKS.register("brown_bear", () -> new CuddlyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WOOL.brown()).noOcclusion()
            .setId(ResourceKey.create(Registries.BLOCK, NewNeoBlahaj.id("brown_bear")))));
    public static final DeferredHolder<Item, CuddlyItem> BROWN_BEAR_ITEM = ITEMS.register("brown_bear", () -> new CuddlyItem(BROWN_BEAR_BLOCK.get(), new Item.Properties().useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, NewNeoBlahaj.id("brown_bear"))).stacksTo(1).attributes(CuddlyItem.createAttributeModifiers()), "block.newneoblahaj.brown_bear.tooltip"));

    public static final DeferredBlock<CuddlyBlock> CAPYBARA_BLOCK = BLOCKS.register("capybara", () -> new CuddlyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WOOL.brown()).noOcclusion()
            .setId(ResourceKey.create(Registries.BLOCK, NewNeoBlahaj.id("capybara")))));
    public static final DeferredHolder<Item, CuddlyItem> CAPYBARA_ITEM = ITEMS.register("capybara", () -> new CuddlyItem(CAPYBARA_BLOCK.get(), new Item.Properties().useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, NewNeoBlahaj.id("capybara"))).stacksTo(1).attributes(CuddlyItem.createAttributeModifiers()), "block.newneoblahaj.capybara.tooltip"));

    public static final List<String> PRIDE_NAMES = List.of(
        "ace", "agender", "aro", "aroace", "bi", "demiboy", "demigirl",
        "demi_r", "demi_s", "enby", "gay", "genderfluid", "genderqueer", "greyrose",
        "grey_r", "grey_s", "intersex", "lesbian", "pan", "poly", "pride", "trans");

    public static final List<DeferredBlock<CuddlyBlock>> PRIDE_BLOCKS = new ArrayList<>();
    public static final List<DeferredHolder<Item, CuddlyItem>> PRIDE_ITEMS = new ArrayList<>();

    static {
        for (String name : PRIDE_NAMES) {
            DeferredBlock<CuddlyBlock> block = BLOCKS.register(name + "_shark", () -> new CuddlyBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WOOL.white()).noOcclusion()
                    .setId(ResourceKey.create(Registries.BLOCK, NewNeoBlahaj.id(name + "_shark")))));
            DeferredHolder<Item, CuddlyItem> item = ITEMS.register(name + "_shark", () -> new CuddlyItem(block.get(), new Item.Properties().useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, NewNeoBlahaj.id(name + "_shark"))).stacksTo(1).attributes(CuddlyItem.createAttributeModifiers()), "block.newneoblahaj.blue_shark.tooltip"));
            PRIDE_BLOCKS.add(block);
            PRIDE_ITEMS.add(item);
        }
    }

    private ModBlocks() {}

    public static void register(IEventBus eventBus) {
        eventBus.addListener(BuildCreativeModeTabContentsEvent.class, ModBlocks::onCreativeTab);
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }

    private static void onCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() != CreativeModeTabs.TOOLS_AND_UTILITIES) {
            return;
        }
        event.accept(new ItemStack(GRAY_SHARK_ITEM));
        event.accept(new ItemStack(BLAHAJ_ITEM));
        event.accept(new ItemStack(BLAVINGAD_ITEM));
        event.accept(new ItemStack(BREAD_ITEM));
        event.accept(new ItemStack(BROWN_BEAR_ITEM));
        event.accept(new ItemStack(CAPYBARA_ITEM));
        for (DeferredHolder<Item, CuddlyItem> item : PRIDE_ITEMS) {
            event.accept(new ItemStack(item));
        }
    }
}
