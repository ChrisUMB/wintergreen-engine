package dev.wintergreen.engine.render.gl.enums

import org.lwjgl.opengl.GL11C

enum class FrontFaceDirection(private val value: Int) : GLEnum {
    CLOCKWISE(GL11C.GL_CW),
    COUNTER_CLOCKWISE(GL11C.GL_CCW);

    override fun value(): Int {
        return value
    }

}