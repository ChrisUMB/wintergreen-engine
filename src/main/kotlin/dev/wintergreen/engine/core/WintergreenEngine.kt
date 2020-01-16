package dev.wintergreen.engine.core

import dev.wintergreen.engine.Engine
import dev.wintergreen.engine.EngineException
import dev.wintergreen.engine.render.RenderEngine
import dev.wintergreen.engine.render.Viewport
import dev.wintergreen.engine.render.window.Window
import dev.wintergreen.engine.util.FrequencyTimer
import java.util.concurrent.TimeUnit

/**
 * The engine that ties everything together.
 */
object WintergreenEngine : Engine("game-engine") {

    internal val viewports = ArrayList<Viewport>()

    internal val loops = ArrayList<EngineLoop>()

    var running = false
        private set

    override fun load() {
        RenderEngine.load()
    }

    override fun start() {
        if (running) {
            throw EngineException(this, "Attempted to start after already running.")
        }

        running = true

        RenderEngine.start()
        loops.add(TickLoop)
        loops.forEach { it.lastRunTimestamp = System.currentTimeMillis(); it.onStart() }

        val secondTimer = FrequencyTimer.timesPer(1, TimeUnit.SECONDS)

        while (running) {
            for (loop in loops) {
                if (!loop.frequency.decrementIfReady()) {
                    continue
                }

                loop.onLoop()
                val lastTime = loop.lastRunTimestamp
                val thisTime = System.currentTimeMillis()
                loop.lastRunTimestamp = thisTime
                loop.deltaTime = (thisTime - lastTime).toFloat() / 1000f
                loop.time += loop.deltaTime
                loop.count++
                loop.rate = loop.count / secondTimer.count().toFloat()

                if (!secondTimer.isReady) {
                    continue
                }

                loop.count -= loop.rate

                println("%-10s @ %-7.2f/s".format(loop.id, loop.rate))
//                println("${loop.id} @ ${loop.rate}/s")

            }

            secondTimer.decrementIfReady()
        }

        loops.forEach { it.onStop() }
    }

    fun stop() {
        running = false
    }
}