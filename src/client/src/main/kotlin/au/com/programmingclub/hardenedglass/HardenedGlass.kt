package au.com.programmingclub.hardenedglass

import net.minecraft.block.Block
import net.minecraft.block.GlassBlock
import net.minecraft.block.material.Material
import net.minecraft.item.ItemStack
import net.ornithemc.osl.entrypoints.api.ModInitializer

class HardenedGlassBlock : GlassBlock {
    constructor() : super(100, 49, Material.GLASS, false) {
        setStrength(10f)
        setBlastResistance(9f)
        setSounds(GLASS_SOUNDS)
        setKey("hardened_glass")
    }
}

class HardenedGlass : ModInitializer {
    private val HardenedGlassBlock: Block = HardenedGlassBlock()

    override fun init() {
        ModLoader.init()

        ModLoader.RegisterBlock(HardenedGlassBlock)
        ModLoader.AddName(HardenedGlassBlock, "Hardened Glass")
        ModLoader.AddSmelting(Block.GLASS, ItemStack(HardenedGlassBlock))
    }
}
