package com.example.stargazrandroid.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stargazrandroid.model.SavedItem

class SharedViewModel : ViewModel() {
    val savedItems = MutableLiveData<List<SavedItem>>()
}