package au.com.programmingclub.hardenedglass

import net.minecraft.client.Minecraft
import net.ornithemc.osl.entrypoints.api.ModInitializer
import net.ornithemc.osl.lifecycle.api.MinecraftEvents

class HardenedGlassInit : ModInitializer {
    override fun init() {
        MinecraftEvents.READY.register {
            Helpers.init(it)
            HardenedGlass.onReady()
        }

        MinecraftEvents.TICK_START.register {
            Helpers.OnTick(0.0f, it)
        }
    }
}
