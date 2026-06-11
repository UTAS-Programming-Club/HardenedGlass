package au.com.programmingclub.hardenedglass.mixin;

import au.com.programmingclub.hardenedglass.HardenedGlassInit;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class ExampleClientMixin {
    @Inject(at = @At("HEAD"), method = "init", remap = false)
    private void init(CallbackInfo info) {
        HardenedGlassInit.INSTANCE.init((Minecraft)(Object)this);
    }

    @Inject(at = @At("HEAD"), method = "tick", remap = false)
    private void tick(CallbackInfo info) {
        HardenedGlassInit.INSTANCE.tick((Minecraft)(Object)this);
    }
}
