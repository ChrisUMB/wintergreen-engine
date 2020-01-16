package dev.wintergreen.engine.core.ecs.impl

import dev.wintergreen.engine.asset.WavefrontModel
import dev.wintergreen.engine.core.ecs.WGComponent

class ModelComponent(val model: WavefrontModel): WGComponent()