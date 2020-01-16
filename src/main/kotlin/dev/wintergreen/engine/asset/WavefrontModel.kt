package dev.wintergreen.engine.asset

import dev.wintergreen.engine.render.gl.GLBuffer
import dev.wintergreen.engine.render.gl.GLType
import dev.wintergreen.engine.render.gl.VertexArray
import dev.wintergreen.engine.util.glCall
import org.lwjgl.opengl.GL11C

class WavefrontModel(private val ibo: GLBuffer, private val vbo: GLBuffer, private val vertexCount: Int) {

    fun bind() {
        vao.bind()
        vbo.bind()
        vao.buffer = vbo
        ibo.bind()
    }

    fun draw() {
        bind()
        glCall { vao.drawElements(vertexCount, GL11C.GL_TRIANGLES, ibo) }
    }

    companion object {

        private val cache = HashMap<Asset, WavefrontModel>()

        operator fun get(asset: Asset): WavefrontModel? {
            return cache.getOrPut(asset) {
                WavefrontLoader.parse(asset)
            }
        }

        private val vao = VertexArray(GLType.FLOAT * 3, GLType.FLOAT * 2, GLType.FLOAT * 3, GLType.FLOAT * 3)

    }
}