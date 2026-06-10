package au.com.programmingclub.hardenedglass

import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.loader.api.ModContainer
import net.minecraft.block.Block
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.render.entity.PlayerRenderer
import net.minecraft.client.render.texture.DynamicTexture
import net.minecraft.client.render.texture.TextureManager
import net.minecraft.crafting.CraftingManager
import net.minecraft.entity.Entities
import net.minecraft.entity.Entity
import net.minecraft.entity.mob.player.PlayerEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.crash.CrashReport
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.HellBiome
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.awt.image.BufferedImage
import java.io.File
import java.lang.reflect.InvocationTargetException
import javax.imageio.ImageIO
import kotlin.collections.toTypedArray
import kotlin.reflect.full.staticProperties
import kotlin.reflect.jvm.isAccessible

// Partial port of rgml-quilt's version of ModLoader to beta 1.7.3
// https://github.com/sschr15/rgml-quilt/blob/f0e4c913fc7cac8b3c3fbcc2d6b64c70a0837c12/src/main/risugami/org/duvetmc/rgml/ModLoader.java
// Used under MIT License

@Suppress("unused", "FunctionName", "GrazieInspection", "SpellCheckingInspection")
object Helpers {
    private val animList: MutableList<DynamicTexture> = mutableListOf()
    // private val blockModels: MutableMap<Int?, BaseMod?> = HashMap<Int?, BaseMod?>()
    private val blockSpecialInv: MutableMap<Int, Boolean> = mutableMapOf()
    private lateinit var classMap: Map<String, Class<out Entity>>
    /*private var clock = 0L
    const val DEBUG: Boolean = false*/
    // private var field_animList: Field? = null
    // private var field_armorList: Field? = null
    // private var field_modifiers: Field? = null
    // private var field_TileEntityRenderers: Field? = null
    private var highestEntityId = 3000
    /*private val inGameHooks: MutableMap<BaseMod?, Boolean?> = HashMap<BaseMod?, Boolean?>()
    private val inGUIHooks: MutableMap<BaseMod?, Boolean?> = HashMap<BaseMod?, Boolean?>()*/
    lateinit var minecraftInstance: Minecraft
    private var itemSpriteIndex = 0
    private var itemSpritesLeft = 0
    /*private val keyList: MutableMap<BaseMod?, MutableMap<KeyBinding?, BooleanArray?>> =
        HashMap<BaseMod?, MutableMap<KeyBinding?, BooleanArray?>>()*/
    private val logger: Logger = LogManager.getLogger("ModLoader")
    /*private var method_RegisterEntityID: Method? = null
    private var method_RegisterTileEntity: Method? = null
    private var nextBlockModelID = 1000*/
    private val overrides: MutableMap<Int, MutableMap<String, Int>> = mutableMapOf()
    private lateinit var standardBiomes: Array<Biome>
    private var terrainSpriteIndex = 0
    private var terrainSpritesLeft = 0
    private var texPack: String? = null
    private var texturesAdded = false
    private val usedItemSprites = BooleanArray(256)
    private val usedTerrainSprites = BooleanArray(256)
    @JvmField
    val smeltables: MutableMap<Int, Int> = mutableMapOf()

    /*fun AddAchievementDesc(achievement: AchievementStat, name: String?, description: String?) {
        try {
            if (achievement.name.contains(".")) {
                val split: Array<String?> = achievement.name.split("\\.")
                if (split.size == 2) {
                    val key = split[1]
                    AddLocalization("achievement." + key, name)
                    AddLocalization("achievement." + key + ".desc", description)
                    setPrivateValue(Stat::class.java, achievement, 1, I18n.translate("achievement." + key))
                    setPrivateValue(
                        AchievementStat::class.java,
                        achievement,
                        3,
                        I18n.translate("achievement." + key + ".desc")
                    )
                } else {
                    ModLoader.setPrivateValue<AchievementStat?, String?>(Stat::class.java, achievement, 1, name)
                    ModLoader.setPrivateValue<AchievementStat?, String?>(
                        AchievementStat::class.java,
                        achievement,
                        3,
                        description
                    )
                }
            } else {
                ModLoader.setPrivateValue<AchievementStat?, String?>(Stat::class.java, achievement, 1, name)
                ModLoader.setPrivateValue<AchievementStat?, String?>(
                    AchievementStat::class.java,
                    achievement,
                    3,
                    description
                )
            }
        } catch (var5: IllegalArgumentException) {
            logger.throwing<Exception?>(var5)
            ThrowException(var5)
        } catch (var5: NoSuchFieldException) {
            logger.throwing<Exception?>(var5)
            ThrowException(var5)
        } catch (var5: SecurityException) {
            logger.throwing<Exception?>(var5)
            ThrowException(var5)
        }
    }

    fun AddAllFuel(id: Int, metadata: Int): Int {
        logger.trace("Finding fuel for " + id)
        var result = 0
        val iter: MutableIterator<BaseMod> = modList.iterator()

        while (iter.hasNext() && result == 0) {
            result = iter.next().AddFuel(id, metadata)
        }

        if (result != 0) {
            logger.trace("Returned " + result)
        }

        return result
    }

    fun AddAllRenderers(renderers: MutableMap<Class<out Entity?>?, EntityRenderer<*>?>?) {
        if (!hasInit) {
            init()
            logger.trace("Initialized")
        }

        for (mod in modList) {
            mod.AddRenderer(renderers)
        }
    }

    fun addAnimation(anim: TextureAtlas) {
        logger.trace("Adding animation " + anim.toString())

        for (oldAnim in animList) {
            if (oldAnim.sprite === anim.sprite && oldAnim.type === anim.type) {
                animList.remove(anim)
                break
            }
        }

        animList.add(anim)
    }*/

    fun AddArmor(armor: String): Int {
        val index = PlayerRenderer.ARMOR_VARIANTS.indexOf(armor)
        if (index != -1) {
            return index
        }

        val combinedList: MutableList<String> = PlayerRenderer.ARMOR_VARIANTS.toMutableList()
        combinedList.add(armor)

        PlayerRenderer.ARMOR_VARIANTS = combinedList.toTypedArray()
        return combinedList.indexOf(armor)
    }

    /*fun AddLocalization(key: String, value: String) {
        Language.getInstance().translations[key] = value
    }

    fun AddName(item: Item, name: String) {
        var tag: String? = null
        if (item.translationKey != null) {
            tag = item.translationKey + ".name"
        }

        AddName(item, tag, name)
    }

    fun AddName(block: Block, name: String) {
        var tag: String? = null
        if (block.translationKey != null) {
            tag = block.translationKey + ".name"
        }

        AddName(block, tag, name)
    }

    fun AddName(stack: ItemStack, name: String) {
        var tag: String? = null
        if (stack.translationKey != null) {
            tag = stack.translationKey + ".name"
        }

        AddName(stack, tag, name)
    }

    private fun AddName(instance: Any, tag: String?, name: String) {
        if (tag != null) {
            AddLocalization(tag, name)
        } else {
            val e = Exception("$instance is missing name tag!")
            logger.error("Error in AddName", e)
            ThrowException(e)
        }
    }*/

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
        var left: Int
        val atlas: Int
        when (path) {
            "/terrain.png" -> {
                atlas = 0
                left = terrainSpritesLeft
            }
            "/gui/items.png" -> {
                atlas = 1
                left = itemSpritesLeft
            }
            else -> return
        }

        logger.trace("addOverride($path,$overlayPath,$index). $left left.")
        val overlays: MutableMap<String, Int> = overrides.computeIfAbsent(atlas) { mutableMapOf() }

        overlays[overlayPath] = index
    }

    fun AddRecipe(output: ItemStack, vararg params: Any) {
        CraftingManager.getInstance().registerShaped(output, *params)
    }

    /*fun AddShapelessRecipe(output: ItemStack, vararg params: Any) {
        CraftingManager.getInstance().registerShapeless(output, *params)
    }*/

    fun AddSmelting(input: Block, output: ItemStack) {
        smeltables[input.id] = output.id
    }

    fun AddSmelting(input: Item, output: ItemStack) {
        smeltables[input.id] = output.id
    }

    /*fun AddSpawn(
        entityClass: Class<out MobEntity>,
        weightedProb: Int,
        // min: Int,
        // max: Int,
        spawnList: MobCategory,
        vararg biomes: Biome
    ) {
        val fullBiomes: Array<out Biome> = biomes.ifEmpty { standardBiomes }

        for (biome in fullBiomes) {
            val list : MutableList<Biome.SpawnEntry>? = biome.getSpawnEntries(spawnList)
            if (list != null) {
                var exists = false

                for (entry in list) {
                    if (entry.type == entityClass) {
                        entry.weight = weightedProb
                        // entry.minGroupSize = min
                        // entry.maxGroupSize = max
                        exists = true
                        break
                    }
                }

                if (!exists) {
                    list.add(Biome.SpawnEntry(entityClass, weightedProb/*, min, max*/))
                }
            }
        }
    }

    fun AddSpawn(
        entityName: String,
        weightedProb: Int,
        // min: Int,
        // max: Int,
        spawnList: MobCategory,
        vararg biomes: Biome
    ) {
        val entityClass: Class<out Entity> = classMap[entityName]!!
        if (MobEntity::class.java.isAssignableFrom(entityClass)) {
            @Suppress("UNCHECKED_CAST")
            AddSpawn(entityClass as Class<out MobEntity>, weightedProb/*, min, max*/, spawnList, *biomes)
        }
    }

    fun DispenseEntity(
        world: World?,
        x: Double,
        y: Double,
        z: Double,
        xVel: Int,
        zVel: Int,
        item: ItemStack?
    ): Boolean {
        var result = false
        val iter: MutableIterator<BaseMod> = modList.iterator()

        while (iter.hasNext() && !result) {
            result = iter.next().DispenseEntity(world, x, y, z, xVel, zVel, item)
        }

        return result
    }

    fun getUniqueBlockModelID(mod: BaseMod?, full3DItem: Boolean): Int {
        val id = nextBlockModelID++
        blockModels.put(id, mod)
        blockSpecialInv.put(id, full3DItem)
        return id
    }*/

    val uniqueEntityId: Int
        get() = highestEntityId++

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

    fun init(minecraft: Minecraft) {
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

        try {
            // instance!!.gameRenderer = EntityRendererProxy(instance)
            classMap = Entities.KEY_TO_TYPE
            // field_modifiers = Field::class.java.getDeclaredField("modifiers")
            // field_modifiers!!.setAccessible(true)
            // field_TileEntityRenderers = BlockEntityRenderDispatcher::class.java.getDeclaredFields()[0]
            // field_TileEntityRenderers!!.setAccessible(true)
            // field_armorList = PlayerRenderer::class.java.getDeclaredFields()[3]
            // field_modifiers!!.setInt(field_armorList, field_armorList!!.modifiers and -17)
            // field_armorList!!.setAccessible(true)
            // field_animList = TextureManager::class.java.getDeclaredFields()[6]
            // field_animList!!.setAccessible(true)

            standardBiomes = Biome::class.staticProperties.mapNotNull {
                it.isAccessible = true
                val biome: Any? = it.get()
                if (biome is Biome && (biome !is HellBiome/* && biome !is TheEndBiome*/)) {
                    biome
                } else {
                    null
                }
            }.toTypedArray()

            /*method_RegisterTileEntity = RuntimeRemapUtil.getRuntimeDeclaredMethod(
                BlockEntity::class.java, "a", arrayOf<Class<*>>(
                    Class::class.java, String::class.java
                )
            )
            method_RegisterTileEntity.setAccessible(true)
            method_RegisterEntityID = RuntimeRemapUtil.getRuntimeDeclaredMethod(
                Entities::class.java, "a", arrayOf<Class<*>>(
                    Class::class.java, String::class.java, Int::class.javaPrimitiveType
                )
            )
            method_RegisterEntityID.setAccessible(true)*/
        } catch (e: SecurityException) {
            logger.error("Error in init", e)
            ThrowException(e)
            throw RuntimeException(e)
        } catch (e: IllegalAccessException) {
            logger.error("Error in init", e)
            ThrowException(e)
            throw RuntimeException(e)
        } catch (e: IllegalArgumentException) {
            logger.error("Error in init", e)
            ThrowException(e)
            throw RuntimeException(e)
        } catch (e: NoSuchMethodException) {
            logger.error("Error in init", e)
            ThrowException(e)
            throw RuntimeException(e)
        } catch (e: NoSuchFieldException) {
            logger.error("Error in init", e)
            ThrowException(e)
            throw RuntimeException(e)
        }

        try {
            // instance!!.options.keyBindings = RegisterAllKeys(instance!!.options.keyBindings)
            // instance!!.options.load()
            initStats()
        } catch (e: Throwable) {
            logger.error("Error in init", e)
            ThrowException("ModLoader has failed to initialize.", e)

            throw RuntimeException(e)
        }
    }

    private fun initStats() {
        /*for (id in Block.BY_ID.indices) {
            if (Stats.byKey(16777216 + id) != null && Block.BY_ID[id] != null && Block.BY_ID[id].hasStats()) {
                val str: String = Language.getInstance().translate("stat.mineBlock", Block.BY_ID[id].name)!!
                val stat = ItemStat(16777216 + id, str, id)
                // Stats.BLOCKS_MINED[id] = stat.register()
                Stats.MINED.add(stat)
            }
        }

        for (id in Item.BY_ID.indices) {
            if (Stats.byKey(16908288 + id) != null && Item.BY_ID[id] != null) {
                val str: String = Language.getInstance().translate("stat.useItem", Item.BY_ID[id].displayName)!!
                val stat = ItemStat(16908288 + id, str, id)
                // Stats.ITEMS_USED[id] = stat.register()
                if (id >= Block.BY_ID.size) {
                    Stats.USED.add(stat)
                }
            }

            if (Stats.byKey(16973824 + id) != null && Item.BY_ID[id] != null && Item.BY_ID[id].isDamageable) {
                val str: String? = Language.getInstance().translate("stat.breakItem", Item.BY_ID[id].displayName)
                Stats.ITEMS_BROKEN[id] = ItemStat(16973824 + id, str, id).register()
            }
        }

        val idHashSet = HashSet<Int>()

        for (result in CraftingManager.getInstance().recipes) {
            idHashSet.add(result.result.id)
        }

        for (result in SmeltingManager.getInstance().recipes.values) {
            idHashSet.add(result.id)
        }

        for (id in idHashSet) {
            if (Stats.byKey(16842752 + id!!) != null && Item.BY_ID[id] != null) {
                val str: String? = Language.getInstance().translate("stat.craftItem", Item.BY_ID[id].displayName)
                Stats.ITEMS_CRAFTED[id] = ItemStat(16842752 + id, str, id).register()
            }
        }*/
    }

    fun isGUIOpen(gui: Class<out Screen>?): Boolean {
        val game = minecraftInstance
        @Suppress("IfThenToElvis")
        return if (gui == null) {
            game.screen == null
        } else {
            gui.isInstance(game.screen)
        }
    }

    @Throws(Exception::class)
    fun loadImage(texCache: TextureManager, path: String): BufferedImage {
        val mod: ModContainer = FabricLoader.getInstance().getModContainer("hardenedglass").get()
        val relativePath: String = path.substring(1)
        val file: File = mod.findPath(relativePath).get().toFile()
        val image: BufferedImage? = ImageIO.read(file)
        if (image == null) {
            throw Exception("Image corrupted: $path")
        } else {
            return image
        }
    }

    /*fun OnItemPickup(player: PlayerEntity?, item: ItemStack?) {
        for (mod in modList) {
            mod.OnItemPickup(player, item)
        }
    }*/

    fun OnTick(tick: Float, game: Minecraft) {
        if (!texturesAdded && game.textureManager != null) {
            RegisterAllTextureOverrides(game.textureManager)
            texturesAdded = true
        }

        /*var newclock = 0L
        if (game.world != null) {
            newclock = game.world.getTime()
            val iter: MutableIterator<MutableMap.MutableEntry<BaseMod?, Boolean?>> = inGameHooks.entries.iterator()

            while (iter.hasNext()) {
                val modSet: MutableMap.MutableEntry<BaseMod?, Boolean?> = iter.next()
                if ((clock != newclock || !modSet.value!!) && !modSet.key.OnTickInGame(tick, game)) {
                    iter.remove()
                }
            }
        }

        if (game.screen != null) {
            val iter: MutableIterator<MutableMap.MutableEntry<BaseMod?, Boolean?>> = inGUIHooks.entries.iterator()

            while (iter.hasNext()) {
                val modSet: MutableMap.MutableEntry<BaseMod?, Boolean?> = iter.next()
                if ((clock != newclock || !(modSet.value!! and (game.world != null))) && !modSet.key.OnTickInGUI(
                        tick,
                        game,
                        game.screen
                    )
                ) {
                    iter.remove()
                }
            }
        }

        if (clock != newclock) {
            for (modSet in keyList.entries) {
                for (keySet in modSet.value.entries) {
                    val state: Boolean = Keyboard.isKeyDown(keySet.key.keyCode)
                    val keyInfo: BooleanArray = keySet.value!!
                    val oldState = keyInfo[1]
                    keyInfo[1] = state
                    if (state && (!oldState || keyInfo[0])) {
                        modSet.key.KeyboardEvent(keySet.key)
                    }
                }
            }
        }

        clock = newclock*/
    }

    fun OpenGUI(player: PlayerEntity, gui: Screen?) {
        val game = minecraftInstance
        if (game.player === player && gui != null) {
            game.openScreen(gui)
        }
    }

    /*fun PopulateChunk(generator: ChunkSource, chunkX: Int, chunkZ: Int, world: World) {
        if (!hasInit) {
            init()
            logger.trace("Initialized")
        }

        val rnd = Random(world.getSeed())
        val xSeed = rnd.nextLong() / 2L * 2L + 1L
        val zSeed = rnd.nextLong() / 2L * 2L + 1L
        rnd.setSeed(chunkX.toLong() * xSeed + chunkZ.toLong() * zSeed xor world.getSeed())

        for (mod in modList) {
            if (generator.getDebugInfo().equals("RandomLevelSource")) {
                mod.GenerateSurface(world, rnd, chunkX shl 4, chunkZ shl 4)
            } else if (generator.getDebugInfo().equals("HellRandomLevelSource")) {
                mod.GenerateNether(world, rnd, chunkX shl 4, chunkZ shl 4)
            }
        }
    }

    fun RegisterAllKeys(keys: Array<KeyBinding?>?): Array<KeyBinding?> {
        val combinedList: MutableList<KeyBinding?> = LinkedList<E?>(Arrays.asList(keys))

        for (keyMap in keyList.values) {
            combinedList.addAll(keyMap.keys)
        }

        return combinedList.toTypedArray<KeyBinding?>()
    }*/

    fun RegisterAllTextureOverrides(cache: TextureManager) {
        animList.clear()
        /* val game = minecraftInstance

        for (mod in modList) {
            mod.RegisterAnimation(game)
        }*/

        for (anim in animList) {
            cache.addDynamicTexture(anim)
        }

        for (overlay: Map.Entry<Int, Map<String, Int>> in overrides.entries) {
            for (overlayEntry: Map.Entry<String, Int> in overlay.value.entries) {
                val overlayPath = overlayEntry.key
                val index: Int = overlayEntry.value
                val dst: Int = overlay.key

                try {
                    val im: BufferedImage = loadImage(cache, overlayPath)
                    val anim = ModTextureStatic(index, dst, im)
                    cache.addDynamicTexture(anim)
                } catch (e: Exception) {
                    logger.error("Error in RegisterAllTextureOverrides", e)
                    ThrowException(e)
                    throw RuntimeException(e)
                }
            }
        }
    }

    @JvmOverloads
    fun RegisterBlock(block: Block, itemclass: Class<out BlockItem?>? = null) {
        try {
            val id = block.id
            val item: BlockItem = if (itemclass != null) {
                itemclass.getConstructor(Integer.TYPE).newInstance(id - 256)
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

    /*fun RegisterEntityID(entityClass: Class<out Entity?>?, entityName: String?, id: Int) {
        try {
            method_RegisterEntityID.invoke(null, entityClass, entityName, id)
        } catch (var4: IllegalArgumentException) {
            logger.error("Error in RegisterEntityID", var4)
            ThrowException(var4)
        } catch (var4: InvocationTargetException) {
            logger.error("Error in RegisterEntityID", var4)
            ThrowException(var4)
        } catch (var4: IllegalAccessException) {
            logger.error("Error in RegisterEntityID", var4)
            ThrowException(var4)
        }
    }

    fun RegisterKey(mod: BaseMod?, keyHandler: KeyBinding?, allowRepeat: Boolean) {
        var keyMap: MutableMap<KeyBinding?, BooleanArray?>? = keyList.get(mod)
        if (keyMap == null) {
            keyMap = HashMap<KeyBinding?, BooleanArray?>()
        }

        keyMap.put(keyHandler, booleanArrayOf(allowRepeat, false))
        keyList.put(mod, keyMap)
    }

    @JvmOverloads
    fun RegisterTileEntity(
        tileEntityClass: Class<out BlockEntity?>?,
        id: String?,
        renderer: BlockEntityRenderer<*>? = null
    ) {
        try {
            method_RegisterTileEntity.invoke(null, tileEntityClass, id)
            if (renderer != null) {
                val ref = BlockEntityRenderDispatcher.INSTANCE
                val renderers: MutableMap<Class<out BlockEntity?>?, BlockEntityRenderer<*>?> =
                    field_TileEntityRenderers.get(ref) as MutableMap<*, *>
                renderers.put(tileEntityClass, renderer)
                renderer.init(ref)
            }
        } catch (var5: IllegalArgumentException) {
            logger.error("Error in RegisterTileEntity", var5)
            ThrowException(var5)
        } catch (var5: InvocationTargetException) {
            logger.error("Error in RegisterTileEntity", var5)
            ThrowException(var5)
        } catch (var5: IllegalAccessException) {
            logger.error("Error in RegisterTileEntity", var5)
            ThrowException(var5)
        }
    }

    fun RemoveSpawn(entityClass: Class<out MobEntity>, spawnList: MobCategory, vararg biomes: Biome) {
        val fullBiomes: Array<out Biome> = biomes.ifEmpty { standardBiomes }

        for (biome in fullBiomes) {
            val list : MutableList<Biome.SpawnEntry>? = biome.getSpawnEntries(spawnList)
            list?.removeIf { entry: Biome.SpawnEntry -> entry.type == entityClass }
        }
    }

    fun RemoveSpawn(entityName: String, spawnList: MobCategory, vararg biomes: Biome) {
        val entityClass: Class<out Entity> = classMap[entityName]!!
        if (MobEntity::class.java.isAssignableFrom(entityClass)) {
            @Suppress("UNCHECKED_CAST")
            RemoveSpawn(entityClass as Class<out MobEntity>, spawnList, *biomes)
        }
    }*/

    fun RenderBlockIsItemFull3D(modelID: Int): Boolean {
        return if (!blockSpecialInv.containsKey(modelID)) {
            modelID == 16
        } else {
            blockSpecialInv[modelID]!!
        }
    }

    /*fun RenderInvBlock(renderer: BlockRenderer?, block: Block?, metadata: Int, modelID: Int) {
        val mod: BaseMod? = blockModels.get(modelID)
        if (mod != null) {
            mod.RenderInvBlock(renderer, block, metadata, modelID)
        }
    }

    fun RenderWorldBlock(
        renderer: BlockRenderer?,
        world: WorldView?,
        x: Int,
        y: Int,
        z: Int,
        block: Block?,
        modelID: Int
    ): Boolean {
        val mod: BaseMod? = blockModels.get(modelID)
        return mod != null && mod.RenderWorldBlock(renderer, world, x, y, z, block, modelID)
    }

    fun SetInGameHook(mod: BaseMod?, enable: Boolean, useClock: Boolean) {
        if (enable) {
            inGameHooks.put(mod, useClock)
        } else {
            inGameHooks.remove(mod)
        }
    }

    fun SetInGUIHook(mod: BaseMod?, enable: Boolean, useClock: Boolean) {
        if (enable) {
            inGUIHooks.put(mod, useClock)
        } else {
            inGUIHooks.remove(mod)
        }
    }

    @Throws(IllegalArgumentException::class, SecurityException::class, NoSuchFieldException::class)
    fun <T, E> setPrivateValue(instanceclass: Class<in T?>, instance: T?, fieldindex: Int, value: E?) {
        try {
            val f: Field = instanceclass.getDeclaredFields()[fieldindex]
            f.setAccessible(true)
            val modifiers: Int = field_modifiers.getInt(f)
            if ((modifiers and 16) != 0) {
                field_modifiers.setInt(f, modifiers and -17)
            }

            f.set(instance, value)
        } catch (var6: IllegalAccessException) {
            logger.error("Error in setPrivateValue", var6)
            ThrowException("An impossible error has occurred!", var6)
        }
    }

    @Throws(IllegalArgumentException::class, SecurityException::class, NoSuchFieldException::class)
    fun <T, E> setPrivateValue(instanceclass: Class<in T?>, instance: T?, field: String, value: E?) {
        try {
            val f: Field = instanceclass.getDeclaredField(field)
            val modifiers: Int = field_modifiers.getInt(f)
            if ((modifiers and 16) != 0) {
                field_modifiers.setInt(f, modifiers and -17)
            }

            f.setAccessible(true)
            f.set(instance, value)
        } catch (var6: IllegalAccessException) {
            logger.error("Error in setPrivateValue", var6)
            ThrowException("An impossible error has occurred!", var6)
        }
    }

    fun TakenFromCrafting(player: PlayerEntity?, item: ItemStack?, matrix: Inventory?) {
        for (mod in modList) {
            mod.TakenFromCrafting(player, item, matrix)
        }
    }

    fun TakenFromFurnace(player: PlayerEntity?, item: ItemStack?) {
        for (mod in modList) {
            mod.TakenFromFurnace(player, item)
        }
    }*/

    @Suppress("unused", "FunctionName")
    fun ThrowException(message: String, e: Throwable) {
        minecraftInstance.handleCrash(CrashReport(message, e))
    }

    private fun ThrowException(e: Throwable) {
        ThrowException("Exception occurred in ModLoader", e)
    }
}
