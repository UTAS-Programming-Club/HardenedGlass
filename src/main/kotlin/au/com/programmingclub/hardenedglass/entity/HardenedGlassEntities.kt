package au.com.programmingclub.hardenedglass.entity

import au.com.programmingclub.hardenedglass.namespace
import net.minecraft.entity.EntityType
import net.minecraft.entity.SpawnGroup
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object HardenedGlassEntities {
    val LONG_PIG_ID: Identifier = Identifier(namespace, "long_pig")
    val LONG_PIG: EntityType<LongPigEntity> = Registry.register(
        Registries.ENTITY_TYPE,
        LONG_PIG_ID,
        EntityType.Builder.create(::LongPigEntity, SpawnGroup.CREATURE).dimensions(1.5f, 0.75f).build()
    )

    val TALL_PIG_ID: Identifier = Identifier(namespace, "tall_pig")
    val TALL_PIG: EntityType<TallPigEntity> = Registry.register(
        Registries.ENTITY_TYPE,
        TALL_PIG_ID,
        EntityType.Builder.create(::TallPigEntity, SpawnGroup.CREATURE).dimensions(0.75f, 1.4f).build()
    )
}
