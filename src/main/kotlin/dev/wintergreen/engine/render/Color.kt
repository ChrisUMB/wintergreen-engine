package dev.wintergreen.engine.render

import me.chrisumb.openmath.vector.Vector3f
import me.chrisumb.openmath.vector.Vector4f


data class Color(var red: Float, var green: Float, var blue: Float, var alpha: Float = 1f) {
    constructor(vector: Vector4f) : this(vector.x, vector.y, vector.z, vector.w)
    constructor(vector: Vector3f) : this(vector.x, vector.y, vector.z)
    constructor(vector: Vector3f, alpha: Float) : this(vector.x, vector.y, vector.z, alpha)

    constructor(red: Int, green: Int, blue: Int, alpha: Int = 255) : this(
        red / 255f,
        green / 255f,
        blue / 255f,
        alpha / 255f
    )

    constructor(rgba: Int) : this(
        rgba shr 24 and 0xFF,
        rgba shr 16 and 0xFF,
        rgba shr 8 and 0xFF,
        rgba and 0xFF
    )

    fun toVector3f() = Vector3f(red, green, blue)
    fun toVector4f() = Vector4f(red, green, blue, alpha)

    companion object {
        fun hsb(hue: Float, saturation: Float, brightness: Float): Color {
            val hsbColor = java.awt.Color.getHSBColor(hue / 360, saturation, brightness)
            return Color(hsbColor.red, hsbColor.green, hsbColor.blue, hsbColor.alpha)
        }
    }
}