package au.com.programmingclub.hardenedglass.mixin;

import au.com.programmingclub.hardenedglass.Helpers;
import net.minecraft.block.entity.FurnaceBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FurnaceBlockEntity.class)
public class FurnaceBlockEntityMixin {
    @Inject(at = @At("HEAD"), method = "getResult", remap = false, cancellable = true)
    private static void getResult(int input, CallbackInfoReturnable<Integer> cir) {
        Helpers.smeltables.forEach((@NotNull Integer smeltableID, @NotNull Integer resultID) -> {
            if (input == smeltableID) {
                cir.setReturnValue(resultID);
            }
        });
    }
}
