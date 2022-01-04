package ru.lyubimov.seam_carving.image_proc

import java.awt.Color
import java.awt.image.BufferedImage

class NegativeImagePreprocessor: ImagePreprocessor {

    override fun preprocessImage(image: BufferedImage) {
        with(image) {
            for (i in 0 until  height) {
                for (j in 0 until width) {
                    val currentColor = getRGB(j, i)
                    setRGB(j, i, invertRgb(currentColor))
                }
            }
        }
    }

    private fun invertRgb(originalRgb: Int): Int {
        val originalColor = Color(originalRgb)
        val invertedRed = 255 - originalColor.red
        val invertedGreen = 255 - originalColor.green
        val invertedBlue = 255 - originalColor.blue
        return Color(invertedRed, invertedGreen, invertedBlue).rgb
    }
}