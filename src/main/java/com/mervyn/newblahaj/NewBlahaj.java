package com.mervyn.newblahaj;

import com.mervyn.newblahaj.block.ModBlocks;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.VillagerProfession;
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

        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (!source.isBuiltin()) {
                return;
            }
            if (matchesLoot(id, "chests/stronghold_crossing") || matchesLoot(id, "chests/stronghold_corridor")) {
                LootPool.Builder pb = LootPool.builder()
                    .with(ItemEntry.builder(ModBlocks.GRAY_SHARK_ITEM).weight(5))
                    .with(ItemEntry.builder(Items.AIR).weight(100));
                tableBuilder.pool(pb.build());
            } else if (matchesLoot(id, "chests/village/village_plains_house")) {
                LootPool.Builder pb = LootPool.builder()
                    .with(ItemEntry.builder(ModBlocks.GRAY_SHARK_ITEM))
                    .with(ItemEntry.builder(Items.AIR).weight(43));
                tableBuilder.pool(pb.build());
            } else if (matchesLoot(id, "chests/village/village_taiga_house") || matchesLoot(id, "chests/village/village_snowy_house")) {
                LootPool.Builder pb = LootPool.builder()
                    .with(ItemEntry.builder(ModBlocks.GRAY_SHARK_ITEM).weight(5))
                    .with(ItemEntry.builder(Items.AIR).weight(54));
                tableBuilder.pool(pb.build());
            } else if (matchesLoot(id, "chests/village/village_fletcher")
                || matchesLoot(id, "chests/village/village_butcher")
                || matchesLoot(id, "chests/village/village_leatherworker")) {
                LootPool.Builder pb = LootPool.builder()
                    .with(ItemEntry.builder(ModBlocks.BROWN_BEAR_ITEM).weight(5))
                    .with(ItemEntry.builder(Items.AIR).weight(25));
                tableBuilder.pool(pb.build());
            }
        });

        TradeOfferHelper.registerVillagerOffers(VillagerProfession.SHEPHERD, 5, factories -> {
            factories.add((entity, random) -> new TradeOffer(
                new ItemStack(Items.EMERALD, 15),
                new ItemStack(ModBlocks.GRAY_SHARK_ITEM),
                2,
                30,
                0.05f
            ));
        });
    }

    public static Identifier id(String path) {
        return new Identifier(MODID, path);
    }

    private static boolean matchesLoot(Identifier id, String path) {
        return id.equals(new Identifier("minecraft", path));
    }
}
