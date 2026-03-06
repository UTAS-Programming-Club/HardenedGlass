package au.com.programmingclub.hardenedglass

import au.com.programmingclub.hardenedglass.entity.HardenedGlassEntities
import net.fabricmc.api.ModInitializer
import net.minecraft.util.Identifier

const val MOD_ID = "hardenedglass"

data object HardenedGlass : ModInitializer {
    override fun onInitialize() {
        BaconItems.init()
        HardenedGlassBlock.init()
        HardenedGlassEntities.init()
    }

    fun id(path: String): Identifier
        = Identifier(MOD_ID, path)
}
