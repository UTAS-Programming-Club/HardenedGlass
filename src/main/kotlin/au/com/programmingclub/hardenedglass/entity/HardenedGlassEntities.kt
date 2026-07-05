package au.com.programmingclub.hardenedglass.entity

import au.com.programmingclub.hardenedglass.MOD_ID
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.minecraft.entity.EntityType
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.item.SpawnEggItem
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

data object HardenedGlassEntities {
    val LONG_PIG_ID = Identifier(MOD_ID, "long_pig")
    val LONG_PIG: EntityType<LongPigEntity> = Registry.register(
        Registries.ENTITY_TYPE,
        LONG_PIG_ID,
        run {
            val pig = EntityType.PIG
            val pigDimensions = pig.dimensions

            val entityBuilder = EntityType.Builder<LongPigEntity>.create(::LongPigEntity, pig.spawnGroup)
            entityBuilder.dimensions(2 * pigDimensions.width(), pigDimensions.height())
            // EntityType.PIG also calls passengerAttachments but not including this appears better for tall pig so doing the same here for consistency
            // Skipping it appears to have no impact on rider position
            entityBuilder.maxTrackingRange(pig.maxTrackDistance)
            entityBuilder.build()
        },
    )

    val TALL_PIG_ID = Identifier(MOD_ID, "tall_pig")
    val TALL_PIG: EntityType<TallPigEntity> = Registry.register(
        Registries.ENTITY_TYPE,
        TALL_PIG_ID,
        run {
            val pig = EntityType.PIG
            val pigDimensions = pig.dimensions

            val entityBuilder = EntityType.Builder<TallPigEntity>.create(::TallPigEntity, pig.spawnGroup)
            // TODO: Make sure 5/3 is the correct factor
            entityBuilder.dimensions(pigDimensions.width(), 5f / 3f * pigDimensions.height())
            // EntityType.PIG also calls passengerAttachments but doing so here would require calculating the new rider height by hand
            // Skipping it however appears to cause it to be determined automatically
            entityBuilder.maxTrackingRange(pig.maxTrackDistance)
            entityBuilder.build()
        },
    )


    fun init() {
        // TODO: Change these colours?
        val longPigSpawnEgg: Item = SpawnEggItem(LONG_PIG, 0xc4c4c4, 0xadadad, Item.Settings())
        val tallPigSpawnEgg: Item = SpawnEggItem(TALL_PIG, 0xc4c4c4, 0xadadad, Item.Settings())

        Registry.register(Registries.ITEM, Identifier(MOD_ID, "long_pig_spawn_egg"), longPigSpawnEgg)
        Registry.register(Registries.ITEM, Identifier(MOD_ID, "tall_pig_spawn_egg"), tallPigSpawnEgg)

        FabricDefaultAttributeRegistry.register(LONG_PIG, LongPigEntity.createMobAttribute())
        FabricDefaultAttributeRegistry.register(TALL_PIG, TallPigEntity.createMobAttribute())

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register { content ->
            content.add(longPigSpawnEgg)
            content.add(tallPigSpawnEgg)
        }
    }
}
