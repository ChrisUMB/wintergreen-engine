package dev.wintergreen.engine.render.gl

import org.lwjgl.opengl.GL11C.*
import org.lwjgl.opengl.GL30C.GL_HALF_FLOAT
import org.lwjgl.opengl.GL41C.GL_FIXED

enum class GLType(val size: Int, val id: Int) {
    BYTE(1, GL_BYTE),
    UBYTE(1, GL_UNSIGNED_BYTE),
    SHORT(2, GL_SHORT),
    USHORT(2, GL_UNSIGNED_SHORT),
    INT(4, GL_INT),
    UINT(4, GL_UNSIGNED_INT),
    FIXED(4, GL_FIXED),
    HALF(2, GL_HALF_FLOAT),
    FLOAT(4, GL_FLOAT),
    DOUBLE(8, GL_DOUBLE);

    operator fun times(count: Int) = VertexArray.Attribute(this, count)
}