package au.com.programmingclub.hardenedglass.entity

import au.com.programmingclub.hardenedglass.namespace
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.item.SpawnEggItem
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object HardenedGlassEntities {
    val LONG_PIG_ID = Identifier(namespace, "long_pig")
    private val LONG_PIG_SPAWN_EGG_ID = Identifier(namespace, "long_pig_spawn_egg")
    val LONG_PIG: EntityType<LongPigEntity> =
        Registry.register(Registries.ENTITY_TYPE, LONG_PIG_ID, getLongPigEntityBuilder())

    // TODO: Add additional difference to vanilla pig?
    private fun getLongPigEntityBuilder(): EntityType<LongPigEntity> {
        val pig = EntityType.PIG
        val pigDimensions = pig.dimensions

        val entityBuilder = EntityType.Builder<LongPigEntity>.create(::LongPigEntity, pig.spawnGroup)
        entityBuilder.dimensions(2 * pigDimensions.width(), pigDimensions.height())
        // EntityType.PIG also calls passengerAttachments but not including this appears better for tall pig so doing the same here for consistency
        // Skipping it appears to have no impact on rider position
        entityBuilder.maxTrackingRange(pig.maxTrackDistance)
        return entityBuilder.build()
    }


    val TALL_PIG_ID = Identifier(namespace, "tall_pig")
    private val TALL_PIG_SPAWN_EGG_ID = Identifier(namespace, "tall_pig_spawn_egg")
    val TALL_PIG: EntityType<TallPigEntity> =
        Registry.register(Registries.ENTITY_TYPE, TALL_PIG_ID, getCamTallPigEntity())

    // TODO: Add additional difference to vanilla pig?
    private fun getCamTallPigEntity(): EntityType<TallPigEntity> {
        val pig = EntityType.PIG
        val pigDimensions = pig.dimensions

        val entityBuilder = EntityType.Builder<TallPigEntity>.create(::TallPigEntity, pig.spawnGroup)
        // TODO: Make sure 5/3 is the correct factor
        entityBuilder.dimensions(pigDimensions.width(), 5f / 3f * pigDimensions.height())
        // EntityType.PIG also calls passengerAttachments but doing so here would require calculating the new rider height by hand
        // Skipping it however appears to cause it to be determined automatically
        entityBuilder.maxTrackingRange(pig.maxTrackDistance)
        return entityBuilder.build()
    }


    fun registerEntities() {
        // TODO: Change these colours?
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
