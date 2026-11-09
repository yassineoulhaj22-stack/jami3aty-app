package com.jami3ty.app.ui.common

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter

/** Renders a Code 128 barcode for the given text. */
fun barcodeBitmap(text: String, width: Int, height: Int): Bitmap {
    val matrix = MultiFormatWriter().encode(text, BarcodeFormat.CODE_128, width, height)
    val pixels = IntArray(width * height) { i ->
        if (matrix.get(i % width, i / width)) Color.BLACK else Color.WHITE
    }
    return Bitmap.createBitmap(pixels, width, height, Bitmap.Config.ARGB_8888)
}
