package dev.wintergreen.engine.input

import me.chrisumb.grids.input.Button
import me.chrisumb.grids.input.ButtonState
import me.chrisumb.grids.input.MouseButton
import me.chrisumb.openmath.vector.Vector2f
import dev.wintergreen.engine.render.window.Window
import org.lwjgl.glfw.GLFW
import java.util.*

class Input(private val window: Window) {

    private val states: MutableMap<Button?, ButtonState> = HashMap()
    internal val _mousePosition = Vector2f()

    val mousePosition: Vector2f
        get() = _mousePosition

    var textBuffer = StringBuilder()
        private set

    var scrollDelta = 0f
        internal set

    var mouseMoved = false
        internal set

    operator fun get(button: Button): ButtonState {
        return states.getOrPut(button) { ButtonState(button) }
    }

    fun update() {
        textBuffer = StringBuilder()
        for (state in states.values) {
            state.update()
        }

        mouseMoved = false
        scrollDelta = 0f
    }
}