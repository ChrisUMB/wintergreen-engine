package dev.wintergreen.engine.render.gl.state

import dev.wintergreen.engine.util.State
import org.lwjgl.opengl.GL11C

class GLEnableState(private val cap: Int) : State<Boolean> {
    override fun set(value: Boolean) {
        if (value) {
            GL11C.glEnable(cap)
        } else {
            GL11C.glDisable(cap)
        }
    }

    override fun get(): Boolean {
        return GL11C.glIsEnabled(cap)
    }

}