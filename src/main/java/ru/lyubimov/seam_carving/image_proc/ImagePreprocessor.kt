package ru.lyubimov.seam_carving.image_proc

import java.awt.image.BufferedImage

interface ImagePreprocessor {
    fun preprocessImage(image: BufferedImage)
}