package au.com.programmingclub.hardenedglass.mixin;

import au.com.programmingclub.hardenedglass.HardenedGlassInit;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    @Inject(at = @At("HEAD"), method = "run")
    private void run(CallbackInfo info) {
        HardenedGlassInit.INSTANCE.init((Minecraft)(Object)this);
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo info) {
        HardenedGlassInit.INSTANCE.tick((Minecraft)(Object)this);
    }
}
