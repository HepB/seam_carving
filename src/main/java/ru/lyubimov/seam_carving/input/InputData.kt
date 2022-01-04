package ru.lyubimov.seam_carving.input

import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.lang.Exception
import javax.imageio.ImageIO

class InputData(
    val inputFilePath: String,
    val outputFilePath: String,
    val widthForRemove: Int,
    val heightForRemove: Int
) {

    fun getImage(): BufferedImage {
        val file = File(this.inputFilePath)
        if (!file.exists()) throw Exception("Input file not exist")
        try {
            return ImageIO.read(file)
        } catch (ex: IOException) {
            throw Exception("Input file data is not correct")
        }
    }
}