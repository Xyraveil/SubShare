package com.xyraveil.subshare.data.repository

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File
import java.io.IOException

object ImageRepository {

    private const val CLOUD_NAME = "ddghntrlg"
    private const val UPLOAD_PRESET = "SubShare"

    fun uploadImage(
        context: Context,
        imageUri: Uri,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {

        val inputStream = context.contentResolver.openInputStream(imageUri)
            ?: run {
                onFailure("Couldn't open image")
                return
            }

        val tempFile = File.createTempFile(
            "upload",
            ".jpg",
            context.cacheDir
        )

        tempFile.outputStream().use {
            inputStream.copyTo(it)
        }

        inputStream.close()

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "file",
                tempFile.name,
                tempFile.asRequestBody("image/*".toMediaTypeOrNull())
            )
            .addFormDataPart(
                "upload_preset",
                UPLOAD_PRESET
            )
            .build()

        val request = Request.Builder()
            .url("https://api.cloudinary.com/v1_1/$CLOUD_NAME/image/upload")
            .post(requestBody)
            .build()

        OkHttpClient().newCall(request).enqueue(object : okhttp3.Callback {

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                onFailure(e.message ?: "Upload failed")
            }

            override fun onResponse(
                call: okhttp3.Call,
                response: okhttp3.Response
            ) {

                val body = response.body?.string()

                if (body == null) {
                    onFailure("Empty response")
                    return
                }

                val imageUrl =
                    JSONObject(body).getString("secure_url")

                onSuccess(imageUrl)

            }

        })

    }
}