package au.com.programmingclub.hardenedglass.entity

import au.com.programmingclub.hardenedglass.HardenedGlass
import net.minecraft.client.model.QuadrupedModel
import net.minecraft.client.model.geom.ModelPart
import net.minecraft.client.model.geom.PartNames
import net.minecraft.client.model.geom.PartPose
import net.minecraft.client.model.geom.builders.CubeDeformation
import net.minecraft.client.model.geom.builders.CubeListBuilder
import net.minecraft.client.model.geom.builders.LayerDefinition
import net.minecraft.client.model.geom.builders.MeshDefinition
import net.minecraft.client.model.geom.builders.PartDefinition
import net.minecraft.client.renderer.entity.EntityRendererProvider
import net.minecraft.client.renderer.entity.MobRenderer
import net.minecraft.client.renderer.entity.layers.SimpleEquipmentLayer
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState
import net.minecraft.client.resources.model.EquipmentClientInfo
import net.minecraft.resources.Identifier
import net.minecraft.world.entity.EquipmentSlot
import net.minecraft.world.item.ItemStack

class TallPigEntityModel(root: ModelPart) :
    // TODO: Fix child head scale and position
    QuadrupedModel<TallPigRenderState>(root) {
    // false, 4.0F, 4.0F, 2.0F, 2.0F, 24
    // boolean headScaled, float childHeadYOffset, float childHeadZOffset, float invertedChildHeadScale, float invertedChildBodyScale, int childBodyYOffset
    companion object {
        private fun getTexturedModelData(dilation: CubeDeformation): LayerDefinition {
            val modelData = MeshDefinition()
            val modelPartData: PartDefinition = modelData.root

            // Fix head angle when looking at player with food
            modelPartData.addOrReplaceChild(
                PartNames.HEAD,
                CubeListBuilder.create().texOffs(0, 0).addBox(-4.0f, -13.0f, -8.0f, 8.0f, 8.0f, 8.0f, dilation)
                    .texOffs(16, 16).addBox(-2.0f, -9.0f, -9.0f, 4.0f, 3.0f, 1.0f, CubeDeformation.NONE),
                PartPose.offset(0.0f, 12.0f, -6.0f)
            )

            modelPartData.addOrReplaceChild(
                PartNames.BODY,
                CubeListBuilder.create().texOffs(28, 8).addBox(-5.0f, -10.0f, 2.0f, 10.0f, 16.0f, 8.0f, dilation),
                PartPose.offsetAndRotation(0.0f, 11.0f, 2.0f, (Math.PI / 2).toFloat(), 0.0f, 0.0f)
            )

            val modelPartBuilder =
                CubeListBuilder.create().texOffs(0, 16).addBox(-2.0f, 0.0f, -2.0f, 4.0f, 15.0f, 4.0f, dilation)
            modelPartData.addOrReplaceChild(
                PartNames.RIGHT_HIND_LEG,
                modelPartBuilder,
                PartPose.offset(-3.0f, 9.0f, 7.0f)
            )
            modelPartData.addOrReplaceChild(
                PartNames.LEFT_HIND_LEG,
                modelPartBuilder,
                PartPose.offset(3.0f, 9.0f, 7.0f)
            )
            modelPartData.addOrReplaceChild(
                PartNames.RIGHT_FRONT_LEG,
                modelPartBuilder,
                PartPose.offset(-3.0f, 9.0f, -5.0f)
            )
            modelPartData.addOrReplaceChild(
                PartNames.LEFT_FRONT_LEG,
                modelPartBuilder,
                PartPose.offset(3.0f, 9.0f, -5.0f)
            )

            return LayerDefinition.create(modelData, 64, 64)
        }

        fun getPigTexturedModelData(): LayerDefinition {
            return getTexturedModelData(CubeDeformation.NONE)
        }

        fun getSaddleTexturedModelData(): LayerDefinition {
            return getTexturedModelData(CubeDeformation(0.5f))
        }
    }
}

class TallPigRenderState : LivingEntityRenderState() {
    var saddle: ItemStack = ItemStack.EMPTY
}

/*class TallPigEntityRenderer :
    MobRenderer<TallPigEntity, TallPigRenderState, TallPigEntityModel> {
    companion object {
        private val TEXTURE = HardenedGlass.id("textures/entity/tall_pig/tall_pig.png")
    }

    constructor(context: EntityRendererProvider.Context) : super(
        context,
        TallPigEntityModel(context.bakeLayer(HardenedGlassModelLayers.TALL_PIG)),
        // Figure out this parameter
        0.5f
    ) {
        this.addLayer(
            SimpleEquipmentLayer(
                this,
                context.equipmentRenderer,
                EquipmentClientInfo.LayerType.PIG_SADDLE,
                { state -> state.saddle },
                TallPigEntityModel(context.bakeLayer(HardenedGlassModelLayers.TALL_PIG_SADDLE)),
                null
            )
            // TODO: Figure out where to give the file path: HardenedGlass.id("textures/entity/tall_pig/tall_pig_saddle.png")
        )
    }

    override fun getTextureLocation(state: TallPigRenderState): Identifier {
        return TEXTURE
    }

    override fun createRenderState(): TallPigRenderState {
        return TallPigRenderState()
    }

    override fun extractRenderState(entity: TallPigEntity, state: TallPigRenderState, partialTicks: Float) {
        super.extractRenderState(entity, state, partialTicks)
        state.saddle = entity.getItemBySlot(EquipmentSlot.SADDLE).copy()
    }
}*/
