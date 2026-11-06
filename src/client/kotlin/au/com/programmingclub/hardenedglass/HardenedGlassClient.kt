package au.com.programmingclub.hardenedglass

import au.com.programmingclub.hardenedglass.entity.HardenedGlassModelLayers
import net.fabricmc.api.ClientModInitializer
import net.minecraft.client.renderer.rendertype.RenderType

object HardenedGlassClient : ClientModInitializer {
    override fun onInitializeClient() {
        // TODO: Setup connected textures without athena
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        HardenedGlassModelLayers.registerEntityModels()
    }
}
