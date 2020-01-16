package dev.wintergreen.engine.render

import me.chrisumb.openmath.matrix.Matrix4f
import me.chrisumb.openmath.quaternion.Quaternionf
import me.chrisumb.openmath.vector.Vector3f

class Camera(
    //TODO: Potentially abstract in a way where you could do something like cameras.find { it.projection is Perspective }
    val projection: Matrix4f,
    val position: Vector3f = Vector3f(),
    val rotation: Quaternionf = Quaternionf()
) {

    companion object {
        fun perspective(
            target: RenderTarget,
            fov: Float = 80f,
            zNear: Float = 0.01f,
            zFar: Float = 1000f
        ): Camera {
            return Camera(Matrix4f.perspective(target.resolution.aspectRatio, fov, zNear, zFar))
        }

        /**
         * Defaults to Bottom Left as (0f, 0f) to Top Right as (1f, 1f)
         */
        fun orthographic(
            target: RenderTarget,
            top: Float = 1f,
            bottom: Float = 0f,
            left: Float = 0f,
            right: Float = 1f,
            near: Float = 0.01f,
            far: Float = 1000f
        ): Camera {
            return Camera(Matrix4f.orthographic(left, right, bottom, top, near, far))
        }
    }

}