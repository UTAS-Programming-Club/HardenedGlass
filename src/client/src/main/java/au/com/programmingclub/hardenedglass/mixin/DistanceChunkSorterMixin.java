package au.com.programmingclub.hardenedglass.mixin;

import net.minecraft.client.render.world.DistanceChunkSorter;
import net.minecraft.client.render.world.RenderChunk;
import net.minecraft.entity.mob.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DistanceChunkSorter.class)
public class DistanceChunkSorterMixin {
    @Shadow
    private PlayerEntity camera;

    @SuppressWarnings("NameDoesntMatchTargetClass")
    @Inject(at = @At("HEAD"), method = "compare(Ljava/lang/Object;Ljava/lang/Object;)I", remap = false, cancellable = true)
    private void compare(Object object, Object object2, CallbackInfoReturnable<Integer> cir) {
        @NotNull RenderChunk chunk1 = (RenderChunk)object;
        @NotNull RenderChunk chunk2 = (RenderChunk)object2;

        float distance1 = chunk1.compare(this.camera);
        float distance2 = chunk2.compare(this.camera);

        cir.setReturnValue(Float.compare(distance1, distance2));
    }
}
