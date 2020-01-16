package dev.wintergreen.engine.render.gl

import dev.wintergreen.engine.util.glCall
import org.lwjgl.opengl.GL11.glDrawArrays
import org.lwjgl.opengl.GL11C
import org.lwjgl.opengl.GL20C.glEnableVertexAttribArray
import org.lwjgl.opengl.GL30C.glBindVertexArray
import org.lwjgl.opengl.GL30C.glGenVertexArrays
import org.lwjgl.opengl.GL43C.*

class VertexArray(vararg attributes: Attribute) {

    data class Attribute(
        val type: GLType,
        val count: Int,
        val normalized: Boolean = false
    )

    val id = glGenVertexArrays()
    val stride = attributes.sumBy { it.type.size * it.count }

    var buffer: GLBuffer? = null
        set(value) {
            field = value
            bindBuffer(value)
        }

    init {
        bind()

        var count = 0

        for ((index, attribute) in attributes.withIndex()) {
            glCall { glEnableVertexAttribArray(index) }

            glCall {
                glVertexAttribFormat(
                    index,
                    attribute.count,
                    attribute.type.id,
                    attribute.normalized,
                    count
                )
            }

            glCall { glVertexAttribBinding(index, 0) }
            count += attribute.type.size * attribute.count
        }
    }

    private fun bindBuffer(buffer: GLBuffer?) {
        if (buffer == null) {
            glCall {
                glBindVertexBuffer(0, 0, 0, 0)
            }
            return
        }

        glCall {
            glBindVertexBuffer(0, buffer.id, 0, stride)
        }
    }

    fun bind() {
        glBindVertexArray(id)
    }

    //TODO: Abstract vertexCount out with byte buffer and stride division
    fun draw(vertexCount: Int = 3, type: Int = GL11C.GL_TRIANGLES) {
        glCall { glDrawArrays(type, 0, vertexCount) }
    }

    fun drawElements(vertexCount: Int = 3, type: Int = GL11C.GL_TRIANGLES, ibo: GLBuffer) {
        ibo.bind()
        glCall { GL11C.glDrawElements(type, vertexCount, GL11C.GL_UNSIGNED_INT, 0) }
    }
}