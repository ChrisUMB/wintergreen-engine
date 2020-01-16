package dev.wintergreen.engine.core.ecs

import dev.wintergreen.engine.core.Scene
import dev.wintergreen.engine.core.ecs.impl.ValueComponent
import java.util.*
import kotlin.NoSuchElementException
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

@Suppress("UNCHECKED_CAST")
class WGEntity(val scene: Scene, vararg components: WGComponent) {

    internal val compatibleSystems = ArrayList<WGSystem>()

    private val _components = HashMap<ComponentType, WGComponent>()

    val id = UUID.randomUUID()

    init {
        updateSystems()
    }

    fun addComponent(component: WGComponent) {
        _components[component::class] = component
        updateSystems()
    }

    fun removeComponent(type: ComponentType) {
        _components.remove(type)
        updateSystems()
    }

    inline fun <V, reified T : ValueComponent<V>> getValue(): V? {
        return getValue(T::class)
    }

    fun <V, T : ValueComponent<V>> getValue(type: ValueComponentType<V>): V? {
        return getOptionalComponent<T>(type)?.value
    }

    fun <T : WGComponent> getOptionalComponent(type: ComponentType): T? {
        return _components[type] as? T
    }

    inline fun <reified T : WGComponent> hasComponent(): Boolean {
        return getOptionalComponent<T>() != null
    }

    inline fun <reified T : WGComponent> getOptionalComponent(): T? {
        return getOptionalComponent(T::class)
    }

    inline fun <reified T : WGComponent> getComponent(): T {
        return getOptionalComponent(T::class)
            ?: throw NoSuchElementException("Entity ($id) missing component ${T::class.simpleName}")
    }

    internal fun updateSystems() {
        compatibleSystems.forEach {
            it._entities.remove(this)
        }

        compatibleSystems.clear()
        for (system in scene.systems) {
            if (system.compatible.all { _components.containsKey(it) }) {
                compatibleSystems.add(system)
            }

            system._entities.add(this)
        }
    }

    init {
        for (component in components) {
            _components[component::class] = component
        }
    }
}