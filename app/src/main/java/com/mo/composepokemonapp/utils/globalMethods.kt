package com.mo.composepokemonapp.utils

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.imageResource
import androidx.palette.graphics.Palette

fun calculateDominantColor(drawable: Drawable, onFinish: (Color) -> Unit) {
    val bitmap = (drawable as BitmapDrawable).bitmap.copy(Bitmap.Config.ARGB_8888, true)
    Palette.from(bitmap).generate { palette ->
        palette?.dominantSwatch?.rgb?.let { color ->
            onFinish(Color(color))
        } ?: onFinish(Color.White)
    }
}
