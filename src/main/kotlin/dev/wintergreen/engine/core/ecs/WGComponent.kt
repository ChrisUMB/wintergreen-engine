package dev.wintergreen.engine.core.ecs

abstract class WGComponent {
    val id: String = this::class.simpleName ?: "anonymous system"
}