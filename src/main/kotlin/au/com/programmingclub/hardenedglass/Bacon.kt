package au.com.programmingclub.hardenedglass

import au.com.programmingclub.hardenedglass.HardenedGlass.namespace
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.component.type.FoodComponent
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

class Bacon {
    companion object{
        private val BaconIdentifier = Identifier(namespace, "bacon")
        val BaconProperties: FoodComponent = FoodComponent.Builder()
            .nutrition(2)
            .snack()
            .alwaysEdible()
            .saturationModifier(2F)
            .statusEffect(StatusEffectInstance(StatusEffects.SPEED, 300, 2), 1f)
            .build()
        val BaconItem = Item(Item.Settings().food(BaconProperties))

        fun registerItem(){
            Registry.register(Registries.ITEM, BaconIdentifier, BaconItem)

            ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register { content ->
                content.add(BaconItem)
            }
        }
    }
}