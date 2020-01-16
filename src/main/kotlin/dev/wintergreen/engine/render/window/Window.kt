package dev.wintergreen.engine.render.window

import dev.wintergreen.openmath.vector.Vector2f
import dev.wintergreen.engine.EngineException
import dev.wintergreen.engine.core.Scene
import dev.wintergreen.engine.core.WintergreenEngine
import dev.wintergreen.engine.input.Input
import dev.wintergreen.engine.render.Camera
import dev.wintergreen.engine.render.RenderEngine
import dev.wintergreen.engine.render.RenderTarget
import dev.wintergreen.engine.render.Viewport
import org.lwjgl.glfw.GLFW.*

class Window private constructor(
    title: String,
    val monitor: Monitor = Monitor.primary,
    resolution: Resolution = monitor.targetResolution
) : RenderTarget(resolution) {

    val input = Input(this)

    override var resolution: Resolution = resolution
        set(value) {
            if (created && field != value) {
                glfwSetWindowSize(id, resolution.width, resolution.height)
            }
            if (!created) {
                recenter()
            }
            field = value
        }

    var title = title
        set(value) {
            field = value
            if (created) {
                glfwSetWindowTitle(id, title)
            }
        }

    var id: Long = -1L
        private set

    val created get() = id != -1L

    //TODO: Vector2i
    var position: Vector2f
        get() {
            if (!created) {
                throw EngineException(RenderEngine, "Window position accessed before creation.")
            }

            val xPos = IntArray(1)
            val yPos = IntArray(1)
            glfwGetWindowPos(id, xPos, yPos)

            return Vector2f(xPos[0].toFloat(), yPos[0].toFloat())
        }
        set(value) {
            if (created) {
                glfwSetWindowPos(id, value.x.toInt(), value.y.toInt())
            }
        }

    var mode = Mode.WINDOWED
        set(value) {
            if (created && field != value) {
                destroy()
                create()
            }

            field = value
        }

    var destoyed = false
        private set

    override val isValid: Boolean
        get() = created && !destoyed && !glfwWindowShouldClose(id)

    fun destroy() {
        destoyed = true
        glfwDestroyWindow(id)
    }

    override fun doBind() {
        glfwMakeContextCurrent(id)
    }

    override fun doUnbind() {

    }

    override fun doInvalidRender(view: Viewport) {
        destroy()
        _windows.remove(this)
    }

    override fun doRender(view: Viewport) {
        view.scene.render(view)
        swapBuffers()
    }

    internal fun create() {
        mode.run(this)
        val monitorId = if (mode == Mode.FULLSCREEN) monitor.id else 0
        val shareWindowId = values.firstOrNull()?.takeIf(Window::created)?.id ?: 0

        setupHints()
        id = glfwCreateWindow(resolution.width, resolution.height, title, monitorId, shareWindowId)
        doBind()
    }

    private fun setupHints() {
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3)
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3)
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE)
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE)
        glfwWindowHint(GLFW_SAMPLES, 16) //TODO: Configurable?
    }

    internal fun setup() {
        recenter()
        glfwSetWindowSizeCallback(id, WindowCallbacks::setSizeCallback)
        glfwSetKeyCallback(id, WindowCallbacks::setKeyCallback)
        glfwSetWindowCloseCallback(id, WindowCallbacks::setCloseCallback)
        glfwSetWindowFocusCallback(id, WindowCallbacks::setFocusCallback)
        glfwSetWindowPosCallback(id, WindowCallbacks::setPosCallback)
        glfwSetMouseButtonCallback(id, WindowCallbacks::setMouseButtonCallback)
        glfwSetCursorPosCallback(id, WindowCallbacks::setCursorPositionCallback)
        glfwSetScrollCallback(id, WindowCallbacks::setScrollCallback)
        glfwSetCharCallback(id, WindowCallbacks::setCharCallback)
        glfwShowWindow(id)
    }

    fun recenter() {
        position = (monitor.currentResolution.vector / 2f) - (resolution.vector / 2f)
    }

    private fun swapBuffers() {
        glfwSwapBuffers(id)
    }

    companion object {

        internal val _windows = ArrayList<Window>()

        val values: List<Window>
            get() = _windows

        operator fun get(index: Int) = values.toList()[index]

        fun create(
            title: String,
            monitor: Monitor = Monitor.primary,
            resolution: Resolution = monitor.targetResolution
        ): Window {
            val window = Window(title, monitor, resolution)
            _windows.add(window)
            val viewport = Viewport(window, Camera.perspective(window), Scene())
            WintergreenEngine.viewports.add(viewport)
            return window
        }

        fun create(title: String, resolution: Resolution): Window {
            return create(title, Monitor.primary, resolution)
        }

        fun create(title: String, width: Int, height: Int): Window {
            return create(title, Monitor.primary, Resolution(width, height))
        }
    }

    enum class Mode(val run: Window.() -> Unit = {}) {
        WINDOWED,
        WINDOWED_BORDERLESS({
            glfwWindowHint(GLFW_DECORATED, GLFW_FALSE)
        }),
        FULLSCREEN,
        FULLSCREEN_BORDERLESS({
            glfwWindowHint(GLFW_DECORATED, GLFW_FALSE)
            val mode = glfwGetVideoMode(monitor.id)
                ?: throw EngineException(RenderEngine, "Failure to get video mode for monitor \"${monitor.name}\"")

            glfwWindowHint(GLFW_RED_BITS, mode.redBits())
            glfwWindowHint(GLFW_GREEN_BITS, mode.greenBits())
            glfwWindowHint(GLFW_BLUE_BITS, mode.blueBits())
            glfwWindowHint(GLFW_REFRESH_RATE, mode.refreshRate())
            resolution = Resolution(mode.width(), mode.height())
        })
    }
}