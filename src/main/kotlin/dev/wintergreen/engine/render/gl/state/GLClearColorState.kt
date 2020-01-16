package dev.wintergreen.engine.render.gl.state

import dev.wintergreen.engine.util.State
import dev.wintergreen.openmath.vector.Vector4f
import org.lwjgl.opengl.GL11C

class GLClearColorState : State<Vector4f> {
    override fun set(value: Vector4f) {
        GL11C.glClearColor(value.x, value.y, value.z, value.w)
    }

    override fun get(): Vector4f {
        throw UnsupportedOperationException("Cannot get clear color value.")
    }
}