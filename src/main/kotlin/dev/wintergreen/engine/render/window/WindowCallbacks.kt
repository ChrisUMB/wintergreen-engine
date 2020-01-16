package dev.wintergreen.engine.render.window

import me.chrisumb.grids.input.MouseButton
import dev.wintergreen.engine.input.Key
import org.lwjgl.glfw.GLFW

internal object WindowCallbacks {

    fun setSizeCallback(id: Long, width: Int, height: Int) {
        val window = getWindow(id) ?: return
        window.resolution = Resolution(width, height)
    }

    fun setKeyCallback(id: Long, key: Int, scancode: Int, action: Int, mods: Int) {
        val window = getWindow(id) ?: return

        if (action != GLFW.GLFW_PRESS && action != GLFW.GLFW_RELEASE) return
        val state = window.input[Key[key] ?: return]
        when (action) {
            GLFW.GLFW_PRESS -> state.pressed()
            GLFW.GLFW_RELEASE -> state.released()
        }
    }

    fun setMouseButtonCallback(id: Long, button: Int, action: Int, mods: Int) {
        val window = getWindow(id) ?: return
        if (action != GLFW.GLFW_PRESS && action != GLFW.GLFW_RELEASE) return
        val state = window.input[MouseButton[button] ?: return]
        when (action) {
            GLFW.GLFW_PRESS -> state.pressed()
            GLFW.GLFW_RELEASE -> state.released()
        }
    }

    fun setCursorPositionCallback(id: Long, x: Double, y: Double) {
        val window = getWindow(id) ?: return

        window.input._mousePosition.x = x.toFloat()
        window.input._mousePosition.y = y.toFloat()
        window.input.mouseMoved = true
    }

    fun setScrollCallback(id: Long, x: Double, y: Double) {
        getWindow(id)?.input?.scrollDelta = y.toFloat()
    }

    fun setCharCallback(id: Long, codePoint: Int) {
        getWindow(id)?.input?.textBuffer?.appendCodePoint(codePoint)
    }

    fun setFocusCallback(window: Long, focused: Boolean) {

    }

    fun setPosCallback(window: Long, x: Int, y: Int) {

    }

    fun setCloseCallback(window: Long) {

    }

    fun getWindow(id: Long): Window? {
        return Window.values.find { it.id == id }
    }
}