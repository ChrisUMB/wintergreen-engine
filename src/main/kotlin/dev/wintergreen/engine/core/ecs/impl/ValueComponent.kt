package dev.wintergreen.engine.core.ecs.impl

import dev.wintergreen.engine.core.ecs.WGComponent

abstract class ValueComponent<T>(val value: T): WGComponent()