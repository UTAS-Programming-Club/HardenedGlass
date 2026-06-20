package au.com.programmingclub.hardenedglass.mixin;

import au.com.programmingclub.hardenedglass.Helpers;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.slot.InventorySlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(InventorySlot.class)
public abstract class InventorySlotMixin {
    @Shadow
    @Final
    public Inventory inventory;

    @Shadow
    public abstract void setItem(ItemStack item);

    @Inject(at = @At("RETURN"), method = "onItemRemoved()V")
    private void onItemRemoved(CallbackInfo ci) {
        setItem(null);
    }

    // TODO: Figure out why using Lnet/minecraft/block/entity/FurnaceBlockEntity;setItem(ILnet/minecraft/item/ItemStack;)V
    // causes a crash on start when applying mixins with Bad local variable type
    @Inject(at = @At("RETURN"), method = "setItem(Lnet/minecraft/item/ItemStack;)V")
    private void setItem(ItemStack item, CallbackInfo ci) {
        if (this.inventory.getClass() != FurnaceBlockEntity.class) {
            return;
        }

        @NotNull FurnaceBlockEntity entity = (FurnaceBlockEntity)this.inventory;
        @Nullable ItemStack smeltable = entity.inventory[0];
        @Nullable ItemStack fuel = entity.inventory[1];
        @Nullable ItemStack result = entity.inventory[2];
        if (smeltable == null || fuel == null || fuel.id != Item.COAL.id) {
            return;
        }

        for (Map.Entry<Integer, Integer> entry : Helpers.smeltables.entrySet()) {
            Integer smeltableID = entry.getKey();
            Integer resultID = entry.getValue();
            if (smeltable.id != smeltableID || (result != null && result.id != resultID)) {
                continue;
            }

            if (result == null) {
                result = new ItemStack(resultID, 0);
                entity.inventory[2] = result;
            }

            int maxSmeltableItems = Integer.min(smeltable.size, fuel.size);
            maxSmeltableItems = Integer.min(maxSmeltableItems, 64 - result.size);

            smeltable.size -= maxSmeltableItems;
            if (smeltable.size == 0) {
                entity.inventory[0] = null;
            }

            fuel.size -= maxSmeltableItems;
            if (fuel.size == 0) {
                entity.inventory[1] = null;
            }

            result.size += maxSmeltableItems;

            break;
        }
    }
}
