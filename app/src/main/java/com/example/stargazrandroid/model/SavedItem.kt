package com.example.stargazrandroid.model

import android.graphics.Bitmap

data class SavedItem(
    val title: String,
    val image: Bitmap,
    val description: String
)