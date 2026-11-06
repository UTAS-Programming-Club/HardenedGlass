package au.com.programmingclub.hardenedglass.entity

import au.com.programmingclub.hardenedglass.HardenedGlass
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.EntityTypes
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item

// TODO: Add helper method for creating entities
data object HardenedGlassEntities {
    val LONG_PIG_ID: Identifier = HardenedGlass.id("long_pig")
    private val LONG_PIG_KEY: ResourceKey<EntityType<*>> = ResourceKey.create(Registries.ENTITY_TYPE, LONG_PIG_ID)
    val LONG_PIG: EntityType<LongPigEntity> = Registry.register(
        BuiltInRegistries.ENTITY_TYPE,
        LONG_PIG_ID,
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
            entityBuilder.build(LONG_PIG_KEY)
        },
    )

    val TALL_PIG_ID = HardenedGlass.id("tall_pig")
    private val TALL_PIG_KEY: ResourceKey<EntityType<*>> = ResourceKey.create(Registries.ENTITY_TYPE, TALL_PIG_ID)
    val TALL_PIG: EntityType<TallPigEntity> = Registry.register(
        BuiltInRegistries.ENTITY_TYPE,
        TALL_PIG_ID,
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
            entityBuilder.build(TALL_PIG_KEY)
        },
    )


    fun init() {
        // TODO: Add textures and models
        // val longPigSpawnEgg = Item(Item.Properties().spawnEgg(LONG_PIG))
        // val tallPigSpawnEgg = Item(Item.Properties().spawnEgg(TALL_PIG))

        // Registry.register(BuiltInRegistries.ITEM, HardenedGlass.id("long_pig_spawn_egg"), longPigSpawnEgg)
        // Registry.register(BuiltInRegistries.ITEM, HardenedGlass.id("tall_pig_spawn_egg"), tallPigSpawnEgg)

        FabricDefaultAttributeRegistry.register(LONG_PIG, LongPigEntity.createMobAttribute())
        FabricDefaultAttributeRegistry.register(TALL_PIG, TallPigEntity.createMobAttribute())

        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.SPAWN_EGGS).register { content ->
            // content.accept(longPigSpawnEgg)
            // content.accept(tallPigSpawnEgg)
        }
    }
}
