package com.scbd.contact.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream


object ImageBitmapString {
    @TypeConverter
    fun bitMapToString(bitmap: Bitmap): String? {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, baos)
        val b: ByteArray = baos.toByteArray()
        val temp: String? = Base64.encodeToString(b, Base64.DEFAULT)
        return temp
    }

    @TypeConverter
    fun stringToBitMap(encodedString: String): Bitmap? {
        return try {
            val encoded = Base64.decode(encodedString, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(encoded, 0, encoded.size)
            bitmap
        } catch (e: Exception) {
            null
        }
    }
}