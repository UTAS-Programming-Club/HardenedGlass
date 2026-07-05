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

data object BaconItems {
    fun init() {
        // SANITY(unusual):
        // Empty function in order to call initialiser of object.
        // The Fabric API will reflect through all variables in this object and register them for us.
    }

    @JvmField
    val LONG_BACON: Item = registerItem(
        Item(
            Item.Settings().food(
                FoodComponent.Builder()
                    .nutrition(2)
                    .snack()
                    .alwaysEdible()
                    .saturationModifier(2F)
                    .statusEffect(StatusEffectInstance(StatusEffects.SPEED, 300, 2), 1f)
                    .build()
            ),
        ),
        "long_bacon",
    )

    @JvmField
    val RAW_LONG_BACON: Item = registerItem(
        Item(
            Item.Settings().food(
                FoodComponent.Builder()
                    .nutrition(2)
                    .snack()
                    .alwaysEdible()
                    .saturationModifier(2F)
                    .statusEffect(StatusEffectInstance(StatusEffects.SLOWNESS, 300, 2), 1f)
                    .build(),
            ),
        ),
        "raw_long_bacon",
    )

    @JvmField
    val TALL_BACON: Item = registerItem(
        Item(
            Item.Settings().food(
                FoodComponent.Builder()
                    .nutrition(2)
                    .snack()
                    .alwaysEdible()
                    .saturationModifier(2F)
                    .statusEffect(StatusEffectInstance(StatusEffects.JUMP_BOOST, 300, 2), 1f)
                    .build(),
            ),
        ),
        "tall_bacon",
    )

    @JvmField
    val RAW_TALL_BACON: Item = registerItem(
        Item(
            Item.Settings().food(
                FoodComponent.Builder()
                    .nutrition(2)
                    .snack()
                    .alwaysEdible()
                    .saturationModifier(2F)
                    .statusEffect(StatusEffectInstance(StatusEffects.JUMP_BOOST, 300, 2), 1f)
                    .build(),
            ),
        ),
        "raw_tall_bacon",
    )

    fun <T : Item> registerItem(item: T, identifier: String): T {
        return Registry.register(Registries.ITEM, Identifier(MOD_ID, identifier), item)
    }
}