package au.com.programmingclub.hardenedglass

import au.com.programmingclub.hardenedglass.entity.HardenedGlassEntities
import net.fabricmc.api.ModInitializer

const val MOD_ID = "hardenedglass"

data object HardenedGlass : ModInitializer {
    override fun onInitialize() {
        BaconItems.init()
        HardenedGlassBlock.init()
        HardenedGlassEntities.init()
    }
}
