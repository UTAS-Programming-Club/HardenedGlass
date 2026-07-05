package au.com.programmingclub.hardenedglass

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.Blocks.GLASS
import net.minecraft.block.TransparentBlock
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

// TODO: Refactor, too annoying to do in TUI.
data object HardenedGlassBlock {
    fun init() {}

    @JvmField
    val HARDENED_GLASS: TransparentBlock = registerBlock(
        TransparentBlock(
            AbstractBlock.Settings.copy(GLASS)
                .hardness(10f)
                .resistance(9f),
        ),
        "hardened_glass",
    )

    fun <T : Block> registerBlock(block: T, identifier: String): T {
        val identifier = Identifier(MOD_ID, identifier)
        val block: T = Registry.register(Registries.BLOCK, identifier, block)
        Registry.register(Registries.ITEM, identifier, BlockItem(block, Item.Settings()))
        return block
    }
}