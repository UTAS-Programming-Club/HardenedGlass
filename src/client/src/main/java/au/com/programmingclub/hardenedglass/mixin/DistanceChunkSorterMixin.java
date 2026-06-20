package au.com.programmingclub.hardenedglass.mixin;

import net.minecraft.client.render.world.DistanceChunkSorter;
import net.minecraft.client.render.world.RenderChunk;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DistanceChunkSorter.class)
public class DistanceChunkSorterMixin {
    @Shadow
    private Entity camera;

    @Inject(at = @At("HEAD"), method = "compare", remap = false, cancellable = true)
    private void fun(Object object2, Object par2, CallbackInfoReturnable<Integer> cir) {
        RenderChunk chunk1 = (RenderChunk)object2;
        RenderChunk chunk2 = (RenderChunk)par2;

        float distance1 = chunk1.compare(this.camera);
        float distance2 = chunk2.compare(this.camera);
        cir.setReturnValue(Float.compare(distance1, distance2));
    }
}
