package dev.wintergreen.engine.core.ecs

import dev.wintergreen.engine.core.ecs.impl.ValueComponent
import kotlin.reflect.KClass

typealias ComponentType = KClass<out WGComponent>
typealias ValueComponentType<T> = KClass<out ValueComponent<T>>