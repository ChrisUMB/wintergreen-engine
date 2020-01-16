package dev.wintergreen.engine.core

import dev.wintergreen.engine.core.ecs.WGComponent
import dev.wintergreen.engine.core.ecs.WGEntity
import dev.wintergreen.engine.core.ecs.WGSystem
import dev.wintergreen.engine.render.Viewport

open class Scene {

    internal val _systems = HashSet<WGSystem>()
    val systems: Set<WGSystem> = _systems

    internal val _entities = HashSet<WGEntity>()
    val entities: Set<WGEntity> = _entities

    fun addEntity(vararg components: WGComponent) {
        _entities.add(WGEntity(this, *components))
    }

    fun addEntity(entity: WGEntity) {
        _entities.add(entity)
    }

    fun removeEntity(entity: WGEntity) {
        _entities.remove(entity)
    }

    fun addSystem(system: WGSystem) {
        _systems.add(system)
    }

    fun removeSystem(system: WGSystem) {
        _systems.remove(system)
    }

    fun render(view: Viewport) {
        _systems.forEach { it.renderOnAll(view) }
    }

    fun tick() {
        _systems.forEach { it.tickOnAll(this) }
    }

}