package com.mervyn.newblahaj.mixin;

import com.mervyn.newblahaj.bed.BedPlushHolder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BedBlockEntity.class)
public abstract class BedBlockEntityMixin implements BedPlushHolder {

    @Shadow
    protected abstract void saveAdditional(CompoundTag tag);

    @Unique
    private ItemStack newblahaj$plushItem = ItemStack.EMPTY;

    @Override
    public ItemStack newblahaj$getPlushItem() {
        return this.newblahaj$plushItem.isEmpty() ? ItemStack.EMPTY : this.newblahaj$plushItem.copy();
    }

    @Override
    public void newblahaj$setPlushItem(ItemStack stack) {
        this.newblahaj$plushItem = stack == null || stack.isEmpty() ? ItemStack.EMPTY : stack.copy();
        ((BlockEntity) (Object) this).setChanged();
    }

    @Inject(method = "load", at = @At("TAIL"))
    private void newblahaj$load(CompoundTag tag, CallbackInfo ci) {
        if (tag.contains("PlushItem", 10)) {
            this.newblahaj$plushItem = ItemStack.of(tag.getCompound("PlushItem"));
        } else {
            this.newblahaj$plushItem = ItemStack.EMPTY;
        }
    }

    @Inject(method = "saveAdditional", at = @At("TAIL"))
    private void newblahaj$save(CompoundTag tag, CallbackInfo ci) {
        if (!this.newblahaj$plushItem.isEmpty()) {
            CompoundTag itemTag = new CompoundTag();
            this.newblahaj$plushItem.save(itemTag);
            tag.put("PlushItem", itemTag);
        }
    }

    @Inject(method = "getUpdatePacket", at = @At("HEAD"), cancellable = true)
    private void newblahaj$getUpdatePacket(CallbackInfoReturnable<ClientboundBlockEntityDataPacket> cir) {
        if (!this.newblahaj$plushItem.isEmpty()) {
            cir.setReturnValue(ClientboundBlockEntityDataPacket.create((BlockEntity) (Object) this));
        }
    }

    @Inject(method = "getUpdateTag", at = @At("HEAD"), cancellable = true)
    private void newblahaj$getUpdateTag(CallbackInfoReturnable<CompoundTag> cir) {
        if (!this.newblahaj$plushItem.isEmpty()) {
            CompoundTag tag = new CompoundTag();
            this.saveAdditional(tag);
            cir.setReturnValue(tag);
        }
    }
}
