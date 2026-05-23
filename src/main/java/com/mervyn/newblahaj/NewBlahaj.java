package com.mervyn.newblahaj;

import com.mervyn.newblahaj.bed.BedPlushEvents;
import com.mervyn.newblahaj.block.ModBlocks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewBlahaj implements ModInitializer {
    public static final String MODID = "newblahaj";
    public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

    @Override
    public void onInitialize() {
        LOGGER.info("New Blåhaj common setup");

        ModSounds.register();
        ModBlocks.register();

        UseBlockCallback.EVENT.register(BedPlushEvents::onRightClickBlock);

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (source.isBuiltin()) {
                if (BuiltInLootTables.STRONGHOLD_CROSSING.equals(id) || BuiltInLootTables.STRONGHOLD_CORRIDOR.equals(id)) {
                    LootPool.Builder pb = LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModBlocks.GRAY_SHARK_ITEM).setWeight(5))
                        .add(LootItem.lootTableItem(Items.AIR).setWeight(100));
                    tableBuilder.pool(pb.build());
                } else if (BuiltInLootTables.VILLAGE_PLAINS_HOUSE.equals(id)) {
                    LootPool.Builder pb = LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModBlocks.GRAY_SHARK_ITEM))
                        .add(LootItem.lootTableItem(Items.AIR).setWeight(43));
                    tableBuilder.pool(pb.build());
                } else if (BuiltInLootTables.VILLAGE_TAIGA_HOUSE.equals(id) || BuiltInLootTables.VILLAGE_SNOWY_HOUSE.equals(id)) {
                    LootPool.Builder pb = LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModBlocks.GRAY_SHARK_ITEM).setWeight(5))
                        .add(LootItem.lootTableItem(Items.AIR).setWeight(54));
                    tableBuilder.pool(pb.build());
                } else if (BuiltInLootTables.FLETCHER_GIFT.equals(id)
                    || BuiltInLootTables.BUTCHER_GIFT.equals(id)
                    || BuiltInLootTables.LEATHERWORKER_GIFT.equals(id)) {
                    LootPool.Builder pb = LootPool.lootPool()
                        .add(LootItem.lootTableItem(ModBlocks.BROWN_BEAR_ITEM).setWeight(5))
                        .add(LootItem.lootTableItem(Items.AIR).setWeight(25));
                    tableBuilder.pool(pb.build());
                }
            }
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.SHEPHERD, 5, factories -> {
            factories.add((entity, random) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 15),
                new ItemStack(ModBlocks.GRAY_SHARK_ITEM),
                2, // maxUses
                30, // merchantXp
                0.05f // priceMultiplier
            ));
        });
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MODID, path);
    }
}
