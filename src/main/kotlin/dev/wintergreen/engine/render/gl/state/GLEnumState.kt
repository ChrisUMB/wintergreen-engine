package dev.wintergreen.engine.render.gl.state

import dev.wintergreen.engine.render.gl.enums.GLEnum
import dev.wintergreen.engine.util.State

class GLEnumState<T : GLEnum>(
    private val clazz: Class<T>,
    private val setter: (Int) -> Unit,
    private val getter: (() -> Int)?
) : State<T> {
    override fun set(value: T) {
        setter(value.value())
    }

    override fun get(): T {
        if (getter == null)
            throw UnsupportedOperationException("This OpenGL state does not support getting.")
        return GLEnum.getEnumConstant(clazz, getter.invoke())
    }

}