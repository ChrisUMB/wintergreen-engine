package dev.wintergreen.engine.asset

import java.awt.image.BufferedImage
import java.net.URL
import javax.imageio.ImageIO

data class Asset(
    val name: String,
    val url: URL
) {

    fun readText(): String {
        return url.readText()
    }

    fun readAssets(): List<Asset> {
        return readText().lines().map { Asset[(name.removeSuffix("/") + "/") + it] }
    }

    fun readBytes(): ByteArray {
        return url.readBytes()
    }

    fun readImage(): BufferedImage {
        return ImageIO.read(url)
    }

    companion object {
        private val assetCache = HashMap<String, Asset>()

        operator fun get(path: String): Asset {
            return assetCache.getOrPut(path) {
                val resource = ClassLoader.getSystemResource(path) ?: throw AssetNotFoundException(path)
                Asset(path, resource)
            }
        }
    }
}