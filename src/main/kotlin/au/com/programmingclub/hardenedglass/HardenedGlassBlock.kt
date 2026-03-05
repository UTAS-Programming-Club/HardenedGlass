package au.com.programmingclub.hardenedglass

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Blocks.GLASS
import net.minecraft.block.TransparentBlock
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.util.Identifier
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry

private val HardenedGlassIdentifier = Identifier(namespace, "hardened_glass")
val HardenedGlassBlock = TransparentBlock(
    AbstractBlock.Settings.copy(GLASS)
        .hardness(10f)
        .resistance(9f)
)
private val HardenedGlassItem = BlockItem(HardenedGlassBlock, Item.Settings())

fun registerHardenedGlassBlock() {
    Registry.register(Registries.BLOCK, HardenedGlassIdentifier, HardenedGlassBlock)
    Registry.register(Registries.ITEM, HardenedGlassIdentifier, HardenedGlassItem)

    ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register({content ->
        content.add(HardenedGlassItem)
    })
}
