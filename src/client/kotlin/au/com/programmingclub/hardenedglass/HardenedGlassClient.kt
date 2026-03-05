package au.com.programmingclub.hardenedglass

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.client.render.RenderLayer

object HardenedGlassClient : ClientModInitializer {
  override fun onInitializeClient() {
    // This entrypoint is suitable for setting up client-specific logic, such as rendering.
    BlockRenderLayerMap.INSTANCE.putBlock(HardenedGlassBlock, RenderLayer.getCutout())
  }
}
