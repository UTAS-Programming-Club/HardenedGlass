package au.com.programmingclub.hardenedglass

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents
import net.minecraft.core.Registry
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.core.registries.Registries
import net.minecraft.resources.ResourceKey
import net.minecraft.world.effect.MobEffectInstance
import net.minecraft.world.effect.MobEffects
import net.minecraft.world.food.FoodProperties
import net.minecraft.world.item.CreativeModeTabs
import net.minecraft.world.item.Item
import net.minecraft.world.item.Item.Properties
import net.minecraft.world.item.component.Consumables
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect

data object BaconItems {
    fun init() {
        // TODO: Move to separate group, find better method
        CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS).register { content ->
            content.accept(LONG_BACON)
            content.accept(RAW_LONG_BACON)
            content.accept(TALL_BACON)
            content.accept(RAW_TALL_BACON)
        }
    }

    @JvmField
    val LONG_BACON: Item = registerItem(
        Properties().food(
            FoodProperties.Builder()
                .nutrition(2)
                .alwaysEdible()
                .saturationModifier(2F)
                .build(),
            Consumables.defaultFood()
                // TODO: figure out matching speed for .consumeSeconds() to match removed .fast()
                .onConsume(ApplyStatusEffectsConsumeEffect(
                    MobEffectInstance(MobEffects.SPEED, 300, 2), 1f)
                )
                .build()
        ),
        "long_bacon",
    )

    @JvmField
    val RAW_LONG_BACON: Item = registerItem(
        Properties().food(
            FoodProperties.Builder()
                .nutrition(2)
                .alwaysEdible()
                .saturationModifier(2F)
                .build(),
            Consumables.defaultFood()
                // TODO: figure out matching speed for .consumeSeconds() to match removed .fast()
                .onConsume(ApplyStatusEffectsConsumeEffect(
                    MobEffectInstance(MobEffects.SLOWNESS, 300, 2), 1f)
                )
                .build()
        ),
        "raw_long_bacon",
    )

    @JvmField
    val TALL_BACON: Item = registerItem(
        Properties().food(
            FoodProperties.Builder()
                .nutrition(2)
                .alwaysEdible()
                .saturationModifier(2F)
                .build(),
            Consumables.defaultFood()
                // TODO: figure out matching speed for .consumeSeconds() to match removed .fast()
                .onConsume(ApplyStatusEffectsConsumeEffect(
                    MobEffectInstance(MobEffects.JUMP_BOOST, 300, 2), 1f)
                )
                .build()
        ),
        "tall_bacon",
    )

    // TODO: Use a different effect than cooked tall bacon
    @JvmField
    val RAW_TALL_BACON: Item = registerItem(
        Properties().food(
            FoodProperties.Builder()
                .nutrition(2)
                .alwaysEdible()
                .saturationModifier(2F)
                .build(),
            Consumables.defaultFood()
                // TODO: figure out matching speed for .consumeSeconds() to match removed .fast()
                .onConsume(ApplyStatusEffectsConsumeEffect(
                    MobEffectInstance(MobEffects.JUMP_BOOST, 300, 2), 1f)
                )
                .build()
        ),
        "raw_tall_bacon",
    )

    fun registerItem(properties: Properties, identifier: String): Item {
        val key: ResourceKey<Item> = ResourceKey.create(Registries.ITEM, HardenedGlass.id(identifier))
        val item = Item(properties.setId(key))
        return Registry.register(BuiltInRegistries.ITEM, key, item)
    }

}
