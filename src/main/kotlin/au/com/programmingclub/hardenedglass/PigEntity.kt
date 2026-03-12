package au.com.programmingclub.hardenedglass

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.passive.PigEntity
import net.minecraft.item.*
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import net.minecraft.world.World

// --------------- Test Entity ---------------
val LongPigEntityIdentifier = Identifier(namespace, "long_pig_entity")

val LongPigEntitySpawnEggIdentifier = Identifier(namespace, "long_pig_entity_spawn_egg")

val LongPigEntity: EntityType<BaseLongPigEntity> = Registry.register(
  Registries.ENTITY_TYPE,
  LongPigEntityIdentifier,
  EntityType.Builder.create( { entityType: EntityType<BaseLongPigEntity>, world: World ->
    BaseLongPigEntity(
      entityType,
      world
    )
  }, SpawnGroup.CREATURE).dimensions(0.75f, 0.75f).build("long_pig_entity")
)

fun registerPigEntity() {
    // --------------- Test Entity ---------------
    val LONG_PIG_ENTITY_SPAWN_EGG: Item =
      SpawnEggItem(LongPigEntity, 0xc4c4c4, 0xadadad, Item.Settings())
    Registry.register(Registries.ITEM, LongPigEntitySpawnEggIdentifier, LONG_PIG_ENTITY_SPAWN_EGG)

    FabricDefaultAttributeRegistry.register(LongPigEntity, BaseLongPigEntity.createMobAttribute())

    ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register({content ->
      content.add(LONG_PIG_ENTITY_SPAWN_EGG)
    })
}

// --------------- Test Entity ---------------
class BaseLongPigEntity(entityType: EntityType<out PigEntity?>, world: World) : PigEntity(entityType, world) {
  companion object {
    fun createMobAttribute(): DefaultAttributeContainer.Builder {
      return MobEntity.createMobAttributes()
        .add(EntityAttributes.GENERIC_MAX_HEALTH, 25.0)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 2.0)
        .add(EntityAttributes.GENERIC_JUMP_STRENGTH, 1.2)
        .add(EntityAttributes.GENERIC_SCALE, 1.0)
    }
  }
}