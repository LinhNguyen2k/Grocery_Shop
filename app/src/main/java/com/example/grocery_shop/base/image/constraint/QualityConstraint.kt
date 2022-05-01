package com.octalsoftaware.myapplication.utils.image.constraint

import com.octalsoftaware.myapplication.utils.image.loadBitmap
import com.octalsoftaware.myapplication.utils.image.overWrite
import java.io.File

class QualityConstraint(private val quality: Int) : Constraint {
    private var isResolved = false

    override fun isSatisfied(imageFile: File): Boolean {
        return isResolved
    }

    override fun satisfy(imageFile: File): File {
        val result = overWrite(imageFile, loadBitmap(imageFile), quality = quality)
        isResolved = true
        return result
    }
}

fun Compression.quality(quality: Int) {
    constraint(QualityConstraint(quality))
}

