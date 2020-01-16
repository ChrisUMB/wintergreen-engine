import me.chrisumb.openmath.matrix.Matrix4f
import me.chrisumb.openmath.quaternion.Quaternionf
import me.chrisumb.openmath.vector.Direction
import dev.wintergreen.engine.asset.Asset
import dev.wintergreen.engine.core.Scene
import dev.wintergreen.engine.core.ecs.WGEntity
import dev.wintergreen.engine.core.ecs.WGSystem
import dev.wintergreen.engine.core.ecs.impl.ModelComponent
import dev.wintergreen.engine.core.ecs.impl.Position3D
import dev.wintergreen.engine.render.RenderLoop
import dev.wintergreen.engine.render.Viewport
import dev.wintergreen.engine.render.shader.Shader
import dev.wintergreen.engine.render.shader.ShaderProgram

object ShitterSystem : WGSystem(ModelComponent::class, Position3D::class) {

    val shader = ShaderProgram(
        Shader(Asset["shaders/wavefront/wavefront.vs"]),
        Shader(Asset["shaders/wavefront/wavefront.fs"])
    )

    override fun tick(scene: Scene, entity: WGEntity) {
    }

    override fun render(view: Viewport, entity: WGEntity) {
        val model = entity.getComponent<ModelComponent>()
        val waveModel = model.model
        val position = entity.getComponent<Position3D>().value
        val camera = view.camera
        shader.bind()

        shader["uViewProjection"] = camera.projection
        shader["uModel"] =
            Matrix4f.newTranslation(position).rotate(
                Quaternionf(Direction.UP.vector, RenderLoop.time * 2f) *
                        Quaternionf(Direction.LEFT.vector, RenderLoop.time * 2f)
            )

        shader["uCameraPosition"] = camera.position
        waveModel.draw()
    }
}