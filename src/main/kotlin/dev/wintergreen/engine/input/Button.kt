package me.chrisumb.grids.input

interface Button {
    val id: Int
    val type: Type?

    enum class Type {
        KEY, MOUSE_BUTTON, CONTROLLER
    }
}