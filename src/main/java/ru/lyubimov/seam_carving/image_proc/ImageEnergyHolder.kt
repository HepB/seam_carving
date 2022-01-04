package ru.lyubimov.seam_carving.image_proc

import ru.lyubimov.seam_carving.ext.getColor
import ru.lyubimov.seam_carving.ext.maxXIndex
import ru.lyubimov.seam_carving.ext.maxYIndex
import java.awt.Color
import java.awt.image.BufferedImage
import kotlin.math.pow
import kotlin.math.sqrt

class ImageEnergyHolder(image: BufferedImage) {
    val energyMap: Array<Array<Double>>
    var maxEnergyOfImage: Double = 0.0

    init {
        energyMap = Array(image.height) { i ->
            Array(image.width) { j ->
                val isTopLeft = i == 0 && j == 0
                val isBottomRight = i == image.maxYIndex() && j == image.maxXIndex()
                val isTopRight = i == 0 && j == image.maxXIndex()
                val isBottomLeft = i == image.maxYIndex() && j == 0
                val isRightCorner = i < image.maxYIndex() && i > 0 && j == image.maxXIndex()
                val isLeftCorner = i < image.maxYIndex() && i > 0 && j == 0
                val isTopCorner = i == 0 && j < image.maxXIndex() && j > 0
                val isBottomCorner = i == image.maxYIndex() && j < image.maxXIndex() && j > 0
                when {
                    isTopLeft -> {
                        val energy = energy(
                            firstX = image.getColor(j, i),
                            secondX = image.getColor(j + 2, i),
                            firstY = image.getColor(j, i),
                            secondY = image.getColor(j, i + 2)
                        )
                        if (energy > maxEnergyOfImage) maxEnergyOfImage = energy
                        energy
                    }
                    isBottomRight -> {
                        val energy = energy(
                            firstX = image.getColor(j - 2, i),
                            secondX = image.getColor(j, i),
                            firstY = image.getColor(j, i - 2),
                            secondY = image.getColor(j, i)
                        )
                        if (energy > maxEnergyOfImage) maxEnergyOfImage = energy
                        energy
                    }
                    isBottomLeft -> {
                        val energy = energy(
                            firstX = image.getColor(j, i),
                            secondX = image.getColor(j + 2, i),
                            firstY = image.getColor(j, i - 2),
                            secondY = image.getColor(j, i)
                        )
                        if (energy > maxEnergyOfImage) maxEnergyOfImage = energy
                        energy
                    }
                    isTopRight -> {
                        val energy = energy(
                            firstX = image.getColor(j - 2, i),
                            secondX = image.getColor(j, i),
                            firstY = image.getColor(j, i),
                            secondY = image.getColor(j, i + 2)
                        )
                        if (energy > maxEnergyOfImage) maxEnergyOfImage = energy
                        energy
                    }
                    isRightCorner -> {
                        val energy = energy(
                            firstX = image.getColor(j - 2, i),
                            secondX = image.getColor(j, i),
                            firstY = image.getColor(j, i - 1),
                            secondY = image.getColor(j, i + 1)
                        )
                        if (energy > maxEnergyOfImage) maxEnergyOfImage = energy
                        energy
                    }
                    isLeftCorner -> {
                        val energy = energy(
                            firstX = image.getColor(j, i),
                            secondX = image.getColor(j + 2, i),
                            firstY = image.getColor(j, i - 1),
                            secondY = image.getColor(j, i + 1)
                        )
                        if (energy > maxEnergyOfImage) maxEnergyOfImage = energy
                        energy
                    }
                    isBottomCorner -> {
                        val energy = energy(
                            firstX = image.getColor(j - 1, i),
                            secondX = image.getColor(j + 1, i),
                            firstY = image.getColor(j, i - 2),
                            secondY = image.getColor(j, i)
                        )
                        if (energy > maxEnergyOfImage) maxEnergyOfImage = energy
                        energy
                    }
                    isTopCorner -> {
                        val energy = energy(
                            firstX = image.getColor(j - 1, i),
                            secondX = image.getColor(j + 1, i),
                            firstY = image.getColor(j, i),
                            secondY = image.getColor(j, i + 2)
                        )
                        if (energy > maxEnergyOfImage) maxEnergyOfImage = energy
                        energy
                    }
                    else -> {
                        val energy = energy(
                            firstX = image.getColor(j - 1, i),
                            secondX = image.getColor(j + 1, i),
                            firstY = image.getColor(j, i - 1),
                            secondY = image.getColor(j, i + 1)
                        )
                        if (energy > maxEnergyOfImage) maxEnergyOfImage = energy
                        energy
                    }
                }
            }
        }
    }

    private fun energy(firstX: Color, secondX: Color, firstY: Color, secondY: Color) =
        sqrt(gradient(firstX, secondX) + gradient(firstY, secondY))

    private fun gradient(first: Color, second: Color) =
        diffPow(first.red, second.red) + diffPow(first.green, second.green) + diffPow(first.blue, second.blue)

    private fun diffPow(a: Int, b: Int) =
        (a - b).toDouble().pow(2)
}