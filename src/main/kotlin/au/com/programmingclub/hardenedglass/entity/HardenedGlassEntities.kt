package au.com.programmingclub.hardenedglass.entity

import au.com.programmingclub.hardenedglass.HardenedGlass
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.EntityTypes
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import net.minecraft.world.item.SpawnEggItem

// TODO: Add helper method for creating entities
data object HardenedGlassEntities {
    fun init() {
        FabricDefaultAttributeRegistry.register(LONG_PIG, LongPigEntity.createMobAttribute())
        FabricDefaultAttributeRegistry.register(TALL_PIG, TallPigEntity.createMobAttribute())

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.SPAWN_EGGS).register { content ->
            content.accept(LONG_PIG_SPAWN_EGG)
            content.accept(TALL_PIG_SPAWN_EGG)
        }
    }

    val LONG_PIG: EntityType<LongPigEntity> = HardenedGlass.register(
        // TODO: Add additional difference to vanilla pig?
        run {
            val pig = EntityTypes.PIG
            val pigDimensions = pig.dimensions

            val entityBuilder: EntityType.Builder<LongPigEntity> =
                EntityType.Builder.of(::LongPigEntity, pig.category)
            entityBuilder.sized(2 * pigDimensions.width(), pigDimensions.height())
            // EntityType.PIG also calls passengerAttachments but not including this appears better for tall pig so doing the same here for consistency
            // Skipping it appears to have no impact on rider position
            entityBuilder.clientTrackingRange(pig.clientTrackingRange())
            return@run entityBuilder
        },
        "long_pig"
    )

    val TALL_PIG: EntityType<TallPigEntity> = HardenedGlass.register(
        // TODO: Add additional difference to vanilla pig?
        run {
            val pig = EntityTypes.PIG
            val pigDimensions = pig.dimensions

            val entityBuilder: EntityType.Builder<TallPigEntity> =
                EntityType.Builder.of(::TallPigEntity, pig.category)
            // TODO: Make sure 5/3 is the correct factor
            entityBuilder.sized(pigDimensions.width(), 5f / 3f * pigDimensions.height())
            // EntityType.PIG also calls passengerAttachments but doing so here would require calculating the new rider height by hand
            // Skipping it however appears to cause it to be determined automatically
            entityBuilder.clientTrackingRange(pig.clientTrackingRange())
            return@run entityBuilder
        },
        "tall_pig"
    )

    // TODO: Add textures and models
    val LONG_PIG_SPAWN_EGG: SpawnEggItem = HardenedGlass.register(
        ::SpawnEggItem,
        Item.Properties()
            .spawnEgg(LONG_PIG),
        "long_pig_spawn_egg"
    )

    val TALL_PIG_SPAWN_EGG: SpawnEggItem = HardenedGlass.register(
        ::SpawnEggItem,
        Item.Properties()
            .spawnEgg(TALL_PIG),
        "tall_pig_spawn_egg"
    )
}
