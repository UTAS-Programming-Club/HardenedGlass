package au.com.programmingclub.hardenedglass.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.server.MinecraftServer;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
	@Inject(at = @At("HEAD"), method = "main", remap = false)
	private static void init(CallbackInfo ci) {
		// This code is injected into the start of MinecraftServer.main()V
	}
}
