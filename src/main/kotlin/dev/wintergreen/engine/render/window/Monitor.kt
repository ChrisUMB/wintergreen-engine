package dev.wintergreen.engine.render.window

import me.chrisumb.openmath.vector.Vector2f
import dev.wintergreen.engine.EngineException
import dev.wintergreen.engine.render.RenderEngine
import org.lwjgl.glfw.GLFW.*

class Monitor private constructor(val id: Long) {

    lateinit var name: String
        private set

    lateinit var currentResolution: Resolution
        private set

    lateinit var targetResolution: Resolution
        private set

    lateinit var supportedResolutions: List<Resolution>
        private set

    lateinit var physicalSize: Vector2f
        private set

    lateinit var contentScale: Vector2f
        private set

    val refreshRate: Int
        get() = glfwGetVideoMode(id)!!.refreshRate()

    companion object {

        val primary
            get() = this[0]

        private val _monitors = ArrayList<Monitor>()
        val values: List<Monitor> = _monitors

        init {
            glfwSetMonitorCallback(::glfwMonitorCallback)
        }

        operator fun get(index: Int) = _monitors[index]

        private fun glfwMonitorCallback(id: Long, event: Int) {
            scanForMonitors()
        }

        fun scanForMonitors() {
            _monitors.clear()
            val monitorPointers = glfwGetMonitors()
                ?: throw EngineException(RenderEngine, "Unable to locate monitors.")

            val limit = monitorPointers.limit()

            for (i in 0 until limit) {
                val id = monitorPointers.get()
                val monitor = Monitor(id)
                setMonitorResolutionData(monitor)
                setMonitorSizeData(monitor)
                setMonitorContentScaleData(monitor)
                monitor.name = glfwGetMonitorName(id)!!
                _monitors.add(monitor)
            }
        }

        private fun setMonitorContentScaleData(monitor: Monitor) {
            val xScale = FloatArray(1)
            val yScale = FloatArray(1)
            glfwGetMonitorContentScale(monitor.id, xScale, yScale)
            monitor.contentScale = Vector2f(xScale[0], yScale[0])
        }

        private fun setMonitorSizeData(monitor: Monitor) {
            val id = monitor.id
            val widthBuffer = IntArray(1)
            val heightBuffer = IntArray(1)

            glfwGetMonitorPhysicalSize(id, widthBuffer, heightBuffer)
            val widthInches = widthBuffer[0].toFloat() / 25.4f
            val heightInches = heightBuffer[0].toFloat() / 25.4f
            monitor.physicalSize = Vector2f(widthInches, heightInches)
        }

        private fun setMonitorResolutionData(monitor: Monitor) {
            val id = monitor.id

            val currentMonitorMode = glfwGetVideoMode(id)
                ?: throw EngineException(RenderEngine, "Could not identify resolutions.")

            monitor.currentResolution = Resolution(currentMonitorMode.width(), currentMonitorMode.height())

            val modes = glfwGetVideoModes(id)
                ?: throw EngineException(RenderEngine, "Could not identify resolutions.")

            val supportedRes = ArrayList<Resolution>()

            for (mode in modes) {
                if (supportedRes.any { it.width == mode.width() && it.height == mode.height() }) {
                    continue
                }

                supportedRes.add(Resolution(mode.width(), mode.height()))
            }

            monitor.supportedResolutions = supportedRes

            val currentRes = monitor.currentResolution

            var target = currentRes

            val indexOfNative = supportedRes.indexOf(currentRes)
            for (i in indexOfNative downTo 0) {
                val res = supportedRes[i]
                if (res.aspectRatio == currentRes.aspectRatio
                    && res < currentRes
                ) {
                    target = res
                    break
                }
            }

            monitor.targetResolution = target
        }
    }

    override fun toString(): String {
        return "Monitor(id=$id, name='$name', currentResolution=$currentResolution, targetResolution=$targetResolution, supportedResolutions=$supportedResolutions, physicalSize=$physicalSize, contentScale=$contentScale)"
    }
}