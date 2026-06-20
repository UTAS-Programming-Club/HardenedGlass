package au.com.programmingclub.hardenedglass

import net.minecraft.client.render.texture.DynamicTexture
import org.lwjgl.opengl.GL11
import java.awt.image.BufferedImage

// Based on rgml-quilt's version of ModLoader
// https://github.com/sschr15/rgml-quilt/blob/f0e4c913fc7cac8b3c3fbcc2d6b64c70a0837c12/src/main/risugami/org/duvetmc/rgml/ModTextureStatic.java
// Used under MIT License

class ModTextureStatic(slot: Int, size: Int, dst: Int, source: BufferedImage) : DynamicTexture(slot) {
    private var oldAnaglyph = false
    private var buffer: IntArray
    private val atlas: Int

    constructor(slot: Int, dst: Int, source: BufferedImage) : this(slot, 1, dst, source)

    init {
        this.copyTo = size
        this.atlas = dst
        bind()
        val targetWidth = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_WIDTH) / 16
        val targetHeight = GL11.glGetTexLevelParameteri(GL11.GL_TEXTURE_2D, 0, GL11.GL_TEXTURE_HEIGHT) / 16
        val width = source.width
        val height = source.height
        this.buffer = IntArray(targetWidth * targetHeight)
        this.pixels = ByteArray(targetWidth * targetHeight * 4)
        if (width == height && width == targetWidth) {
            source.getRGB(0, 0, width, height, this.buffer, 0, width)
        } else {
            val img = BufferedImage(targetWidth, targetHeight, 6)
            val gfx = img.createGraphics()
            gfx.drawImage(source, 0, 0, targetWidth, targetHeight, 0, 0, width, height, null)
            img.getRGB(0, 0, targetWidth, targetHeight, this.buffer, 0, targetWidth)
            gfx.dispose()
        }

        this.updatePixels()
    }

    override fun tick() {
        if (this.oldAnaglyph != this.anaglyph) {
            this.updatePixels()
        }
    }

    private fun updatePixels() {
        for (i in this.buffer.indices) {
            val a = this.buffer[i] shr 24 and 0xFF
            var r = this.buffer[i] shr 16 and 0xFF
            var g = this.buffer[i] shr 8 and 0xFF
            var b = this.buffer[i] shr 0 and 0xFF
            if (this.anaglyph) {
                val grey = (r + g + b) / 3
                b = grey
                g = grey
                r = grey
            }

            this.pixels[i * 4 + 0] = r.toByte()
            this.pixels[i * 4 + 1] = g.toByte()
            this.pixels[i * 4 + 2] = b.toByte()
            this.pixels[i * 4 + 3] = a.toByte()
        }

        this.oldAnaglyph = this.anaglyph
    }

    private fun bind() {
        val file: String = when (atlas == 1) {
            true -> "/gui/items.png"
            false -> "/terrain.png"
        }
        Helpers.minecraftInstance.textureManager.load(file)
    }
}
