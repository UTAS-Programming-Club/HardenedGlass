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
val TallPigEntitySpawnEggIdentifier = Identifier(namespace, "tall_pig_entity_spawn_egg")

fun registerTallPigEntity() {
    // --------------- Test Entity ---------------
    val TALL_PIG_ENTITY_SPAWN_EGG: Item =
        SpawnEggItem(HardenedGlassEntities.TALL_PIG, 0xc4c4c4, 0xadadad, Item.Settings())
    Registry.register(Registries.ITEM, TallPigEntitySpawnEggIdentifier, TALL_PIG_ENTITY_SPAWN_EGG)

    FabricDefaultAttributeRegistry.register(HardenedGlassEntities.TALL_PIG, TallPigEntity.createMobAttribute())

    ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS)
        .register { content -> content.add(TALL_PIG_ENTITY_SPAWN_EGG) }
}

// --------------- Test Entity ---------------
class TallPigEntity(entityType: EntityType<TallPigEntity>, world: World) : PigEntity(entityType, world) {
    companion object {
        fun createMobAttribute(): DefaultAttributeContainer.Builder {
            return createPigAttributes().add(EntityAttributes.GENERIC_JUMP_STRENGTH, .7)
        }
    }
}
