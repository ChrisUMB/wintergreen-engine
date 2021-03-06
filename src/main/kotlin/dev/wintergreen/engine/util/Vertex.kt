package dev.wintergreen.engine.util

import dev.wintergreen.openmath.vector.Vector2f
import dev.wintergreen.openmath.vector.Vector3f

data class Vertex(
    val position: Vector3f,
    val texCoord: Vector2f,
    val normal: Vector3f
) {
    var tangent = Vector3f()

    val floatArray = position.floatArray + texCoord.floatArray + normal.floatArray + tangent.floatArray
}