package me.chrisumb.grids.input

enum class MouseButton(override val id: Int) : Button {
    UNKNOWN(-1), LEFT(0), RIGHT(1), MIDDLE(2), M4(3), M5(4), M6(5), M7(6);

    override val type: Button.Type?
        get() = Button.Type.MOUSE_BUTTON

    companion object {
        private val buttons =
            arrayOfNulls<MouseButton>(8)

        operator fun get(id: Int): MouseButton? {
            return buttons[id]
        }

        init {
            for (button in values()) {
                if (button.id < 0) continue
                buttons[button.id] = button
            }
        }
    }

}