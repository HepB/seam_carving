package ru.lyubimov.seam_carving.ext

import ru.lyubimov.seam_carving.file.FileType
import ru.lyubimov.seam_carving.image_proc.Pixel
import ru.lyubimov.seam_carving.input.InputData
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import java.io.IOException
import java.lang.Exception
import javax.imageio.ImageIO

fun BufferedImage.saveImage(data: InputData, fileType: FileType = FileType.PNG) {
    val file = File(data.outputFilePath)
    try {
        ImageIO.write(this, fileType.name, file)
    } catch (ex: IOException) {
        throw Exception("Can't save output image")
    }
}

fun BufferedImage.rotate(degree: Int): BufferedImage {
    val width: Int = this.width
    val height: Int = this.height

    val result: BufferedImage =
        when (degree) {
            90, 270 -> BufferedImage(
                height,
                width,
                type
            )
            180 -> BufferedImage(width, height, type)
            else -> return this
        }

    when (degree) {
        90 -> {
            for (i in 0 until width) {
                for (j in 0 until height) {
                    result.setRGB(height - j - 1, i, this.getRGB(i, j))
                }
            }
        }
        180 -> {
            for (i in 0 until width) {
                for (j in 0 until height) {
                    result.setRGB(width - i - 1, height - j - 1, this.getRGB(i, j))
                }
            }
        }
        270 -> {
            for (i in 0 until width) {
                for (j in 0 until height) {
                    result.setRGB(j, width - i - 1, this.getRGB(i, j))
                }
            }
        }
    }
    this.flush()
    return result
}

fun BufferedImage.removeColumn(shortestWay: List<Pixel>): BufferedImage {
    val width: Int = this.width - 1
    val height: Int = this.height
    val result = BufferedImage(width, height, type)
    for (i in result.yIndexes()) {
        var isPixelWithLowestEnergyFunded = false
        for (j in result.xIndexes()) {
            if (!isPixelWithLowestEnergyFunded) {
                isPixelWithLowestEnergyFunded = shortestWay.firstOrNull { it == Pixel(x = j, y = i) } != null
            }
            if (isPixelWithLowestEnergyFunded) {
                result.setRGB(j, i, getRGB(j + 1, i))
            } else {
                result.setRGB(j, i, getRGB(j, i))
            }
        }
    }
    this.flush()
    return result
}

fun BufferedImage.xIndexes() = 0 until width

fun BufferedImage.yIndexes() = 0 until height

fun BufferedImage.maxXIndex() = width - 1

fun BufferedImage.maxYIndex() = height - 1

fun BufferedImage.getColor(x: Int, y: Int) = Color(this.getRGB(x, y))