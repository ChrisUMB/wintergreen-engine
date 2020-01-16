package dev.wintergreen.engine.render.shader

import dev.wintergreen.openmath.matrix.Matrix4f
import dev.wintergreen.openmath.vector.Vector2f
import dev.wintergreen.openmath.vector.Vector3f
import dev.wintergreen.openmath.vector.Vector4f
import dev.wintergreen.engine.render.Color
import dev.wintergreen.engine.util.glCall
import org.lwjgl.opengl.GL20C.*
import org.lwjgl.opengl.GL30C.glBindFragDataLocation

class ShaderProgram(vararg shaders: Shader) {

    val id = glCreateProgram()

    private val uniformMap = HashMap<String, Int>()

    init {
        for (shader in shaders) {
            glAttachShader(id, shader.id)
        }

        glBindFragDataLocation(id, 0, "fColor")
        glLinkProgram(id)

        if (glGetProgrami(id, GL_LINK_STATUS) == 0) {
            throw IllegalStateException("Error Linking: \n${glGetProgramInfoLog(id)}")
        }

        glValidateProgram(id)

        if (glGetProgrami(id, GL_VALIDATE_STATUS) == 0) {
            throw IllegalStateException("Error Validating: \n${glGetProgramInfoLog(id)}")
        }

        for (shader in shaders) {
            glDetachShader(id, shader.id)
        }
    }

    private fun getUniformLocation(name: String): Int {
        return uniformMap.computeIfAbsent(name) { glGetUniformLocation(id, name) }
    }

    operator fun set(name: String, value: Float) {
        glCall { glUniform1f(getUniformLocation(name), value) }
    }

    operator fun set(name: String, value: Vector2f) {
        glCall { glUniform2f(getUniformLocation(name), value.x, value.y) }
    }

    operator fun set(name: String, value: Vector3f) {
        glCall { glUniform3f(getUniformLocation(name), value.x, value.y, value.z) }
    }

    operator fun set(name: String, value: Vector4f) {
        glCall { glUniform4f(getUniformLocation(name), value.x, value.y, value.z, value.w) }
    }

    operator fun set(name: String, value: Matrix4f) {
        glCall { glUniformMatrix4fv(getUniformLocation(name), false, value.buffer.asFloatBuffer()) }
    }

    operator fun set(name: String, value: Int) {
        glCall { glUniform1i(getUniformLocation(name), value) }
    }

    operator fun set(name: String, value: Boolean) {
        this[name] = if (value) 1 else 0
    }

    operator fun set(name: String, value: Color) {
        this[name] = value.toVector4f()
    }

    fun bind() {
        glCall { glUseProgram(id) }
    }

}