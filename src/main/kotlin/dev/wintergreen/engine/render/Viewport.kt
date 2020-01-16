package dev.wintergreen.engine.render

import dev.wintergreen.engine.core.Scene

data class Viewport(
    var target: RenderTarget,
    var camera: Camera,
    var scene: Scene
)