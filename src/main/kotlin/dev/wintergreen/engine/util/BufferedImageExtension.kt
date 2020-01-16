package dev.wintergreen.engine.util

import java.awt.image.BufferedImage
import java.nio.ByteBuffer
import java.nio.ByteOrder

fun BufferedImage.toByteBuffer(
    format: ColorFormat = ColorFormat.RGBA,
    flippedX: Boolean = false,
    flippedY: Boolean = false
): ByteBuffer {
    val pixels = getRGB(0, 0, width, height, null, 0, width)

    val buffer = ByteBuffer.allocateDirect(height * width * format.order.size).order(ByteOrder.nativeOrder())
    val hasAlpha = colorModel.hasAlpha()

    val xRange = if (flippedX) width - 1 downTo 0 else 0 until width
    val yRange = if (flippedY) height - 1 downTo 0 else 0 until height
    for (y in yRange) {
        for (x in xRange) {
            val pixel = pixels[y * width + x]

            format.order.forEach {
                when (it) {
                    0 -> buffer.put((pixel shr 16 and 0xFF).toByte())
                    1 -> buffer.put((pixel shr 8 and 0xFF).toByte())
                    2 -> buffer.put((pixel and 0xFF).toByte())
                    3 -> if (hasAlpha) buffer.put((pixel shr 24 and 0xFF).toByte()) else buffer.put(0xFF.toByte())
                }
            }
        }
    }

    buffer.flip()
    return buffer
}