package au.com.programmingclub.hardenedglass.mixin;

import net.minecraft.client.render.world.DirtyChunkSorter;
import net.minecraft.client.render.world.RenderChunk;
import net.minecraft.entity.mob.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DirtyChunkSorter.class)
public class DirtyChunkSorterMixin {
    @Shadow
    private PlayerEntity camera;

    @Inject(at = @At("HEAD"), method = "compare", remap = false, cancellable = true)
    private void fun(Object object2, Object par2, CallbackInfoReturnable<Integer> cir) {
        RenderChunk chunk1 = (RenderChunk)object2;
        RenderChunk chunk2 = (RenderChunk)par2;

        boolean isVisible1 = chunk1.visible;
        boolean isVisible2 = chunk2.visible;

        if (isVisible1 && !isVisible2) {
            cir.setReturnValue(1);
        } else if (!isVisible1 && isVisible2) {
            cir.setReturnValue(-1);
        }

        float distance1 = chunk1.compare(this.camera);
        float distance2 = chunk2.compare(this.camera);

        cir.setReturnValue(Float.compare(distance2, distance1));
    }
}
