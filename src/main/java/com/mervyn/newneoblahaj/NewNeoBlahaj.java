package com.mervyn.newneoblahaj;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.mervyn.newneoblahaj.bed.BedPlushEvents;
import com.mervyn.newneoblahaj.bed.ModAttachments;
import com.mervyn.newneoblahaj.block.ModBlocks;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.LootTableLoadEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(NewNeoBlahaj.MODID)
public class NewNeoBlahaj {
    public static final String MODID = "newneoblahaj";
    public static final Logger LOGGER = LogUtils.getLogger();

    public NewNeoBlahaj(IEventBus modEventBus) {
        ModDataComponents.register(modEventBus);
        ModSounds.register(modEventBus);
        ModAttachments.register(modEventBus);
        ModBlocks.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(BedPlushEvents.class);
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MODID, path);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("New Neo Blåhaj common setup");
    }

    /**
     * Appends extra loot pools to selected vanilla tables at load time. Pools are additive;
     * relative ordering vs other mods follows {@link LootTableLoadEvent} dispatch order.
     * Listener priority is {@link EventPriority#LOW} so other mods can adjust tables first.
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    public void onLootTableLoad(LootTableLoadEvent event) {
        ResourceKey<LootTable> key = event.getKey();
        if (key == null) {
            return;
        }
        LootTable table = event.getTable();
        if (key == BuiltInLootTables.STRONGHOLD_CROSSING || key == BuiltInLootTables.STRONGHOLD_CORRIDOR) {
            LootPool.Builder pb = LootPool.lootPool()
                .add(LootItem.lootTableItem(ModBlocks.GRAY_SHARK_ITEM.get()).setWeight(5))
                .add(LootItem.lootTableItem(Items.AIR).setWeight(100));
            table.addPool(pb.build());
        } else if (key == BuiltInLootTables.VILLAGE_PLAINS_HOUSE) {
            LootPool.Builder pb = LootPool.lootPool()
                .add(LootItem.lootTableItem(ModBlocks.GRAY_SHARK_ITEM.get()))
                .add(LootItem.lootTableItem(Items.AIR).setWeight(43));
            table.addPool(pb.build());
        } else if (key == BuiltInLootTables.VILLAGE_TAIGA_HOUSE || key == BuiltInLootTables.VILLAGE_SNOWY_HOUSE) {
            LootPool.Builder pb = LootPool.lootPool()
                .add(LootItem.lootTableItem(ModBlocks.GRAY_SHARK_ITEM.get()).setWeight(5))
                .add(LootItem.lootTableItem(Items.AIR).setWeight(54));
            table.addPool(pb.build());
        } else if (key == BuiltInLootTables.FLETCHER_GIFT
            || key == BuiltInLootTables.BUTCHER_GIFT
            || key == BuiltInLootTables.LEATHERWORKER_GIFT) {
            LootPool.Builder pb = LootPool.lootPool()
                .add(LootItem.lootTableItem(ModBlocks.BROWN_BEAR_ITEM.get()).setWeight(5))
                .add(LootItem.lootTableItem(Items.AIR).setWeight(25));
            table.addPool(pb.build());
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("New Neo Blåhaj server starting");
    }
}
