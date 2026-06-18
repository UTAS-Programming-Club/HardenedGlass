package au.com.programmingclub.hardenedglass

import au.com.programmingclub.hardenedglass.entity.HardenedGlassEntities
import au.com.programmingclub.hardenedglass.entity.HardenedGlassModelLayers
import au.com.programmingclub.hardenedglass.entity.LongPigEntityModel
import au.com.programmingclub.hardenedglass.entity.LongPigEntityRenderer
import au.com.programmingclub.hardenedglass.entity.TallPigEntityModel
import au.com.programmingclub.hardenedglass.entity.TallPigEntityRenderer
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.minecraft.client.render.RenderLayer

@Environment(EnvType.CLIENT)
object HardenedGlassClient : ClientModInitializer {
    override fun onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        BlockRenderLayerMap.INSTANCE.putBlock(HardenedGlassBlock, RenderLayer.getCutout())

        EntityRendererRegistry.register(HardenedGlassEntities.LONG_PIG, ::LongPigEntityRenderer)
        EntityRendererRegistry.register(HardenedGlassEntities.TALL_PIG, ::TallPigEntityRenderer)

        EntityModelLayerRegistry.registerModelLayer(
            HardenedGlassModelLayers.LONG_PIG,
            LongPigEntityModel::getTexturedModelData
        )
        EntityModelLayerRegistry.registerModelLayer(
            HardenedGlassModelLayers.TALL_PIG,
            TallPigEntityModel::getTexturedModelData
        )
    }
}
