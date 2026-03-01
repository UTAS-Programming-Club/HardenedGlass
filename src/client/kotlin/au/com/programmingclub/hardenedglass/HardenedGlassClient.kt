package au.com.programmingclub.hardenedglass

import au.com.programmingclub.hardenedglass.HardenedGlass.namespace
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry
import net.minecraft.client.model.*
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.VertexConsumer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.client.render.entity.model.EntityModel
import net.minecraft.client.render.entity.model.EntityModelLayer
import net.minecraft.client.render.entity.model.EntityModelPartNames
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier
import net.minecraft.world.gen.densityfunction.DensityFunction


@Environment(EnvType.CLIENT)
object HardenedGlassClient : ClientModInitializer {
  val MODEL_CUBE_LAYER: EntityModelLayer = EntityModelLayer(Identifier.of(HardenedGlass.namespace, "cube"), "main")

  override fun onInitializeClient() {
    // This entrypoint is suitable for setting up client-specific logic, such as rendering.
    BlockRenderLayerMap.INSTANCE.putBlock(HardenedGlass.HardenedGlassBlock, RenderLayer.getCutout())

    EntityRendererRegistry.register(HardenedGlass.TestEntity, { context -> TestEntityRenderer(context) })

    // In 1.17, use EntityRendererRegistry.register (seen below) instead of EntityRendererRegistry.INSTANCE.register (seen above)
    EntityRendererRegistry.register<BaseTestEntity?>(
      HardenedGlass.TestEntity,
      EntityRendererFactory { context: EntityRendererFactory.Context? -> TestEntityRenderer(context!!) })

    EntityModelLayerRegistry.registerModelLayer(MODEL_CUBE_LAYER, TestEntityModel::getTexturedModelData)
  }
}

class TestEntityModel(modelPart : ModelPart) : EntityModel<BaseTestEntity>() {

  private val base: ModelPart =  modelPart.getChild(EntityModelPartNames.CUBE)

  companion object {
    fun getTexturedModelData(): TexturedModelData {
      val modelData = ModelData()
      val modelPartData = modelData.root
      modelPartData.addChild(
        EntityModelPartNames.CUBE,
        ModelPartBuilder.create().uv(0, 0).cuboid(-6f, 12f, -6f, 12f, 12f, 12f),
        ModelTransform.pivot(0f, 0f, 0f)
      )
      return TexturedModelData.of(modelData, 64, 64)
    }
  }

  override fun setAngles(
    entity: BaseTestEntity,
    limbAngle: Float,
    limbDistance: Float,
    animationProgress: Float,
    headYaw: Float,
    headPitch: Float
  ) {
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
    listOf(base).forEach { modelRenderer ->
      modelRenderer.render(matrices, vertices, light, overlay, red, green, blue, alpha)
    }
  }
}

class TestEntityRenderer(context: EntityRendererFactory.Context) : MobEntityRenderer<BaseTestEntity, TestEntityModel>(
  context,
  TestEntityModel(context.getPart(HardenedGlassClient.MODEL_CUBE_LAYER)),
  0.5f
) {
  public override fun getTexture(entity: BaseTestEntity?): Identifier? {
    return Identifier.of(namespace, "textures/entity/test/cube.png")
  }
}