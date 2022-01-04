package ru.lyubimov.seam_carving.image_proc

import ru.lyubimov.seam_carving.ext.removeColumn
import java.awt.Color
import java.awt.image.BufferedImage

class VerticalSeamPreprocessor: ImagePreprocessor {

    override fun preprocessImage(image: BufferedImage) {
        val imageEnergyMap = ImageEnergyHolder(image).energyMap
        val verticalEnergyGraph = VerticalEnergyGraph(imageEnergyMap)
        val shortestWay = verticalEnergyGraph.shortestWay()
        shortestWay.forEach { image.setRGB(it.x, it.y, Color.red.rgb) }
    }

    fun removeColumns(image: BufferedImage, countOfColumns: Int): BufferedImage {
        var imageEnergyMap = ImageEnergyHolder(image).energyMap
        var tempImage = image
        for (i in 0 until countOfColumns) {
            val verticalEnergyGraph = VerticalEnergyGraph(imageEnergyMap)
            val shortestWay = verticalEnergyGraph.shortestWay()
            tempImage = tempImage.removeColumn(shortestWay)
            imageEnergyMap = ImageEnergyHolder(tempImage).energyMap
        }
        return tempImage
    }
}