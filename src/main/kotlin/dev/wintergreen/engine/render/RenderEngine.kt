package dev.wintergreen.engine.render

import dev.wintergreen.engine.Engine
import dev.wintergreen.engine.EngineException
import dev.wintergreen.engine.core.WintergreenEngine
import dev.wintergreen.engine.render.gl.enums.BlendFunction
import dev.wintergreen.engine.render.gl.enums.CullFaceMode
import dev.wintergreen.engine.render.gl.enums.FrontFaceDirection
import dev.wintergreen.engine.render.gl.state.GLStateManager
import dev.wintergreen.engine.render.window.Monitor
import dev.wintergreen.engine.render.window.Window
import dev.wintergreen.openmath.vector.Vector4f
import org.lwjgl.glfw.GLFW.glfwInit
import org.lwjgl.opengl.GL

object RenderEngine : Engine("render-engine") {

    override fun load() {
        if (!glfwInit()) {
            throw EngineException(this, "Failed to initialize GLFW.")
        }

        Monitor.scanForMonitors()
    }

    internal fun createWindows() {
        if (WintergreenEngine.viewports.isEmpty()) {
            throw EngineException(RenderEngine, "No scenes were added, cannot create GL context without a scene.")
        }
        Window.values.forEach(Window::create)
        GL.createCapabilities()
        setup()
        Window.values.forEach(Window::setup)
    }

    private fun setup() {
        GLStateManager.enableCullFace.set(true)
        GLStateManager.enableDepthTest.set(true)
        GLStateManager.enableDepthClamp.set(true)
        GLStateManager.enableBlend.set(true)

        GLStateManager.cullFaceMode.set(CullFaceMode.BACK)
        GLStateManager.frontFace.set(FrontFaceDirection.CLOCKWISE)
        GLStateManager.blendFunction.set(BlendFunction.standard)

        GLStateManager.clearColor.set(Vector4f(0.2f))
    }

    /**
     * Called after windows have been created.
     */
    override fun start() {
        WintergreenEngine.loops.add(RenderLoop)
    }
}