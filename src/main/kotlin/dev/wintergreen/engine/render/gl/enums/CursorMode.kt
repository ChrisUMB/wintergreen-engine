package dev.wintergreen.engine.render.gl.enums

import org.lwjgl.glfw.GLFW

enum class CursorMode(private val value: Int) : GLEnum {
    NORMAL(GLFW.GLFW_CURSOR_NORMAL),
    HIDDEN(GLFW.GLFW_CURSOR_HIDDEN),
    DISABLED(GLFW.GLFW_CURSOR_DISABLED);

    override fun value(): Int {
        return value
    }

}