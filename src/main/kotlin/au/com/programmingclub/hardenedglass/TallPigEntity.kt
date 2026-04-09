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
val TallPigEntityIdentifier = Identifier(namespace, "tall_pig_entity")

val TallPigEntitySpawnEggIdentifier = Identifier(namespace, "tall_pig_entity_spawn_egg")

val TallPigEntity: EntityType<BaseTallPigEntity> = Registry.register(
    Registries.ENTITY_TYPE,
    TallPigEntityIdentifier,
    EntityType.Builder.create( { entityType: EntityType<BaseTallPigEntity>, world: World ->
        BaseTallPigEntity(
            entityType,
            world
        )
    }, SpawnGroup.CREATURE).dimensions(0.75f, 0.75f).build("tall_pig_entity")
)

fun registerTallPigEntity() {
    // --------------- Test Entity ---------------
    val TALL_PIG_ENTITY_SPAWN_EGG: Item =
        SpawnEggItem(TallPigEntity, 0xc4c4c4, 0xadadad, Item.Settings())
    Registry.register(Registries.ITEM, TallPigEntitySpawnEggIdentifier, TALL_PIG_ENTITY_SPAWN_EGG)

    FabricDefaultAttributeRegistry.register(TallPigEntity, BaseTallPigEntity.createMobAttribute())

    ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register({content ->
        content.add(TALL_PIG_ENTITY_SPAWN_EGG)
    })
}

// --------------- Test Entity ---------------
class BaseTallPigEntity(entityType: EntityType<out PigEntity?>, world: World) : PigEntity(entityType, world) {
    companion object {
        fun createMobAttribute(): DefaultAttributeContainer.Builder {
            return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 15.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, .3)
                .add(EntityAttributes.GENERIC_JUMP_STRENGTH, .7)
                .add(EntityAttributes.GENERIC_SCALE, 1.0)
        }
    }
}