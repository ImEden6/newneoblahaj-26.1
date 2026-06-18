package com.mervyn.newneoblahaj.bed;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public record BedPlushData(Map<BlockPos, ItemStack> plushes) {
    public static final Codec<BedPlushData> CODEC = Codec.unboundedMap(Codec.STRING, ItemStack.CODEC).xmap(
        stringMap -> {
            Map<BlockPos, ItemStack> map = new ConcurrentHashMap<>();
            stringMap.forEach((k, v) -> {
                try {
                    String[] parts = k.split(",");
                    if (parts.length == 3) {
                        map.put(new BlockPos(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), Integer.parseInt(parts[2])), v);
                    }
                } catch (Exception e) {
                    com.mojang.logging.LogUtils.getLogger().warn("Skipping malformed bed plush key '{}': {}", k, e.getMessage());
                }
            });
            return new BedPlushData(map);
        },
        data -> {
            Map<String, ItemStack> map = new HashMap<>();
            data.plushes().forEach((k, v) -> {
                if (!v.isEmpty()) {
                    map.put(k.getX() + "," + k.getY() + "," + k.getZ(), v);
                }
            });
            return map;
        }
    );

    public static final StreamCodec<RegistryFriendlyByteBuf, BedPlushData> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.map(ConcurrentHashMap::new, BlockPos.STREAM_CODEC, ItemStack.OPTIONAL_STREAM_CODEC),
        BedPlushData::plushes,
        BedPlushData::new
    );
}
