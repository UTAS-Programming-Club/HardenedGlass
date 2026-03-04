package au.com.programmingclub.hardenedglass

import net.fabricmc.api.ModInitializer
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

object HardenedGlass : ModInitializer {
  val namespace = "hardenedglass"

  private val HardenedGlassIdentifier = Identifier(namespace, "hardened_glass")
  public val HardenedGlassBlock = TransparentBlock(
    AbstractBlock.Settings.copy(GLASS)
    .hardness(10f)
    .resistance(9f)
  )
  private val HardenedGlassItem = BlockItem(HardenedGlassBlock, Item.Settings())

  override fun onInitialize() {
    // This code runs as soon as Minecraft is in a mod-load-ready state.
    // However, some things (like resources) may still be uninitialized.
    // Proceed with mild caution.
    Registry.register(Registries.BLOCK, HardenedGlassIdentifier, HardenedGlassBlock)
    Registry.register(Registries.ITEM, HardenedGlassIdentifier, HardenedGlassItem)

    Bacon.registerItem()

    ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register({content ->
      content.add(HardenedGlassItem)
    })
  }
}
