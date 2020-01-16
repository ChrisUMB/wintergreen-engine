package dev.wintergreen.engine.render.gl.enums

data class BlendFunction(val src: BlendFactor, val dst: BlendFactor) {
    companion object {
        val standard = BlendFunction(
            BlendFactor.SRC_ALPHA,
            BlendFactor.ONE_MINUS_SRC_ALPHA
        )
    }

}