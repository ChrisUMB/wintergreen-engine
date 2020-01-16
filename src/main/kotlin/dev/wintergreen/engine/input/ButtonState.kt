package me.chrisumb.grids.input

import me.chrisumb.grids.input.Button

class ButtonState internal constructor(val button: Button?) {
    var isPressed = false
        private set
    var isHeld = false
        private set
    var isReleased = false
        private set
    var pressedStartTime = 0L
        private set

    val heldTime: Float
        get() = if (pressedStartTime == 0L) 0f else (System.currentTimeMillis() - pressedStartTime) / 1000f

    fun pressed() {
        pressedStartTime = System.currentTimeMillis()
        isHeld = true
        isPressed = true
    }

    fun released() {
        isHeld = false
        isReleased = true
        pressedStartTime = 0L
    }

    fun update() {
        isPressed = false
        isReleased = false
    }

    override fun toString(): String {
        return "ButtonState{" +
                "button=" + button +
                ", pressed=" + isPressed +
                ", held=" + isHeld +
                ", released=" + isReleased +
                '}'
    }

}