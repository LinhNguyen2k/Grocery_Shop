package com.octalsoftaware.myapplication.utils.image

import android.content.Context
import com.octalsoftaware.myapplication.utils.image.constraint.Compression
import com.octalsoftaware.myapplication.utils.image.constraint.default
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.coroutines.CoroutineContext

object Compressor {
    suspend fun compress(
        context: Context,
        imageFile: File,
        coroutineContext: CoroutineContext = Dispatchers.IO,
        compressionPatch: Compression.() -> Unit = { default() }
    ) = withContext(coroutineContext) {
        val compression = Compression().apply(compressionPatch)
        var result = context.copyToCache(imageFile)
        compression.constraints.forEach { constraint ->
            while (constraint.isSatisfied(result).not()) {
                result = constraint.satisfy(result)
            }
        }
        return@withContext result
    }
}