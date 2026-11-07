package au.com.programmingclub.hardenedglass

import au.com.programmingclub.hardenedglass.entity.HardenedGlassEntities
import au.com.programmingclub.hardenedglass.entity.HardenedGlassModelLayers
import au.com.programmingclub.hardenedglass.entity.LongPigEntityRenderer
import au.com.programmingclub.hardenedglass.entity.TallPigEntityRenderer
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.renderer.entity.EntityRenderers

object HardenedGlassClient : ClientModInitializer {
    override fun onInitializeClient() {
        // TODO: Setup connected textures without athena
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        EntityRenderers.register(HardenedGlassEntities.LONG_PIG, ::LongPigEntityRenderer)
        EntityRenderers.register(HardenedGlassEntities.TALL_PIG, ::TallPigEntityRenderer)

        HardenedGlassModelLayers.registerEntityModels()
    }
}
