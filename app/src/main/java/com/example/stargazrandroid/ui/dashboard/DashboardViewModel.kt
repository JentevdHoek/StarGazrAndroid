package com.example.stargazrandroid.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stargazrandroid.model.APODModel

class DashboardViewModel : ViewModel() {
    val favorites = listOf(APODModel(
            date = "27-03-2024",
            explanation = "Very much start",
            hdurl = null,
            media_type = "picture",
            service_version = "2",
            title = "Starry",
            url = "https://apod.nasa.gov/apod/image/2403/ComaCluster_Hua_960.jpg",
            copyright = null
        ),
        APODModel(
            date = "27-03-2024",
            explanation = "Very much start",
            hdurl = null,
            media_type = "picture",
            service_version = "2",
            title = "Starry",
            url = "https://apod.nasa.gov/apod/image/2403/ComaCluster_Hua_960.jpg",
            copyright = null
        )
    )
}