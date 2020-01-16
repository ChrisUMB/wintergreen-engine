package dev.wintergreen.engine.render.shader

import dev.wintergreen.engine.asset.Asset
import org.lwjgl.opengl.GL20C.*
import org.lwjgl.opengl.GL32C

class Shader(val asset: Asset, val type: Type = determineType(asset)) {

    enum class Type(val id: Int) {
        FRAGMENT(GL_FRAGMENT_SHADER),
        VERTEX(GL_VERTEX_SHADER),
        GEOMETRY(GL32C.GL_GEOMETRY_SHADER)
    }

    val id = glCreateShader(type.id)

    init {
        val code = asset.readText()
        glShaderSource(id, code)
        glCompileShader(id)

        if (glGetShaderi(id, GL_COMPILE_STATUS) == 0) {
            val log = glGetShaderInfoLog(id)
            throw IllegalStateException("ERROR: Shader Compile Failure (shaders/${asset.name}) \n$log")
        }
    }

    fun delete() {
        glDeleteShader(id)
    }

    companion object {
        private fun determineType(asset: Asset): Type {
            val last = asset.name.split('.').last()
            return when (last) {
                "fs" -> Type.FRAGMENT
                "vs" -> Type.VERTEX
                "gs" -> Type.GEOMETRY
                else -> throw IllegalArgumentException("Could not determine shader type for ${asset.name}, please explicitly type.")
            }
        }
    }

}