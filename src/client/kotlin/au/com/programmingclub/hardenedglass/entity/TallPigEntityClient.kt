package au.com.programmingclub.hardenedglass.entity

import au.com.programmingclub.hardenedglass.namespace
import net.minecraft.client.model.Dilation
import net.minecraft.client.model.ModelData
import net.minecraft.client.model.ModelPart
import net.minecraft.client.model.ModelPartBuilder
import net.minecraft.client.model.ModelTransform
import net.minecraft.client.model.TexturedModelData
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.client.render.entity.feature.SaddleFeatureRenderer
import net.minecraft.client.render.entity.model.EntityModelPartNames
import net.minecraft.client.render.entity.model.QuadrupedEntityModel
import net.minecraft.util.Identifier

class TallPigEntityModel(root: ModelPart) :
    // TODO: Fix child head scale and position
    QuadrupedEntityModel<TallPigEntity>(root, false, 4.0F, 4.0F, 2.0F, 2.0F, 24) {
    companion object {
        private fun getTexturedModelData(dilation: Dilation): TexturedModelData {
            val modelData = ModelData()
            val modelPartData = modelData.root

            // Fix head angle when looking at player with food
            modelPartData.addChild(
                EntityModelPartNames.HEAD,
                ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, -13.0f, -8.0f, 8.0f, 8.0f, 8.0f, dilation)
                    .uv(16, 16).cuboid(-2.0f, -9.0f, -9.0f, 4.0f, 3.0f, 1.0f, Dilation.NONE),
                ModelTransform.pivot(0.0f, 12.0f, -6.0f)
            )

            modelPartData.addChild(
                EntityModelPartNames.BODY,
                ModelPartBuilder.create().uv(28, 8).cuboid(-5.0f, -10.0f, 2.0f, 10.0f, 16.0f, 8.0f, dilation),
                ModelTransform.of(0.0f, 11.0f, 2.0f, (Math.PI / 2).toFloat(), 0.0f, 0.0f)
            )

            val modelPartBuilder =
                ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 15.0f, 4.0f, dilation)
            modelPartData.addChild(
                EntityModelPartNames.RIGHT_HIND_LEG,
                modelPartBuilder,
                ModelTransform.pivot(-3.0f, 9.0f, 7.0f)
            )
            modelPartData.addChild(
                EntityModelPartNames.LEFT_HIND_LEG,
                modelPartBuilder,
                ModelTransform.pivot(3.0f, 9.0f, 7.0f)
            )
            modelPartData.addChild(
                EntityModelPartNames.RIGHT_FRONT_LEG,
                modelPartBuilder,
                ModelTransform.pivot(-3.0f, 9.0f, -5.0f)
            )
            modelPartData.addChild(
                EntityModelPartNames.LEFT_FRONT_LEG,
                modelPartBuilder,
                ModelTransform.pivot(3.0f, 9.0f, -5.0f)
            )

            return TexturedModelData.of(modelData, 64, 64)
        }

        fun getPigTexturedModelData(): TexturedModelData {
            return getTexturedModelData(Dilation.NONE)
        }

        fun getSaddleTexturedModelData(): TexturedModelData {
            return getTexturedModelData(Dilation(0.5f))
        }
    }
}

class TallPigEntityRenderer :
    MobEntityRenderer<TallPigEntity, TallPigEntityModel> {
    companion object {
        private val TEXTURE = Identifier(namespace, "textures/entity/tall_pig/tall_pig.png")
    }

    constructor(context: EntityRendererFactory.Context) : super(
        context,
        TallPigEntityModel(context.getPart(HardenedGlassModelLayers.TALL_PIG)),
        // Figure out this parameter
        0.5f
    ) {
        this.addFeature(
            SaddleFeatureRenderer(
                this,
                TallPigEntityModel(context.getPart(HardenedGlassModelLayers.TALL_PIG_SADDLE)),
                Identifier(namespace, "textures/entity/tall_pig/tall_pig_saddle.png")
            )
        )
    }

    override fun getTexture(entity: TallPigEntity): Identifier {
        return TEXTURE
    }
}
