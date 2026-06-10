package au.com.programmingclub.hardenedglass

import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.server.MinecraftServer
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.lang.reflect.InvocationTargetException

// Partial port of rgml-quilt's version of ModLoader to beta 1.7.3
// https://github.com/sschr15/rgml-quilt/blob/f0e4c913fc7cac8b3c3fbcc2d6b64c70a0837c12/src/main/risugami/org/duvetmc/rgml/ModLoader.java
// Used under MIT License

@Suppress("unused", "FunctionName", "SpellCheckingInspection")
object Helpers {
    private var highestEntityId = 3000
    lateinit var minecraftInstance: MinecraftServer
    private var itemSpriteIndex = 0
    private var itemSpritesLeft = 0
    private val logger: Logger = LogManager.getLogger("ModLoader")
    private var terrainSpriteIndex = 0
    private var terrainSpritesLeft = 0
    private val usedItemSprites = BooleanArray(256)
    private val usedTerrainSprites = BooleanArray(256)
    @JvmField
    val smeltables: MutableMap<Int, Int> = mutableMapOf()

    fun addOverride(fileToOverride: String, fileToAdd: String): Int {
        try {
            val i = getUniqueSpriteIndex(fileToOverride)
            addOverride(fileToOverride, fileToAdd, i)
            return i
        } catch (e: Throwable) {
            logger.error("Error in addOverride", e)
            ThrowException(e)
            throw RuntimeException(e)
        }
    }

    fun addOverride(path: String, overlayPath: String, index: Int) {
        val left: Int = when (path) {
            "/terrain.png" -> {
                terrainSpritesLeft
            }

            "/gui/items.png" -> {
                itemSpritesLeft
            }

            else -> return
        }

        logger.trace("addOverride($path,$overlayPath,$index). $left left.")
    }

    fun AddRecipe(output: ItemStack, vararg params: Any) {
    }

    fun AddSmelting(input: Block, output: ItemStack) {
        smeltables[input.id] = output.id
    }

    fun AddSmelting(input: Item, output: ItemStack) {
        smeltables[input.id] = output.id
    }

    private val uniqueItemSpriteIndex: Int
        get() {
            while (itemSpriteIndex < usedItemSprites.size) {
                if (!usedItemSprites[itemSpriteIndex]) {
                    usedItemSprites[itemSpriteIndex] = true
                    --itemSpritesLeft
                    return itemSpriteIndex++
                }

                ++itemSpriteIndex
            }

            val e = Exception("No more empty item sprite indices left!")
            logger.error("Error in getUniqueItemSpriteIndex", e)
            ThrowException(e)
            return 0
        }

    fun getUniqueSpriteIndex(path: String): Int {
        when (path) {
            "/gui/items.png" -> {
                return uniqueItemSpriteIndex
            }
            "/terrain.png" -> {
                return uniqueTerrainSpriteIndex
            }
            else -> {
                val e = Exception("No registry for this texture: $path")
                logger.error("Error in getUniqueItemSpriteIndex", e)
                ThrowException(e)
                return 0
            }
        }
    }

    private val uniqueTerrainSpriteIndex: Int
        get() {
            while (terrainSpriteIndex < usedTerrainSprites.size) {
                if (!usedTerrainSprites[terrainSpriteIndex]) {
                    usedTerrainSprites[terrainSpriteIndex] = true
                    --terrainSpritesLeft
                    return terrainSpriteIndex++
                }

                ++terrainSpriteIndex
            }

            val e = Exception("No more empty terrain sprite indices left!")
            logger.error("Error in getUniqueItemSpriteIndex", e)
            ThrowException(e)
            return 0
        }

    fun init(minecraft: MinecraftServer) {
        val usedItemSpritesString =
            "1111111111111111111111111111111111111101111111011111111111111001111111111111111111111111111011111111100110000011111110000000001111111001100000110000000100000011000000010000001100000000000000110000000000000000000000000000000000000000000000001100000000000000"
        val usedTerrainSpritesString =
            "1111111111111111111111111111110111111111111111111111110111111111111111111111000111111011111111111111001111111110111111111111100011111111000010001111011110000000111111000000000011111100000000001111000000000111111000000000001101000000000001111111111111000011"

        for (i in 0..255) {
            usedItemSprites[i] = usedItemSpritesString[i] == '1'
            if (!usedItemSprites[i]) {
                ++itemSpritesLeft
            }

            usedTerrainSprites[i] = usedTerrainSpritesString[i] == '1'
            if (!usedTerrainSprites[i]) {
                ++terrainSpritesLeft
            }
        }

        minecraftInstance = minecraft
    }

    fun OnTick(tick: Float, game: MinecraftServer) {
    }

    @JvmOverloads
    fun RegisterBlock(block: Block, itemClass: Class<out BlockItem>? = null) {
        try {
            val id = block.id
            val item: BlockItem = if (itemClass != null) {
                itemClass.getConstructor(Integer.TYPE).newInstance(id - 256)
            } else {
                BlockItem(id - 256)
            }

            if (Block.BY_ID[id] != null && Item.BY_ID[id] == null) {
                Item.BY_ID[id] = item
            }
        } catch (e: IllegalArgumentException) {
            logger.error("Error in RegisterBlock", e)
            ThrowException(e)
        } catch (e: NoSuchMethodException) {
            logger.error("Error in RegisterBlock", e)
            ThrowException(e)
        } catch (e: InvocationTargetException) {
            logger.error("Error in RegisterBlock", e)
            ThrowException(e)
        } catch (e: InstantiationException) {
            logger.error("Error in RegisterBlock", e)
            ThrowException(e)
        } catch (e: SecurityException) {
            logger.error("Error in RegisterBlock", e)
            ThrowException(e)
        } catch (e: IllegalAccessException) {
            logger.error("Error in RegisterBlock", e)
            ThrowException(e)
        }
    }

    private fun ThrowException(e: Throwable) {
        throw e
    }
}
