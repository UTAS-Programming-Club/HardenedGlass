package au.com.programmingclub.hardenedglass

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.references.BlockItemId
import net.minecraft.resources.Identifier
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
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

    @JvmField
    val HARDENED_GLASS: TransparentBlock = registerBlock(
        ::TransparentBlock,
        BlockBehaviour.Properties.ofFullCopy(GLASS)
                .destroyTime(10f)
                .explosionResistance(9f),
        "hardened_glass",
    )

    fun <T : Block> registerBlock(blockFactory: (BlockBehaviour.Properties) -> T, properties: BlockBehaviour.Properties, identifier: String): T {
        val identifier: Identifier = HardenedGlass.id(identifier)
        val blockItemId = BlockItemId.create(identifier, identifier)

        val block: T = blockFactory(properties.setId(blockItemId.block))
        Registry.register(BuiltInRegistries.BLOCK, blockItemId.block, block)

        val properties = Item.Properties()
            .useBlockDescriptionPrefix()
            .setId(blockItemId.item)
        val blockItem = BlockItem(block, properties)
        Registry.register(BuiltInRegistries.ITEM, blockItemId.item, blockItem)

        return block
    }
}
