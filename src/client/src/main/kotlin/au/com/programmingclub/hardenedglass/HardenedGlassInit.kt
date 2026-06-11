package au.com.programmingclub.hardenedglass

import net.minecraft.client.Minecraft

object HardenedGlassInit {
    fun init(minecraft: Minecraft) {
        Helpers.init(minecraft)
        HardenedGlass.onReady()
    }

    fun tick(minecraft: Minecraft) {
        Helpers.OnTick(0.0f, minecraft)
    }
}
