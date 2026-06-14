package au.com.programmingclub.hardenedglass

import net.minecraft.client.model.Dilation
import net.minecraft.client.model.ModelData
import net.minecraft.client.model.ModelPart
import net.minecraft.client.model.ModelPartBuilder
import net.minecraft.client.model.ModelPartData
import net.minecraft.client.model.ModelTransform
import net.minecraft.client.model.TexturedModelData
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.client.render.entity.model.EntityModel
import net.minecraft.client.render.entity.model.SinglePartEntityModel
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier
import net.minecraft.util.math.MathHelper

class TallPigEntityModel(val modelPart : ModelPart) : SinglePartEntityModel<BaseTallPigEntity>() {

    companion object {
        fun getTexturedModelData(): TexturedModelData {
            val modelData = ModelData()
            val modelPartData = modelData.root
            val body: ModelPartData? = modelPartData.addChild(
                "body",
                ModelPartBuilder.create().uv(28, 8)
                    .cuboid(-5.0f, -10.0f, 2f, 10.0f, 16.0f, 8.0f, Dilation(0.0f)),
                ModelTransform.of(0.0f, 11.0f, 2f, 1.5708f, 0.0f, 0.0f)
            )

            val head: ModelPartData? = modelPartData.addChild(
                "head",
                ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, -13f, -8.0f, 8.0f, 8.0f, 8.0f, Dilation(0.0f))
                    .uv(16, 16).cuboid(-2.0f, -9f, -9.0f, 4.0f, 3.0f, 1.0f, Dilation(0.0f)),
                ModelTransform.pivot(0.0f, 12.0f, -6.0f)
            )

            val leg1: ModelPartData? = modelPartData.addChild(
                "leg1",
                ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, -9.0f, -2.0f, 4.0f, 15.0f, 4.0f, Dilation(0.0f)),
                ModelTransform.pivot(-3.0f, 18.0f, 7.0f)
            )

            val leg2: ModelPartData? = modelPartData.addChild(
                "leg2",
                ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, -9.0f, -2.0f, 4.0f, 15.0f, 4.0f, Dilation(0.0f)),
                ModelTransform.pivot(3.0f, 18.0f, 7.0f)
            )

            val leg3: ModelPartData? = modelPartData.addChild(
                "leg3",
                ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, -9.0f, -2.0f, 4.0f, 15.0f, 4.0f, Dilation(0.0f)),
                ModelTransform.pivot(-3.0f, 18.0f, -5.0f)
            )

            val leg4: ModelPartData? = modelPartData.addChild(
                "leg4",
                ModelPartBuilder.create().uv(0, 16).cuboid(-2.0f, -9.0f, -2.0f, 4.0f, 15.0f, 4.0f, Dilation(0.0f)),
                ModelTransform.pivot(3.0f, 18.0f, -5f)
            )
            return TexturedModelData.of(modelData, 64, 64)
        }
    }

    override fun render(
        matrices: MatrixStack,
        vertices: VertexConsumer,
        light: Int,
        overlay: Int,
        red: Float,
        green: Float,
        blue: Float,
        alpha: Float
    ) {
        val body : ModelPart = modelPart.getChild("body")
        val head : ModelPart = modelPart.getChild("head")
        val leg1 : ModelPart = modelPart.getChild("leg1")
        val leg2 : ModelPart = modelPart.getChild("leg2")
        val leg3 : ModelPart = modelPart.getChild("leg3")
        val leg4 : ModelPart = modelPart.getChild("leg4")

        body.render(matrices, vertices, light, overlay, red, green, blue, alpha)
        head.render(matrices, vertices, light, overlay, red, green, blue, alpha)
        leg1.render(matrices, vertices, light, overlay, red, green, blue, alpha)
        leg2.render(matrices, vertices, light, overlay, red, green, blue, alpha)
        leg3.render(matrices, vertices, light, overlay, red, green, blue, alpha)
        leg4.render(matrices, vertices, light, overlay, red, green, blue, alpha)
    }

    override fun getPart(): ModelPart? {
        return modelPart.getChild("body")
    }

    override fun setAngles(
        entity: BaseTallPigEntity?,
        limbAngle: Float,
        limbDistance: Float,
        animationProgress: Float,
        headYaw: Float,
        headPitch: Float
    ) {
        modelPart.traverse().forEach { modelPart -> modelPart.resetTransform() }

        this.animateMovement(
            walk_tall_pig,
            limbAngle,
            limbDistance,
            animationProgress,
            2.5f
        )

        this.updateAnimation(entity?.idleAnimState, idle_tall_pig, animationProgress, 1f)
    }
}

class TallPigEntityRenderer(context: EntityRendererFactory.Context) : MobEntityRenderer<BaseTallPigEntity, TallPigEntityModel>(
    context,
    TallPigEntityModel(context.getPart(HardenedGlassClient.MODEL_TALL_PIG_LAYER)),
    0.5f
) {
    override fun getTexture(entity: BaseTallPigEntity?): Identifier? {
        return Identifier.of(namespace, "textures/entity/tall_pig/tall_pig.png")
    }
}