package com.mervyn.newneoblahaj.block;

import java.util.function.Consumer;

import com.mervyn.newneoblahaj.ModDataComponents;
import com.mervyn.newneoblahaj.NewNeoBlahaj;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.TooltipDisplay;
import org.jetbrains.annotations.Nullable;

public class CuddlyItem extends BlockItem {

    private static final Identifier MINING_SPEED_MODIFIER_ID = NewNeoBlahaj.id("cuddly_mining_speed");
    private static final Identifier ATTACK_DAMAGE_MODIFIER_ID = NewNeoBlahaj.id("cuddly_attack_damage");

    private final Component subtitle;

    public CuddlyItem(net.minecraft.world.level.block.Block block, Properties settings, @Nullable String subtitleLangKey) {
        super(block, settings);
        this.subtitle = subtitleLangKey == null ? null : Component.translatable(subtitleLangKey).withStyle(ChatFormatting.GRAY);
    }

    @Override
    public void onCraftedBy(ItemStack stack, Player player) {
        super.onCraftedBy(stack, player);
        stack.set(ModDataComponents.OWNER.get(), player.getName());
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag type) {
        super.appendHoverText(stack, context, display, tooltip, type);
        if (subtitle != null) {
            tooltip.accept(subtitle);
        }
        @Nullable Component ownerName = stack.get(ModDataComponents.OWNER.get());
        if (ownerName != null) {
            @Nullable Component customName = stack.get(DataComponents.CUSTOM_NAME);
            if (customName == null) {
                tooltip.accept(Component.translatable("tooltip.newneoblahaj.owner.craft", ownerName).withStyle(ChatFormatting.GRAY));
            } else {
                tooltip.accept(Component.translatable("tooltip.newneoblahaj.owner.rename", customName, ownerName).withStyle(ChatFormatting.GRAY));
            }
        }
    }

    public static ItemAttributeModifiers createAttributeModifiers() {
        return ItemAttributeModifiers.builder()
            .add(
                Attributes.BLOCK_BREAK_SPEED,
                new AttributeModifier(MINING_SPEED_MODIFIER_ID, -3.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
                EquipmentSlotGroup.MAINHAND
            )
            .add(
                Attributes.ATTACK_DAMAGE,
                new AttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, -2.0, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL),
                EquipmentSlotGroup.MAINHAND
            )
            .build();
    }
}
