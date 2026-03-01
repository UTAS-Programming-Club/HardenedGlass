package au.com.programmingclub.hardenedglass

import net.fabricmc.api.ModInitializer
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricDefaultAttributeRegistry
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry
import net.minecraft.block.AbstractBlock
import net.minecraft.block.Block
import net.minecraft.block.Blocks.GLASS
import net.minecraft.block.TransparentBlock
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.mob.MobEntity
import net.minecraft.entity.mob.PathAwareEntity
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier
import net.minecraft.world.World


object HardenedGlass : ModInitializer {
  val namespace = "hardenedglass"

  // --------------- Hardened Glass ---------------
  private val HardenedGlassIdentifier = Identifier(namespace, "hardened_glass")
  public val HardenedGlassBlock = TransparentBlock(
    AbstractBlock.Settings.copy(GLASS)
    .hardness(10f)
    .resistance(9f)
  )
  private val HardenedGlassItem = BlockItem(HardenedGlassBlock, Item.Settings())

  // --------------- Test Block ---------------
  private val TestBlockIdentifier = Identifier(namespace, "test_block")
  public val TestBlockBlock = Block(
    AbstractBlock.Settings.create()
  )
  private val TestBlockItem = BlockItem(TestBlockBlock, Item.Settings())

  // --------------- Test Entity ---------------
  val TestEntity: EntityType<BaseTestEntity?>? = Registry.register<EntityType<*>?, EntityType<BaseTestEntity?>?>(
    Registries.ENTITY_TYPE,
    Identifier.of(namespace, "cube"),
    EntityType.Builder.create<BaseTestEntity?>(EntityType.EntityFactory { entityType: EntityType<BaseTestEntity?>?, world: World? ->
      BaseTestEntity(
        entityType!!,
        world!!
      )
    }, SpawnGroup.CREATURE).dimensions(0.75f, 0.75f).build("cube")
  )
  private val TestEntityIdentifier = Identifier(namespace, "test_entity")


  override fun onInitialize() {
    // This code runs as soon as Minecraft is in a mod-load-ready state.
    // However, some things (like resources) may still be uninitialized.
    // Proceed with mild caution.

    // --------------- Hardened Glass ---------------
    Registry.register(Registries.BLOCK, HardenedGlassIdentifier, HardenedGlassBlock)
    Registry.register(Registries.ITEM, HardenedGlassIdentifier, HardenedGlassItem)

    // --------------- Test Block ---------------
    Registry.register(Registries.BLOCK, TestBlockIdentifier, TestBlockBlock)
    Registry.register(Registries.ITEM, TestBlockIdentifier, TestBlockItem)

    FabricDefaultAttributeRegistry.register(TestEntity, BaseTestEntity.createMobAttribute())

    FlammableBlockRegistry.getDefaultInstance().add(TestBlockBlock, 3, 2)

    ItemGroupEvents.modifyEntriesEvent(ItemGroups.COLORED_BLOCKS).register({content ->
      content.add(HardenedGlassItem)
      content.add(TestBlockItem)
    })
  }
}

// --------------- Test Entity ---------------
class BaseTestEntity(entityType: EntityType<out PathAwareEntity?>, world: World) : PathAwareEntity(entityType, world) {
  companion object {
    fun createMobAttribute(): DefaultAttributeContainer.Builder {
      return MobEntity.createMobAttributes()
        .add(EntityAttributes.GENERIC_MAX_HEALTH, 25.0)
        .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 2.0)
        .add(EntityAttributes.GENERIC_JUMP_STRENGTH, 2.0)
        .add(EntityAttributes.GENERIC_SCALE, 2.0)
    }
  }
}