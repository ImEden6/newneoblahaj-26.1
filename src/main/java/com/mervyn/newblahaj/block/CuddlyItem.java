package com.mervyn.newblahaj.block;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CuddlyItem extends BlockItem {

    private final Text subtitle;

    public CuddlyItem(Block block, Settings settings, @Nullable String subtitleLangKey) {
        super(block, settings);
        this.subtitle = subtitleLangKey == null ? null : Text.translatable(subtitleLangKey).formatted(Formatting.GRAY);
    }

    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        super.onCraft(stack, world, player);
        stack.getOrCreateNbt().putString("Owner", player.getName().getString());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        if (subtitle != null) {
            tooltip.add(subtitle);
        }
        if (stack.hasNbt() && stack.getNbt().contains("Owner")) {
            String ownerName = stack.getNbt().getString("Owner");
            Text ownerComp = Text.literal(ownerName);
            if (stack.hasCustomName()) {
                tooltip.add(Text.translatable("tooltip.newblahaj.owner.rename", stack.getName(), ownerComp).formatted(Formatting.GRAY));
            } else {
                tooltip.add(Text.translatable("tooltip.newblahaj.owner.craft", ownerComp).formatted(Formatting.GRAY));
            }
        }
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return super.getMiningSpeedMultiplier(stack, state) * 0.25f;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
            builder.putAll(super.getAttributeModifiers(slot));
            builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", -2.0, EntityAttributeModifier.Operation.ADDITION));
            return builder.build();
        }
        return super.getAttributeModifiers(slot);
    }
}
