package dev.wintergreen.engine

import dev.wintergreen.engine.core.WintergreenEngine
import dev.wintergreen.engine.render.RenderEngine
import dev.wintergreen.engine.render.Viewport

abstract class EngineInitializer {

    val viewports
        get() = WintergreenEngine.viewports

    open fun preStart() {}

    open fun onStart() {}

    fun addViewport(view: Viewport) {
        WintergreenEngine.viewports.add(view)
    }

    fun start() {
        if (WintergreenEngine.running) {
            throw EngineException(WintergreenEngine, "Tried to start the engine more than once.")
        }

        WintergreenEngine.load()
        preStart()
        RenderEngine.createWindows()
        onStart()
        WintergreenEngine.start()
    }
}