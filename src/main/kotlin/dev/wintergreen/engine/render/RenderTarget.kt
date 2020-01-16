package dev.wintergreen.engine.render

import dev.wintergreen.engine.EngineException
import dev.wintergreen.engine.render.window.Resolution
import java.util.*

abstract class RenderTarget(
    open val resolution: Resolution
) {

    protected abstract fun doBind()
    protected abstract fun doUnbind()
    abstract fun doRender(view: Viewport)
    abstract fun doInvalidRender(view: Viewport)


    abstract val isValid: Boolean

    /**
     * Binds this render target.
     */
    fun bind() {
        stack.lastOrNull()?.doUnbind()
        stack.push(this)
        doBind()
    }

    /**
     * Unbind only succeeds if there is another render target available AND bound.
     */
    fun unbind() {
        if (stack.peek() != this) {
            throw EngineException(RenderEngine, "Attempted to unbind an already unbound render target.")
        }

        if (stack.size <= 1) {
            throw EngineException(RenderEngine, "Attempted to unbind the last render target in the stack.")
        }

        stack.pop()
        doUnbind()
    }

    internal companion object {
        val stack = Stack<RenderTarget>()

        fun reset() {
            for (target in stack) {
                target.doUnbind()
            }

            stack.clear()
        }
    }
}