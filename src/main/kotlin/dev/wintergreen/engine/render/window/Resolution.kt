package dev.wintergreen.engine.render.window

import dev.wintergreen.openmath.vector.Vector2f

data class Resolution(
    val width: Int,
    val height: Int
) {

    val aspectRatio = width.toFloat() / height.toFloat()

    val totalPixels = width * height

    val vector = Vector2f(width.toFloat(), height.toFloat())

    operator fun compareTo(other: Resolution): Int {
        return totalPixels.compareTo(other.totalPixels)
    }

    override fun toString(): String {
        return "Resolution(width=$width, height=$height, aspectRatio=$aspectRatio)"
    }
}