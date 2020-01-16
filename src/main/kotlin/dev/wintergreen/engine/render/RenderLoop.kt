package dev.wintergreen.engine.render

import dev.wintergreen.engine.core.EngineLoop
import dev.wintergreen.engine.core.WintergreenEngine
import dev.wintergreen.engine.util.FrequencyTimer
import org.lwjgl.glfw.GLFW
import org.lwjgl.opengl.GL11C
import kotlin.system.exitProcess

object RenderLoop : EngineLoop(
    "render",
    FrequencyTimer.always()
) {

    override fun onStart() {
        GLFW.glfwSwapInterval(0)
    }

    override fun onLoop() {
        if (WintergreenEngine.viewports.isEmpty()) {
            WintergreenEngine.stop()
            return
        }

        val invalidPorts = ArrayList<Viewport>(0)

        for (viewport in WintergreenEngine.viewports) {
            RenderTarget.reset()
            val target = viewport.target
            if (!target.isValid) {
                invalidPorts.add(viewport)
                target.doInvalidRender(viewport)
                continue
            }

            target.bind()
            GL11C.glClear(GL11C.GL_COLOR_BUFFER_BIT or GL11C.GL_DEPTH_BUFFER_BIT)
            target.doRender(viewport)
        }

        WintergreenEngine.viewports.removeAll(invalidPorts)

        GLFW.glfwPollEvents()
    }

    override fun onStop() {
        GLFW.glfwTerminate()
        exitProcess(0)
    }
}