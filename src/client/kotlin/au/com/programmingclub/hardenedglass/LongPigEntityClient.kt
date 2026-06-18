package au.com.programmingclub.hardenedglass

import net.minecraft.client.model.*
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.client.render.entity.model.EntityModel
import net.minecraft.client.render.entity.model.EntityModelPartNames
import net.minecraft.client.render.entity.model.PigEntityModel
import net.minecraft.client.render.entity.model.QuadrupedEntityModel
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier

// CONVERTED FROM BLOCK BENCH MODEL EXPORT USING JAVAINUSE
class LongPigEntityModel(root: ModelPart) :
    QuadrupedEntityModel<BaseLongPigEntity>(root, false, 4.0F, 4.0F, 2.0F, 2.0F, 24) {

    companion object {
        fun getTexturedModelData(): TexturedModelData {
            val modelData = ModelData()
            val modelPartData = modelData.root

            modelPartData.addChild(
                EntityModelPartNames.HEAD,
                ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, -4.0f, -8.0f, 8.0f, 8.0f, 8.0f, Dilation.NONE)
                    .uv(16, 16).cuboid(-2.0f, 0.0f, -9.0f, 4.0f, 3.0f, 1.0f, Dilation.NONE),
                ModelTransform.pivot(0.0f, 12.0f, -15.0f)
            )

            modelPartData.addChild(
                EntityModelPartNames.BODY,
                ModelPartBuilder.create().uv(28, 8).cuboid(-5.0f, -10.0f, -7.0f, 10.0f, 32.0f, 8.0f, Dilation.NONE),
                ModelTransform.of(0.0f, 11.0f, -7.0f, 1.5708f, 0.0f, 0.0f)
            )

            val modelPartBuilder =
                ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 6.0f, 4.0f, Dilation.NONE)
            modelPartData.addChild(
                EntityModelPartNames.RIGHT_HIND_LEG,
                modelPartBuilder,
                ModelTransform.pivot(-3.0f, 18.0f, 14.0f)
            )
            modelPartData.addChild(
                EntityModelPartNames.LEFT_HIND_LEG,
                modelPartBuilder,
                ModelTransform.pivot(3.0f, 18.0f, 14.0f)
            )
            modelPartData.addChild(
                EntityModelPartNames.RIGHT_FRONT_LEG,
                modelPartBuilder,
                ModelTransform.pivot(-3.0f, 18.0f, -12.0f)
            )
            modelPartData.addChild(
                EntityModelPartNames.LEFT_FRONT_LEG,
                modelPartBuilder,
                ModelTransform.pivot(3.0f, 18.0f, -12.0f)
            )

            return TexturedModelData.of(modelData, 64, 64)
        }
    }
}

class LongPigEntityRenderer(context: EntityRendererFactory.Context) :
    MobEntityRenderer<BaseLongPigEntity, LongPigEntityModel>(
        context,
        LongPigEntityModel(context.getPart(HardenedGlassClient.MODEL_LONG_PIG_LAYER)),
        0.5f
    ) {
    override fun getTexture(entity: BaseLongPigEntity?): Identifier? {
        return Identifier.of(namespace, "textures/entity/long_pig/long_pig.png")
    }
}