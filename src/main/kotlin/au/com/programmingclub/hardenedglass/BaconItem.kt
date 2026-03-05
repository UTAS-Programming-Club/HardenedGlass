package au.com.programmingclub.hardenedglass

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.component.type.FoodComponent
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

private val BaconItemIdentifier = Identifier(namespace, "bacon")
private val BaconItemProperties: FoodComponent = FoodComponent.Builder()
    .nutrition(2)
    .snack()
    .alwaysEdible()
    .saturationModifier(2F)
    .statusEffect(StatusEffectInstance(StatusEffects.SPEED, 300, 2), 1f)
    .build()
private val BaconItem = Item(Item.Settings().food(BaconItemProperties))

fun registerBaconItem() {
    Registry.register(Registries.ITEM, BaconItemIdentifier, BaconItem)

    ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register { content ->
        content.add(BaconItem)
    }
}
