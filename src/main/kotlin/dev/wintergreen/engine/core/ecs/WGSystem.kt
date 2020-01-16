package dev.wintergreen.engine.core.ecs

import dev.wintergreen.engine.core.Scene
import dev.wintergreen.engine.render.Viewport

abstract class WGSystem(vararg val compatible: ComponentType) {
    val id: String = this::class.simpleName ?: "anonymous system"

    internal val _entities = HashSet<WGEntity>()
    val entities: Set<WGEntity> = _entities

//    abstract fun action(entity: WGEntity)

    abstract fun tick(scene: Scene, entity: WGEntity)
    abstract fun render(view: Viewport, entity: WGEntity)

    fun renderOnAll(view: Viewport) {
        _entities.forEach { render(view, it) }
    }

    fun tickOnAll(scene: Scene) {
        _entities.forEach { tick(scene, it) }
    }
}