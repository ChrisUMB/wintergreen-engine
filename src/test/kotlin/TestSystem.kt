import dev.wintergreen.openmath.matrix.Matrix4f
import dev.wintergreen.openmath.quaternion.Quaternionf
import dev.wintergreen.openmath.vector.Direction
import dev.wintergreen.engine.asset.Asset
import dev.wintergreen.engine.core.Scene
import dev.wintergreen.engine.core.ecs.WGEntity
import dev.wintergreen.engine.core.ecs.WGSystem
import dev.wintergreen.engine.core.ecs.impl.ModelComponent
import dev.wintergreen.engine.core.ecs.impl.Position3DComponent
import dev.wintergreen.engine.core.ecs.impl.RotationComponent
import dev.wintergreen.engine.render.RenderLoop
import dev.wintergreen.engine.render.Viewport
import dev.wintergreen.engine.render.shader.Shader
import dev.wintergreen.engine.render.shader.ShaderProgram

object TestSystem : WGSystem(ModelComponent::class, Position3DComponent::class) {

    val shader = ShaderProgram(
        Shader(Asset["shaders/wavefront/wavefront.vs"]),
        Shader(Asset["shaders/wavefront/wavefront.fs"])
    )

    override fun tick(scene: Scene, entity: WGEntity) {
    }

    override fun render(view: Viewport, entity: WGEntity) {
        val model = entity.get<ModelComponent>()
        val waveModel = model.model
        val position = entity.get<Position3DComponent>().value
        val rotation = entity.getOptional<RotationComponent>()?.value ?: Quaternionf()
        val camera = view.camera
        shader.bind()

        shader["uViewProjection"] = camera.projection
        shader["uModel"] = Matrix4f.newTranslation(position).rotate(rotation)
        shader["uCameraPosition"] = camera.position
        waveModel.draw()
    }
}