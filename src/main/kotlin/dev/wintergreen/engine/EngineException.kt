package dev.wintergreen.engine

class EngineException(val engine: Engine, message: String) : RuntimeException("[${engine.id}] $message")