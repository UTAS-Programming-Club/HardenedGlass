package au.com.programmingclub.hardenedglass.entity

import net.minecraft.server.level.ServerLevel
import net.minecraft.world.entity.AgeableMob
import net.minecraft.world.entity.EntitySpawnReason
import net.minecraft.world.entity.EntityType
import net.minecraft.world.entity.ai.attributes.AttributeSupplier
import net.minecraft.world.entity.ai.attributes.Attributes
import net.minecraft.world.entity.animal.pig.Pig
import net.minecraft.world.level.Level

// --------------- Test Entity ---------------
// TODO: Change objectives or breeding foods?
class TallPigEntity(entityType: EntityType<TallPigEntity>, level: Level) : Pig(entityType, level) {
    companion object {
        // TODO: Add additional difference to vanilla pig?
        fun createMobAttribute(): AttributeSupplier.Builder {
            return createAttributes().add(Attributes.JUMP_STRENGTH, .7)
        }
    }

    override fun getBreedOffspring(serverLevel: ServerLevel, passiveEntity: AgeableMob): TallPigEntity? {
        return HardenedGlassEntities.TALL_PIG.create(serverLevel, EntitySpawnReason.BREEDING)
    }
}
