package au.com.programmingclub.hardenedglass

import net.minecraft.client.render.entity.animation.Animation
import net.minecraft.client.render.entity.animation.AnimationHelper
import net.minecraft.client.render.entity.animation.Keyframe
import net.minecraft.client.render.entity.animation.Transformation

// Save this class in your mod and generate all required imports
/**
 * Made with Blockbench 5.1.4
 * Exported for Minecraft version 1.19 or later with Yarn mappings
 * @author Author
 */
val walk_tall_pig: Animation = Animation.Builder.create(0.9583f).looping()
    .addBoneAnimation(
        "leg1", Transformation(
            Transformation.Targets.ROTATE,
            Keyframe(
                0.0f,
                AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                Transformation.Interpolations.LINEAR
            ),
            Keyframe(
                0.2083f,
                AnimationHelper.createRotationalVector(25.0f, 0.0f, 0.0f),
                Transformation.Interpolations.LINEAR
            ),
            Keyframe(
                0.4583f,
                AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                Transformation.Interpolations.LINEAR
            ),
            Keyframe(
                0.7083f,
                AnimationHelper.createRotationalVector(-35.0f, 0.0f, 0.0f),
                Transformation.Interpolations.LINEAR
            ),
            Keyframe(
                0.9583f,
                AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                Transformation.Interpolations.LINEAR
            )
        )
    )
    .addBoneAnimation(
        "leg2", Transformation(
            Transformation.Targets.ROTATE,
            Keyframe(
                0.0f,
                AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                Transformation.Interpolations.LINEAR
            ),
            Keyframe(
                0.2083f,
                AnimationHelper.createRotationalVector(-30.0f, 0.0f, 0.0f),
                Transformation.Interpolations.LINEAR
            ),
            Keyframe(
                0.4583f,
                AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                Transformation.Interpolations.LINEAR
            ),
            Keyframe(
                0.7083f,
                AnimationHelper.createRotationalVector(42.5f, 0.0f, 0.0f),
                Transformation.Interpolations.LINEAR
            ),
            Keyframe(
                0.9583f,
                AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                Transformation.Interpolations.LINEAR
            )
        )
    )
    .addBoneAnimation(
        "leg3", Transformation(
            Transformation.Targets.ROTATE,
            Keyframe(
                0.0f,
                AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                Transformation.Interpolations.LINEAR
            ),
            Keyframe(
                0.2083f,
                AnimationHelper.createRotationalVector(27.5f, 0.0f, 0.0f),
                Transformation.Interpolations.LINEAR
            ),
            Keyframe(
                0.4583f,
                AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                Transformation.Interpolations.LINEAR
            ),
            Keyframe(
                0.7083f,
                AnimationHelper.createRotationalVector(-37.5f, 0.0f, 0.0f),
                Transformation.Interpolations.LINEAR
            ),
            Keyframe(
                0.9583f,
                AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                Transformation.Interpolations.LINEAR
            )
        )
    )
    .addBoneAnimation(
        "leg4", Transformation(
            Transformation.Targets.ROTATE,
            Keyframe(
                0.0f,
                AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                Transformation.Interpolations.LINEAR
            ),
            Keyframe(
                0.2083f,
                AnimationHelper.createRotationalVector(-25.0f, 0.0f, 0.0f),
                Transformation.Interpolations.LINEAR
            ),
            Keyframe(
                0.4583f,
                AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                Transformation.Interpolations.LINEAR
            ),
            Keyframe(
                0.7083f,
                AnimationHelper.createRotationalVector(32.5f, 0.0f, 0.0f),
                Transformation.Interpolations.LINEAR
            ),
            Keyframe(
                0.9583f,
                AnimationHelper.createRotationalVector(0.0f, 0.0f, 0.0f),
                Transformation.Interpolations.LINEAR
            )
        )
    )
    .build()

val idle_tall_pig: Animation? = Animation.Builder.create(0.0f).looping()

    .build()