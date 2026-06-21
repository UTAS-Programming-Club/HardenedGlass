package au.com.programmingclub.hardenedglass.mixin;

import au.com.programmingclub.hardenedglass.Helpers;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class BlockMixin {
    @Shadow
    @Final
    public int id;

    @SuppressWarnings("NameDoesntMatchTargetClass")
    @Inject(at = @At("HEAD"), method = "takeFireDamage(Lnet/minecraft/world/World;FFF)Z", cancellable = true)
    void takeFireDamage(World world, float x, float y, float z, CallbackInfoReturnable<Boolean> cir) {
        if (Helpers.smeltFlammable(world, x, y, z, id)) {
            cir.setReturnValue(true);
        }
    }
}
