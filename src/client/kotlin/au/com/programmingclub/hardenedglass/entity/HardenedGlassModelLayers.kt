package au.com.programmingclub.hardenedglass.entity

import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.minecraft.client.render.entity.model.EntityModelLayer

object HardenedGlassModelLayers {
    val LONG_PIG = EntityModelLayer(HardenedGlassEntities.LONG_PIG_ID, "main")
    val TALL_PIG = EntityModelLayer(HardenedGlassEntities.TALL_PIG_ID, "main")
    val TALL_PIG_SADDLE = EntityModelLayer(HardenedGlassEntities.TALL_PIG_ID, "saddle")

    fun registerEntityModels() {
        EntityRendererRegistry.register(HardenedGlassEntities.LONG_PIG, ::LongPigEntityRenderer)
        EntityRendererRegistry.register(HardenedGlassEntities.TALL_PIG, ::TallPigEntityRenderer)

        EntityModelLayerRegistry.registerModelLayer(LONG_PIG, LongPigEntityModel::getTexturedModelData)
        EntityModelLayerRegistry.registerModelLayer(TALL_PIG, TallPigEntityModel::getPigTexturedModelData)
        EntityModelLayerRegistry.registerModelLayer(TALL_PIG_SADDLE, TallPigEntityModel::getSaddleTexturedModelData)
    }
}
