import dev.wintergreen.engine.EngineInitializer
import dev.wintergreen.engine.asset.Asset
import dev.wintergreen.engine.asset.WavefrontModel
import dev.wintergreen.engine.core.ecs.impl.ModelComponent
import dev.wintergreen.engine.core.ecs.impl.Position3D
import dev.wintergreen.engine.render.window.Window
import me.chrisumb.openmath.vector.Vector3f

object TestInitializer : EngineInitializer() {

    override fun preStart() {
        Window.create("Test!")
    }

    override fun onStart() {
        val viewport = viewports[0]

        val scene = viewport.scene

        scene.addSystem(ShitterSystem)
        scene.addEntity(
            ModelComponent(WavefrontModel[Asset["models/cube.obj"]]!!),
            Position3D(Vector3f(0f, 0f, 5f))
        )
    }
}