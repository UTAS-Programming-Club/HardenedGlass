package au.com.programmingclub.hardenedglass

import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraft.world.World
import java.util.Random

class HardenedGlassBlock : Block {
    constructor() : super(
        100,
        Helpers.addOverride("/terrain.png", "/assets/hardenedglass/textures/block/hardened_glass/hardened_glass.png"),
    ) {
        setStrength(10f)
        setBlastResistance(9f)
        setSounds(METAL_SOUNDS)
        // setKey("hardened_glass")
    }

    // From TransparentBock
    override fun isSolid(): Boolean {
        return false
    }

    override fun shouldRenderFace(world: World, x: Int, y: Int, z: Int, face: Int): Boolean {
        val block = world.getBlock(x, y, z)
        if (block == this.id) {
            return false
        }

        return super.shouldRenderFace(world, x, y, z, face)
    }

    // from GlassBlock
    override fun getBaseDropCount(random: Random): Int {
        return 0
    }
}

object HardenedGlass {
    private val HardenedGlassBlock: Block = HardenedGlassBlock()

    fun onReady() {
        Helpers.registerBlock(HardenedGlassBlock)
        // Helpers.AddName(HardenedGlassBlock, "Hardened Glass")
        Helpers.addSmelting(Block.GLASS, ItemStack(HardenedGlassBlock.id))
    }
}
