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

////////////////// LONG BACON /////////////////////
private val LongBaconItemIdentifier = Identifier(namespace, "long_bacon")
private val LongBaconItemProperties: FoodComponent = FoodComponent.Builder()
    .nutrition(2)
    .snack()
    .alwaysEdible()
    .saturationModifier(2F)
    .statusEffect(StatusEffectInstance(StatusEffects.SPEED, 300, 2), 1f)
    .build()
private val LongBaconItem = Item(Item.Settings().food(LongBaconItemProperties))

// RAW //
private val RawLongBaconItemIdentifier = Identifier(namespace, "raw_long_bacon")
private val RawLongBaconItemProperties: FoodComponent = FoodComponent.Builder()
    .nutrition(2)
    .snack()
    .alwaysEdible()
    .saturationModifier(2F)
    .statusEffect(StatusEffectInstance(StatusEffects.SLOWNESS, 300, 2), 1f)
    .build()
private val RawLongBaconItem = Item(Item.Settings().food(RawLongBaconItemProperties))

////////////////// TALL BACON /////////////////////
private val TallBaconItemIdentifier = Identifier(namespace, "tall_bacon")
private val TallBaconItemProperties: FoodComponent = FoodComponent.Builder()
    .nutrition(2)
    .snack()
    .alwaysEdible()
    .saturationModifier(2F)
    .statusEffect(StatusEffectInstance(StatusEffects.JUMP_BOOST, 300, 2), 1f)
    .build()
private val TallBaconItem = Item(Item.Settings().food(TallBaconItemProperties))

// RAW //
private val RawTallBaconItemIdentifier = Identifier(namespace, "raw_tall_bacon")
private val RawTallBaconItemProperties: FoodComponent = FoodComponent.Builder()
    .nutrition(2)
    .snack()
    .alwaysEdible()
    .saturationModifier(2F)
    .statusEffect(StatusEffectInstance(StatusEffects.JUMP_BOOST, 300, 2), 1f)
    .build()
private val RawTallBaconItem = Item(Item.Settings().food(RawTallBaconItemProperties))

fun registerBaconItems() {
    Registry.register(Registries.ITEM, LongBaconItemIdentifier, LongBaconItem)
    Registry.register(Registries.ITEM, RawLongBaconItemIdentifier, RawLongBaconItem)
    Registry.register(Registries.ITEM, TallBaconItemIdentifier, TallBaconItem)
    Registry.register(Registries.ITEM, RawTallBaconItemIdentifier, RawTallBaconItem)

    ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register { content ->
        content.add(LongBaconItem)
        content.add(RawLongBaconItem)
        content.add(TallBaconItem)
        content.add(RawTallBaconItem)
    }
}
