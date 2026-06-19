package au.com.programmingclub.hardenedglass.entity

import net.minecraft.entity.EntityType
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.passive.PassiveEntity
import net.minecraft.entity.passive.PigEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.world.World

// --------------- Test Entity ---------------
// TODO: Change objectives or breeding foods?
class LongPigEntity(entityType: EntityType<LongPigEntity>, world: World) : PigEntity(entityType, world) {
    companion object {
        // TODO: Add additional difference to vanilla pig?
        fun createMobAttribute(): DefaultAttributeContainer.Builder {
            return createPigAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, .3)
        }
    }

    override fun createChild(serverWorld: ServerWorld, passiveEntity: PassiveEntity): LongPigEntity? {
        return HardenedGlassEntities.LONG_PIG.create(serverWorld)
    }
}
