package ru.lyubimov.seam_carving

import ru.lyubimov.seam_carving.ext.rotate
import ru.lyubimov.seam_carving.ext.saveImage
import ru.lyubimov.seam_carving.image_proc.VerticalSeamPreprocessor
import ru.lyubimov.seam_carving.input.InputPreprocessor


fun main(args: Array<String>) {
    val inputData = InputPreprocessor.readInputData(args)
    // val preprocessor: ImagePreprocessor = NegativePreprocessor()
    // val preprocessor: ImagePreprocessor = PixelEnergyPreprocessor()
    val preprocessor = VerticalSeamPreprocessor()
    var image = preprocessor.removeColumns(inputData.getImage(), inputData.widthForRemove)
    image = image.rotate(90)
    image = preprocessor.removeColumns(image, inputData.heightForRemove)
    image = image.rotate(270)
    image.saveImage(inputData)
}