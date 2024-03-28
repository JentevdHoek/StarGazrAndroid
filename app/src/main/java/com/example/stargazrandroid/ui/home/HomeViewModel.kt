package com.example.stargazrandroid.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.stargazrandroid.model.APODModel
import com.example.stargazrandroid.model.fetchAPODData
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    val apodData = MutableLiveData<APODModel>()

    fun fetchData(date: String?) {
        viewModelScope.launch {
            apodData.value = fetchAPODData(date)
        }
    }
}