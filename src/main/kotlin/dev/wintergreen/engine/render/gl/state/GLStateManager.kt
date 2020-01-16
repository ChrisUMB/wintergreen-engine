package dev.wintergreen.engine.render.gl.state

import dev.wintergreen.engine.render.gl.enums.BlendFunction
import dev.wintergreen.engine.render.gl.enums.CullFaceMode
import dev.wintergreen.engine.render.gl.enums.FrontFaceDirection
import dev.wintergreen.engine.util.State
import dev.wintergreen.openmath.vector.Vector4f
import org.lwjgl.opengl.GL11C
import org.lwjgl.opengl.GL32C

object GLStateManager {
    val enableBlend: State<Boolean> = GLEnableState(GL11C.GL_BLEND)
    val enableDepthTest: State<Boolean> = GLEnableState(GL11C.GL_DEPTH_TEST)
    val enableDepthClamp: State<Boolean> = GLEnableState(GL32C.GL_DEPTH_CLAMP)
    val enableCullFace: State<Boolean> = GLEnableState(GL11C.GL_CULL_FACE)
    val cullFaceMode: State<CullFaceMode> = GLEnumState(
        CullFaceMode::class.java,
        { GL11C.glCullFace(it) },
        { GL11C.glGetInteger(GL11C.GL_CULL_FACE_MODE) }
    )
    val frontFace: State<FrontFaceDirection> = GLEnumState(
        FrontFaceDirection::class.java,
        { GL11C.glFrontFace(it) },
        { GL11C.glGetInteger(GL11C.GL_FRONT_FACE) }
    )
    val blendFunction: State<BlendFunction> = GLBlendFuncState()
    val clearColor: State<Vector4f> = GLClearColorState()
}