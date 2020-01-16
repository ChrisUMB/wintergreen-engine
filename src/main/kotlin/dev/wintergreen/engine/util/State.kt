package dev.wintergreen.engine.util

interface State<T> {
    fun set(value: T)
    fun get(): T
}