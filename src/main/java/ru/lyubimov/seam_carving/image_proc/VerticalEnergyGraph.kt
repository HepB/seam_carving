package ru.lyubimov.seam_carving.image_proc

import kotlin.math.min

class VerticalEnergyGraph(energyMap: Array<Array<Double>>) {

    private val graph: Array<Array<Double>>

    init {
        val result = Array(energyMap.size) { i ->
            Array(energyMap[i].size) { Double.MAX_VALUE }
        }

        for (i in energyMap.indices) {
            for (j in energyMap[i].indices) {
                result[i][j] = when {
                    i - 1 >= 0 && result[i].size == 1 -> energyMap[i][j] + result[i - 1][j]
                    i - 1 >= 0 && j - 1 >= 0 && j + 1 <= result[i].lastIndex -> {
                        val leftVertex = result[i - 1][j - 1]
                        val upVertex = result[i - 1][j]
                        val rightVertex = result[i - 1][j + 1]
                        val minWeight = min(min(leftVertex, upVertex), min(upVertex, rightVertex))
                        energyMap[i][j] + minWeight
                    }
                    i - 1 >= 0 && j + 1 <= result[i].lastIndex -> {
                        val upVertex = result[i - 1][j]
                        val rightVertex = result[i - 1][j + 1]
                        val minWeight = min(upVertex, rightVertex)
                        energyMap[i][j] + minWeight
                    }
                    i - 1 >= 0 && j - 1 >= 0 -> {
                        val leftVertex = result[i - 1][j - 1]
                        val upVertex = result[i - 1][j]
                        val minWeight = min(leftVertex, upVertex)
                        energyMap[i][j] + minWeight
                    }
                    else -> energyMap[i][j]
                }
            }
        }

        graph = result
    }

    fun shortestWay(): List<Pixel> {
        val result = mutableListOf<Pixel>()
        val bottomLine = graph.last()
        val min = bottomLine.minOrNull() ?: bottomLine[0]
        val initialPixel = Pixel(x = bottomLine.indexOf(min), y = graph.lastIndex)
        nextStep(initialPixel, result)
        return result
    }

    private fun nextStep(pixel: Pixel, shortestWay: MutableList<Pixel>): MutableList<Pixel> {
        shortestWay.add(pixel)
        return when {
            pixel.y - 1 >= 0 && pixel.x >= 1 && graph[pixel.y - 1].lastIndex >= pixel.x + 1 -> {
                val leftVertex = graph[pixel.y - 1][pixel.x - 1]
                val upVertex = graph[pixel.y - 1][pixel.x]
                val rightVertex = graph[pixel.y - 1][pixel.x + 1]
                return when(min(min(leftVertex, upVertex), min(upVertex, rightVertex))) {
                    leftVertex -> nextStep(Pixel( x = pixel.x - 1, y = pixel.y - 1 ), shortestWay)
                    upVertex -> nextStep(Pixel( x = pixel.x, y = pixel.y - 1 ), shortestWay)
                    else -> nextStep(Pixel( x = pixel.x + 1, y = pixel.y - 1 ), shortestWay)
                }
            }
            pixel.y - 1 >= 0 && pixel.x >= 1 -> {
                val leftVertex = graph[pixel.y - 1][pixel.x - 1]
                val upVertex = graph[pixel.y - 1][pixel.x]
                return when(min(leftVertex, upVertex)) {
                    leftVertex -> nextStep(Pixel( x = pixel.x - 1, y = pixel.y - 1 ), shortestWay)
                    else -> nextStep(Pixel( x = pixel.x, y = pixel.y - 1 ), shortestWay)
                }
            }
            pixel.y - 1 >= 0 && graph[pixel.y - 1].lastIndex >= pixel.x + 1 -> {
                val upVertex = graph[pixel.y - 1][pixel.x]
                val rightVertex = graph[pixel.y - 1][pixel.x + 1]
                return when(min(upVertex, rightVertex)) {
                    upVertex -> nextStep(Pixel( x = pixel.x, y = pixel.y - 1 ), shortestWay)
                    else -> nextStep(Pixel( x = pixel.x + 1, y = pixel.y - 1 ), shortestWay)
                }
            }
            else -> shortestWay
        }
    }
}