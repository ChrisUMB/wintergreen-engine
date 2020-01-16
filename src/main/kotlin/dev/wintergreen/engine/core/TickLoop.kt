package dev.wintergreen.engine.core

import dev.wintergreen.engine.util.FrequencyTimer
import java.util.concurrent.TimeUnit

object TickLoop : EngineLoop("tick", FrequencyTimer.timesPer(60, TimeUnit.SECONDS)) {

    private val hasTicked = ArrayList<Scene>()

    override fun onLoop() {
        for ((target, camera, scene) in WintergreenEngine.viewports) {
            if (scene !in hasTicked) {
                scene.tick()
            }
        }

        hasTicked.clear()
    }
}