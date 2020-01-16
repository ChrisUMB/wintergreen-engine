package dev.wintergreen.engine.util

import org.lwjgl.opengl.GL11C.*
import org.lwjgl.opengl.GL30C.GL_INVALID_FRAMEBUFFER_OPERATION

fun clearOpenGLErrors() {
    while(true) {
        if(glGetError() == GL_NO_ERROR) break
    }
}

private fun accumulateErrors(initial: Int): String {
    val errors = ArrayList<GLError>()

    var errorID = initial
    while(errorID != GL_NO_ERROR) {
        errors.add(GLError[errorID] ?: continue)
        errorID = glGetError()
    }

    return errors.toString()
}

fun getOpenGLErrors(): String? {
    val error = glGetError()
    if (error != GL_NO_ERROR)
        return accumulateErrors(error)
    return null
}

class OpenGLError(message: String) : RuntimeException(message)

inline fun <R> glCall(body: () -> R): R {
    clearOpenGLErrors()
    val result = body()
    getOpenGLErrors()?.let { throw OpenGLError(it) }

    return result
}

enum class GLError(val id: Int) {
    INVALID_ENUM(GL_INVALID_ENUM),
    INVALID_VALUE(GL_INVALID_VALUE),
    INVALID_OPERATION(GL_INVALID_OPERATION),
    INVALID_FRAMEBUFFER_OPERATION(GL_INVALID_FRAMEBUFFER_OPERATION),
    OUT_OF_MEMORY(GL_OUT_OF_MEMORY);

    override fun toString(): String {
        return "$name (0x${id.toString(16)})"
    }

    companion object {
        operator fun get(id: Int): GLError? {
            return values().find { it.id == id }
        }
    }
}