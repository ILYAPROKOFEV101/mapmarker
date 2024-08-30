package com.ilya.mapmarker

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas

import android.graphics.drawable.BitmapDrawable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory


object MapMarkerUtils {

    fun bitmapDescriptorFromVector(
        context: Context,
        icon: Any,
        colorString: String,
        width: Int,
        height: Int
    ): BitmapDescriptor {
        when (icon) {
            is Int -> {
                val vectorDrawable = ContextCompat.getDrawable(context, icon)
                vectorDrawable?.let {
                    val drawable = DrawableCompat.wrap(it).mutate()

                    val color = Color.fromHex(colorString).toArgb() // Конвертация цвета

                    DrawableCompat.setTint(drawable, color)
                    drawable.setBounds(0, 0, width, height)
                    val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                    val canvas = Canvas(bitmap)
                    drawable.draw(canvas)
                    return BitmapDescriptorFactory.fromBitmap(bitmap)
                }
            }
            is BitmapDrawable -> {
                val bitmap = Bitmap.createScaledBitmap(icon.bitmap, width, height, false)
                return BitmapDescriptorFactory.fromBitmap(bitmap)
            }
            else -> {
                // Обработка других типов источников, если необходимо
            }
        }
        return BitmapDescriptorFactory.defaultMarker()
    }

    fun Color.Companion.fromHex(colorString: String): Color {
        val colorWithoutHash = colorString.removePrefix("#")

        if (colorWithoutHash.length != 6 && colorWithoutHash.length != 8) {
            throw IllegalArgumentException("Invalid hex color string: $colorString")
        }

        val color = android.graphics.Color.parseColor("#$colorWithoutHash")
        return Color(color)
    }
}
