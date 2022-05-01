package com.octalsoftaware.myapplication.utils.image.constraint

import java.io.File

interface Constraint {
    fun isSatisfied(imageFile: File): Boolean

    fun satisfy(imageFile: File): File
}