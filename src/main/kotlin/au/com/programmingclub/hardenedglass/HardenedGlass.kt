package au.com.programmingclub.hardenedglass

import au.com.programmingclub.hardenedglass.entity.HardenedGlassEntities
import net.fabricmc.api.ModInitializer

const val namespace = "hardenedglass"

object HardenedGlass : ModInitializer {
    override fun onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        registerBaconItems()
        registerHardenedGlassBlock()
        HardenedGlassEntities.registerEntities()
    }
}
