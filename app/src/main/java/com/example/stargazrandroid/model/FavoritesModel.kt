package com.example.stargazrandroid.model

import android.net.Uri

data class FavoriteModel(
    val explanation: String,
    val media_type: String,
    val title: String,
    val imagePath: String,
)

data class FavoritesModel(
    val favorites: Array<FavoriteModel>
)