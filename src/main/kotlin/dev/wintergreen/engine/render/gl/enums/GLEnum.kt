package dev.wintergreen.engine.render.gl.enums

import java.lang.RuntimeException
import java.util.*

interface GLEnum {
    fun value(): Int

    companion object {
        fun <T : GLEnum?> getEnumConstant(enumClass: Class<T>, value: Int): T {
            return enumClass.enumConstants
                .firstOrNull { it!!.value() == value }
                ?: throw RuntimeException() //TODO Kotlinize and change exception?
        }
    }
}