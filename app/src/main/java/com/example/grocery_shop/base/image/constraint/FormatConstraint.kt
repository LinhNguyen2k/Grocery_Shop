package com.octalsoftaware.myapplication.utils.image.constraint

import android.graphics.Bitmap
import com.octalsoftaware.myapplication.utils.image.compressFormat
import com.octalsoftaware.myapplication.utils.image.loadBitmap
import com.octalsoftaware.myapplication.utils.image.overWrite
import java.io.File

class FormatConstraint(private val format: Bitmap.CompressFormat) : Constraint {

    override fun isSatisfied(imageFile: File): Boolean {
        return format == imageFile.compressFormat()
    }

    override fun satisfy(imageFile: File): File {
        return overWrite(imageFile, loadBitmap(imageFile), format)
    }
}

fun Compression.format(format: Bitmap.CompressFormat) {
    constraint(FormatConstraint(format))
}