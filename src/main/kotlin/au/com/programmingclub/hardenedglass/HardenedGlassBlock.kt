package au.com.programmingclub.hardenedglass

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.level.block.Blocks.GLASS
import net.minecraft.world.level.block.TransparentBlock
import net.minecraft.world.level.block.state.BlockBehaviour

// TODO: Refactor, too annoying to do in TUI.
data object HardenedGlassBlock {
    fun init() {
        // TODO: Move to separate group, find better method
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.COLORED_BLOCKS).register { content ->
            content.accept(HARDENED_GLASS)
        }
    }

    val HARDENED_GLASS: TransparentBlock = HardenedGlass.register(
        ::TransparentBlock,
        BlockBehaviour.Properties.ofFullCopy(GLASS)
                .destroyTime(10f)
                .explosionResistance(9f),
        "hardened_glass",
    )
}
