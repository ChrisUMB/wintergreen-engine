package dev.wintergreen.engine.render.gl.state

import dev.wintergreen.engine.render.gl.enums.BlendFactor
import dev.wintergreen.engine.render.gl.enums.BlendFunction
import dev.wintergreen.engine.render.gl.enums.GLEnum
import dev.wintergreen.engine.util.State
import org.lwjgl.opengl.GL11C

class GLBlendFuncState : State<BlendFunction> {
    override fun set(value: BlendFunction) {
        GL11C.glBlendFunc(value.src.value(), value.dst.value())
    }

    override fun get(): BlendFunction {
        val src = GL11C.glGetInteger(GL11C.GL_BLEND_SRC)
        val dst = GL11C.glGetInteger(GL11C.GL_BLEND_DST)
        val enumSrc: BlendFactor = GLEnum.getEnumConstant(BlendFactor::class.java, src)
        val enumDst: BlendFactor = GLEnum.getEnumConstant(BlendFactor::class.java, dst)
        return BlendFunction(enumSrc, enumDst)
    }
}