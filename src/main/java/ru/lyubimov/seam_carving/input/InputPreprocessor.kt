package ru.lyubimov.seam_carving.input

import java.lang.Exception
import java.lang.IndexOutOfBoundsException

const val ARG_INPUT = "-in"
const val ARG_OUTPUT = "-out"
const val ARG_WIDTH = "-width"
const val ARG_HEIGHT = "-height"

object InputPreprocessor {

    fun readInputData(args: Array<String>): InputData {
        var inputFilePath= ""
        var outputFilePath= ""
        var widthForRemove = 0
        var heightForRemove = 0

        for (index in args.indices step 2) {
            when (args[index]) {
                ARG_INPUT -> {
                    try {
                        inputFilePath = args[index + 1]
                    } catch (ex: IndexOutOfBoundsException) {
                        throw Exception("Enter input file path")
                    }
                }
                ARG_OUTPUT -> {
                    try {
                        outputFilePath = args[index + 1]
                    } catch (ex: IndexOutOfBoundsException) {
                        throw Exception("Enter output file path")
                    }
                }
                ARG_WIDTH -> {
                    try {
                        widthForRemove = args[index + 1].toInt()
                    } catch (ex: IndexOutOfBoundsException) {
                        throw Exception("Enter correct width for remove")
                    }
                }
                ARG_HEIGHT -> {
                    try {
                        heightForRemove = args[index + 1].toInt()
                    } catch (ex: IndexOutOfBoundsException) {
                        throw Exception("Enter correct height for remove")
                    }
                }
                else -> throw Exception("Incorrect argument")
            }
        }

        return InputData(
            inputFilePath = inputFilePath,
            outputFilePath = outputFilePath,
            widthForRemove = widthForRemove,
            heightForRemove = heightForRemove
        )
    }
}