package com.bteamcoding.vehicledetection.core.utils

import android.content.Context
import android.net.Uri

fun uriToByteArray(
    context: Context,
    uri: Uri
): ByteArray? {
    return context.contentResolver.openInputStream(uri)?.use { inputStream ->
        inputStream.readBytes()
    }
}
