package dev.wintergreen.engine.render

import dev.wintergreen.engine.Engine
import dev.wintergreen.engine.EngineException
import dev.wintergreen.engine.core.WintergreenEngine
import dev.wintergreen.engine.render.window.Monitor
import dev.wintergreen.engine.render.window.Window
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
        Window.values.forEach(Window::setup)
    }

    /**
     * Called after windows have been created.
     */
    override fun start() {
        WintergreenEngine.loops.add(RenderLoop)
    }
}