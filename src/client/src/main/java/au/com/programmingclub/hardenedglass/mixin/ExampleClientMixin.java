package au.com.programmingclub.hardenedglass.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class ExampleClientMixin {
  @Inject(at = @At("HEAD"), method = "main", remap = false)
  private static void init(CallbackInfo info) {
      // This code is injected into the start of Minecraft.main()V
  }
}
