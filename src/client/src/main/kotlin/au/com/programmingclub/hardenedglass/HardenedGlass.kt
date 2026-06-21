package au.com.programmingclub.hardenedglass

import net.minecraft.block.Block
import net.minecraft.block.TransparentBlock
import net.minecraft.block.material.Material
import net.minecraft.item.ItemStack
import java.util.Random

class HardenedGlassBlock : TransparentBlock {
    constructor() : super(
        100,
        Helpers.addOverride("/terrain.png", "/assets/hardenedglass/textures/block/hardened_glass/hardened_glass.png"),
        Material.GLASS,
        false
    ) {
        setStrength(10f)
        setBlastResistance(9f)
        setSounds(GLASS_SOUNDS)
        // setKey("hardened_glass")
    }

    override fun getBaseDropCount(random: Random): Int {
        return 0
    }
}

object HardenedGlass {
    private val HardenedGlassBlock: Block = HardenedGlassBlock()

    fun onReady() {
        Helpers.registerBlock(HardenedGlassBlock)
        // Helpers.AddName(HardenedGlassBlock, "Hardened Glass")
        Helpers.addSmelting(Block.GLASS, ItemStack(HardenedGlassBlock))
    }
}
