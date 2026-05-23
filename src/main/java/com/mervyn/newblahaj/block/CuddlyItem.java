package com.mervyn.newblahaj.block;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CuddlyItem extends BlockItem {

    private final Component subtitle;

    public CuddlyItem(Block block, Properties settings, @Nullable String subtitleLangKey) {
        super(block, settings);
        this.subtitle = subtitleLangKey == null ? null : Component.translatable(subtitleLangKey).withStyle(ChatFormatting.GRAY);
    }

    @Override
    public void onCraftedBy(ItemStack stack, Level level, net.minecraft.world.entity.player.Player player) {
        super.onCraftedBy(stack, level, player);
        stack.getOrCreateTag().putString("Owner", player.getName().getString());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag context) {
        super.appendHoverText(stack, level, tooltip, context);
        if (subtitle != null) {
            tooltip.add(subtitle);
        }
        if (stack.hasTag() && stack.getTag().contains("Owner")) {
            String ownerName = stack.getTag().getString("Owner");
            Component ownerComp = Component.literal(ownerName);
            if (stack.hasCustomHoverName()) {
                tooltip.add(Component.translatable("tooltip.newblahaj.owner.rename", stack.getHoverName(), ownerComp).withStyle(ChatFormatting.GRAY));
            } else {
                tooltip.add(Component.translatable("tooltip.newblahaj.owner.craft", ownerComp).withStyle(ChatFormatting.GRAY));
            }
        }
    }

    @Override
    public float getDestroySpeed(ItemStack stack, BlockState state) {
        return super.getDestroySpeed(stack, state) * 0.25f;
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getDefaultAttributeModifiers(slot));
            builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Weapon modifier", -2.0, AttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getDefaultAttributeModifiers(slot);
    }
}
