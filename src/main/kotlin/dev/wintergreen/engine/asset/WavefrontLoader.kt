package dev.wintergreen.engine.asset

import me.chrisumb.openmath.extension.times
import me.chrisumb.openmath.vector.Vector2f
import me.chrisumb.openmath.vector.Vector3f
import dev.wintergreen.engine.render.gl.GLBuffer
import dev.wintergreen.engine.util.Vertex
import org.lwjgl.BufferUtils
import org.lwjgl.opengl.GL15C.GL_ELEMENT_ARRAY_BUFFER
import java.nio.FloatBuffer

object WavefrontLoader {

    fun parse(asset: Asset): WavefrontModel {
        val positions = ArrayList<Vector3f>()
        val texCoords = ArrayList<Vector2f>()
        val normals = ArrayList<Vector3f>()
        val indices = ArrayList<VertexIndex>()

        val lines = asset.readText().lines()
        for (line in lines) {
            val split = line.split(' ')

            when (split[0]) {
                "v" -> positions.add(Vector3f(splitToFloats(split, 3)))
                "vt" -> texCoords.add(Vector2f(splitToFloats(split, 2)))
                "vn" -> normals.add(Vector3f(splitToFloats(split, 3)))
                "f" -> {

                    for (i in 1..3) {
                        val fSplit = split[i].split("/")
                        val vIndex = fSplit[0].toInt() - 1
                        val vTexCoordIndex = fSplit[1].toInt() - 1
                        val vNormal = fSplit[2].toInt() - 1
                        indices.add(VertexIndex(vIndex, vTexCoordIndex, vNormal))
                    }
                }
            }
        }

        val vertices = ArrayList<Vertex>()
        val iboData = ArrayList<Int>()

        for (index in indices) {
            val pos = positions[index.position]
            val tCoord = texCoords[index.texCoord]
            val normal = normals[index.normal]
            val vertex = Vertex(pos, tCoord, normal)

            val indexOf = vertices.indexOf(vertex)
            if (indexOf == -1) {
                iboData.add(vertices.size)
                vertices.add(vertex)
            } else {
                iboData.add(indexOf)
            }
        }

        for (i in 0 until iboData.size step 3) {
            val index = iboData[i]

            val vert0 = vertices[index]
            val vert1 = vertices[index + 1]
            val vert2 = vertices[index + 2]

            val e1 = vert1.position - vert0.position
            val e2 = vert2.position - vert0.position
            val d1 = vert1.texCoord - vert0.texCoord
            val d2 = vert2.texCoord - vert0.texCoord

            val f = 1.0f / (d1.x * d2.y - d2.x * d1.y)

            val tangent = (f * ((d2.y * e1) - (d1.y * e2))).normalized
            vert0.tangent = (vert0.tangent + tangent).normalized
            vert1.tangent = (vert1.tangent + tangent).normalized
            vert2.tangent = (vert2.tangent + tangent).normalized
        }

        val ibo = GLBuffer(GL_ELEMENT_ARRAY_BUFFER)
        ibo.data(iboData.toIntArray())

        val vbo = GLBuffer()
        val data = vertices.flatMap { vertex ->
            listOf(
                vertex.position.floatArray,
                vertex.texCoord.floatArray,
                vertex.normal.floatArray,
                vertex.tangent.floatArray
            ).flatMap { it.toList() }

        }.toFloatArray()

        vbo.data(data)

        return WavefrontModel(ibo, vbo, iboData.size)
    }

    private fun splitToFloats(split: List<String>, count: Int): FloatBuffer {
        val buffer = BufferUtils.createFloatBuffer(count)
        for (i in 1..count) {
            buffer.put(split[i].toFloat())
        }

        buffer.flip()
        return buffer
    }

    data class VertexIndex(
        val position: Int,
        val texCoord: Int,
        val normal: Int
    )
}