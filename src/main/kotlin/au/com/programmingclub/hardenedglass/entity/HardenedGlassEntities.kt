package au.com.programmingclub.hardenedglass.entity

import au.com.programmingclub.hardenedglass.namespace
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.item.SpawnEggItem
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object HardenedGlassEntities {
    val LONG_PIG_ID = Identifier(namespace, "long_pig")
    private val LONG_PIG_SPAWN_EGG_ID = Identifier(namespace, "long_pig_spawn_egg")
    val LONG_PIG: EntityType<LongPigEntity> = Registry.register(
        Registries.ENTITY_TYPE,
        LONG_PIG_ID,
        EntityType.Builder.create(::LongPigEntity, SpawnGroup.CREATURE).dimensions(1.5f, 0.75f).build()
    )

    val TALL_PIG_ID = Identifier(namespace, "tall_pig")
    private val TALL_PIG_SPAWN_EGG_ID = Identifier(namespace, "tall_pig_spawn_egg")
    val TALL_PIG: EntityType<TallPigEntity> = Registry.register(
        Registries.ENTITY_TYPE,
        TALL_PIG_ID,
        EntityType.Builder.create(::TallPigEntity, SpawnGroup.CREATURE).dimensions(0.75f, 1.4f).build()
    )

    fun registerEntities() {
        val longPigSpawnEgg: Item = SpawnEggItem(LONG_PIG, 0xc4c4c4, 0xadadad, Item.Settings())
        val tallPigSpawnEgg: Item = SpawnEggItem(TALL_PIG, 0xc4c4c4, 0xadadad, Item.Settings())

        Registry.register(Registries.ITEM, LONG_PIG_SPAWN_EGG_ID, longPigSpawnEgg)
        Registry.register(Registries.ITEM, TALL_PIG_SPAWN_EGG_ID, tallPigSpawnEgg)

        FabricDefaultAttributeRegistry.register(LONG_PIG, LongPigEntity.createMobAttribute())
        FabricDefaultAttributeRegistry.register(TALL_PIG, TallPigEntity.createMobAttribute())

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register { content ->
            content.add(longPigSpawnEgg)
            content.add(tallPigSpawnEgg)
        }
    }
}
