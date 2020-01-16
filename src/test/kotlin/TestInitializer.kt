import dev.wintergreen.engine.EngineInitializer
import dev.wintergreen.engine.asset.Asset
import dev.wintergreen.engine.asset.WavefrontModel
import dev.wintergreen.engine.core.ecs.impl.ModelComponent
import dev.wintergreen.engine.core.ecs.impl.Position3DComponent
import dev.wintergreen.engine.core.ecs.impl.RotationComponent
import dev.wintergreen.engine.render.window.Window
import dev.wintergreen.openmath.global.radians
import dev.wintergreen.openmath.quaternion.Quaternionf
import dev.wintergreen.openmath.vector.Axis
import dev.wintergreen.openmath.vector.Direction
import dev.wintergreen.openmath.vector.Vector3f

object TestInitializer : EngineInitializer() {

    override fun preStart() {
        Window.create("Test!")
    }

    override fun onStart() {
        val viewport = viewports[0]

        val scene = viewport.scene

        scene.addSystem(TestSystem)
        scene.addEntity(
            ModelComponent(WavefrontModel[Asset["models/Toriman.obj"]]!!),
            Position3DComponent(Vector3f(0f, 0f, 7f)),
            RotationComponent(Quaternionf(Axis.Z.vector, radians(45f)))
        )
    }
}