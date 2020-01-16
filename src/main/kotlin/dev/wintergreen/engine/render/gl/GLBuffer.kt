package dev.wintergreen.engine.render.gl

import dev.wintergreen.engine.util.glCall
import org.lwjgl.opengl.GL15C.*

class GLBuffer(val target: Int = GL_ARRAY_BUFFER) {

    val id = glGenBuffers()

    init {
        bind()
    }

    fun data(data: FloatArray): GLBuffer {
        glCall { glBufferData(target, data, GL_STATIC_DRAW) }
        return this
    }

    fun data(data: IntArray): GLBuffer {
        glCall { glBufferData(target, data, GL_STATIC_DRAW) }
        return this
    }

    fun bind() {
        glCall { glBindBuffer(target, id) }
    }
}