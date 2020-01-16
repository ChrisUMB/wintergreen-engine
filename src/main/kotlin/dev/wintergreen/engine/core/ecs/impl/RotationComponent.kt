package dev.wintergreen.engine.core.ecs.impl

import dev.wintergreen.openmath.quaternion.Quaternionf
import dev.wintergreen.openmath.vector.Vector3f

class RotationComponent(rotation: Quaternionf) : ValueComponent<Quaternionf>(rotation)