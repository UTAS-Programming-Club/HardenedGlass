package au.com.programmingclub.hardenedglass

import au.com.programmingclub.hardenedglass.entity.HardenedGlassEntities
import net.fabricmc.api.ModInitializer
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.references.BlockItemId
import net.minecraft.resources.Identifier
import net.minecraft.resources.ResourceKey
import net.minecraft.world.entity.Entity
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.LivingEntity
import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour

data object HardenedGlass : ModInitializer {
    override fun onInitialize() {
        BaconItems.init()
        HardenedGlassBlock.init()
        HardenedGlassEntities.init()
    }

    fun id(path: String): Identifier =
        Identifier.fromNamespaceAndPath("hardenedglass", path)

    fun <T : Item> register(factory: (Item.Properties) -> T, properties: Item.Properties, name: String) : T {
        val identifier: Identifier = id(name)
        val key: ResourceKey<Item> = ResourceKey.create(Registries.ITEM, identifier)

        val item: T = factory(properties.setId(key))
        return Registry.register(BuiltInRegistries.ITEM, key, item)
    }

    fun <T : Block> register(factory: (BlockBehaviour.Properties) -> T, properties: BlockBehaviour.Properties, name: String) : T {
        val identifier: Identifier = id(name)
        val blockItemId = BlockItemId.create(identifier, identifier)

        val block: T = factory(properties.setId(blockItemId.block))
        Registry.register(BuiltInRegistries.BLOCK, blockItemId.block, block)

        val properties = Item.Properties()
            .useBlockDescriptionPrefix()
            .setId(blockItemId.item)
        val blockItem = BlockItem(block, properties)
        Registry.register(BuiltInRegistries.ITEM, blockItemId.item, blockItem)

        return block
    }

    fun <T : LivingEntity> register(builder: EntityType.Builder<T>, name: String) : EntityType<T> {
        val identifier: Identifier = id(name)
        val key: ResourceKey<EntityType<*>> = ResourceKey.create(Registries.ENTITY_TYPE, identifier)

        val entityType: EntityType<T> = builder.build(key)
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, entityType)
    }
}
