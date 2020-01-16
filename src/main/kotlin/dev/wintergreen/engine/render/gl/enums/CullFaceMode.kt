package dev.wintergreen.engine.render.gl.enums

import org.lwjgl.opengl.GL11C

enum class CullFaceMode(private val value: Int) : GLEnum {
    FRONT(GL11C.GL_FRONT),
    BACK(GL11C.GL_BACK),
    FRONT_AND_BACK(GL11C.GL_FRONT_AND_BACK);

    override fun value(): Int {
        return value
    }

}