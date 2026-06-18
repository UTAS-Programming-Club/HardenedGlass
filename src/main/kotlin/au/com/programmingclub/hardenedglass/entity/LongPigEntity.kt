package au.com.programmingclub.hardenedglass.entity

import au.com.programmingclub.hardenedglass.namespace
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.minecraft.entity.EntityType
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.passive.PigEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.item.SpawnEggItem
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import net.minecraft.world.World

// --------------- Test Entity ---------------
val LongPigEntitySpawnEggIdentifier = Identifier(namespace, "long_pig_entity_spawn_egg")

fun registerPigEntity() {
    // --------------- Test Entity ---------------
    val LONG_PIG_ENTITY_SPAWN_EGG: Item =
        SpawnEggItem(HardenedGlassEntities.LONG_PIG, 0xc4c4c4, 0xadadad, Item.Settings())
    Registry.register(Registries.ITEM, LongPigEntitySpawnEggIdentifier, LONG_PIG_ENTITY_SPAWN_EGG)

    FabricDefaultAttributeRegistry.register(HardenedGlassEntities.LONG_PIG, LongPigEntity.createMobAttribute())

    ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register { content ->
        content.add(LONG_PIG_ENTITY_SPAWN_EGG)
    }
}

// --------------- Test Entity ---------------
class LongPigEntity(entityType: EntityType<LongPigEntity>, world: World) : PigEntity(entityType, world) {
    companion object {
        fun createMobAttribute(): DefaultAttributeContainer.Builder {
            return createPigAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, .3)
        }
    }
}
