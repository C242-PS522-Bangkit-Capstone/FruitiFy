package com.capstone.frutify.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.channels.FileChannel

class FruitClassifierHelper(private val context: Context) {

    private val model: Interpreter by lazy {
        val assetFileDescriptor = context.assets.openFd("fruitify_model.tflite")
        val fileInputStream = FileInputStream(assetFileDescriptor.fileDescriptor)
        val fileChannel = fileInputStream.channel
        val startOffset = assetFileDescriptor.startOffset
        val declaredLength = assetFileDescriptor.declaredLength
        val byteBuffer = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
        Interpreter(byteBuffer)
    }

    fun classifyFruit(imageUri: Uri, selectedFruit: String): Pair<String, Float>? {
        try {
            val inputTensorShape = model.getInputTensor(0).shape()
            val inputHeight = inputTensorShape[1]
            val inputWidth = inputTensorShape[2]

            val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(imageUri))
            val resizedBitmap = Bitmap.createScaledBitmap(bitmap, inputWidth, inputHeight, true)

            val byteBuffer = preprocessImage(resizedBitmap)

            // Prepare output buffer
            val outputShape = model.getOutputTensor(0).shape() // Example: [1, 16]
            val output = Array(outputShape[0]) { FloatArray(outputShape[1]) }

            // Run inference
            model.run(byteBuffer, output)

            // Get confidence scores
            val confidenceScores = output[0]
            if (confidenceScores.size != 16) {
                Log.e("FruitClassifierHelper", "Unexpected output size: ${confidenceScores.size}")
                return null
            }

            // Map output index to labels
            val labels = listOf(
                "Fresh Apple", "Fresh Banana", "Fresh Cucumber", "Fresh Orange", "Fresh Peach",
                "Fresh Pomegranate", "Fresh Strawberry", "Fresh Tomato", "Stale Apple", "Stale Banana",
                "Stale Cucumber", "Stale Orange", "Stale Peach", "Stale Pomegranate", "Stale Strawberry",
                "Stale Tomato"
            )

            if (labels.size != confidenceScores.size) {
                Log.e("FruitClassifierHelper", "Label size mismatch with output size")
                return null
            }

            // Filter labels based on the selected fruit
            val relevantLabels = labels.filter { it.contains(selectedFruit, ignoreCase = true) }
            if (relevantLabels.isEmpty()) {
                Log.e("FruitClassifierHelper", "No matching labels for the selected fruit: $selectedFruit")
                return null
            }

            // Determine the index of the relevant labels
            val relevantIndices = labels.indices.filter { labels[it] in relevantLabels }

            // Find the label with the highest confidence among the relevant ones
            val maxIndex = relevantIndices.maxByOrNull { confidenceScores[it] } ?: return null
            val maxConfidence = confidenceScores[maxIndex]

            return Pair(labels[maxIndex], maxConfidence)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


    private fun preprocessImage(bitmap: Bitmap): ByteBuffer {
        val inputTensorShape = model.getInputTensor(0).shape() // Example: [1, 224, 224, 3]
        val inputHeight = inputTensorShape[1]
        val inputWidth = inputTensorShape[2]
        val inputChannels = inputTensorShape[3]

        val byteBuffer = ByteBuffer.allocateDirect(inputHeight * inputWidth * inputChannels * 4) // 4 bytes per float
        byteBuffer.order(ByteOrder.nativeOrder())

        for (y in 0 until inputHeight) {
            for (x in 0 until inputWidth) {
                val pixel = bitmap.getPixel(x, y)
                byteBuffer.putFloat((pixel shr 16 and 0xFF) / 255.0f) // Red
                byteBuffer.putFloat((pixel shr 8 and 0xFF) / 255.0f)  // Green
                byteBuffer.putFloat((pixel and 0xFF) / 255.0f)       // Blue
            }
        }
        return byteBuffer
    }
}