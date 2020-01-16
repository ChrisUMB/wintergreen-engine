package dev.wintergreen.engine

abstract class Engine(val id: String) {
    abstract fun start()
    abstract fun load()
}