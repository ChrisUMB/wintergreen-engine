package dev.wintergreen.engine.render.shader

import org.lwjgl.opengl.GL20C
import org.lwjgl.opengl.GL32C

enum class ShaderType(val id: Int) {
    FRAGMENT(GL20C.GL_FRAGMENT_SHADER),
    VERTEX(GL20C.GL_VERTEX_SHADER),
    GEOMETRY(GL32C.GL_GEOMETRY_SHADER)
}