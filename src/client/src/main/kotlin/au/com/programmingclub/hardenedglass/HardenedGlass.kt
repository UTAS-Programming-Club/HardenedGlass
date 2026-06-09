package au.com.programmingclub.hardenedglass

import net.minecraft.block.Block
import net.minecraft.block.GlassBlock
import net.minecraft.block.material.Material
import net.minecraft.item.ItemStack

class HardenedGlassBlock : GlassBlock {
    constructor() : super(100, ModLoader.addOverride("/terrain.png", "/assets/hardenedglass/textures/block/hardened_glass/hardened_glass.png"), Material.GLASS, false) {
        setStrength(10f)
        setBlastResistance(9f)
        setSounds(GLASS_SOUNDS)
        setKey("hardened_glass")
    }
}

object HardenedGlass {
    private val HardenedGlassBlock: Block = HardenedGlassBlock()

    fun onReady() {
        ModLoader.RegisterBlock(HardenedGlassBlock)
        ModLoader.AddName(HardenedGlassBlock, "Hardened Glass")
        ModLoader.AddSmelting(Block.GLASS, ItemStack(HardenedGlassBlock))
    }
}
