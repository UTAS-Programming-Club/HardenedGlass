package au.com.programmingclub.hardenedglass

import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.model.EntityModelLayer

@Environment(EnvType.CLIENT)
object HardenedGlassClient : ClientModInitializer {
  val MODEL_LONG_PIG_LAYER: EntityModelLayer = EntityModelLayer(LongPigEntityIdentifier, "main")
  val MODEL_TALL_PIG_LAYER: EntityModelLayer = EntityModelLayer(TallPigEntityIdentifier, "main")

  override fun onInitializeClient() {
    // This entrypoint is suitable for setting up client-specific logic, such as rendering.
    BlockRenderLayerMap.INSTANCE.putBlock(HardenedGlassBlock, RenderLayer.getCutout())

    EntityRendererRegistry.register(LongPigEntity, { context -> LongPigEntityRenderer(context) })
    EntityRendererRegistry.register(TallPigEntity, { context -> TallPigEntityRenderer(context) })

    EntityRendererRegistry.register(
      LongPigEntity,
      { context: EntityRendererFactory.Context? -> LongPigEntityRenderer(context!!) })

    EntityModelLayerRegistry.registerModelLayer(MODEL_LONG_PIG_LAYER, LongPigEntityModel::getTexturedModelData)

    EntityRendererRegistry.register(
      TallPigEntity,
      { context: EntityRendererFactory.Context? -> TallPigEntityRenderer(context!!) })

    EntityModelLayerRegistry.registerModelLayer(MODEL_TALL_PIG_LAYER, TallPigEntityModel::getTexturedModelData)

  }
}
