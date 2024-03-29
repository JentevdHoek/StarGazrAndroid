package com.example.stargazrandroid.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stargazrandroid.model.SavedItem

class SharedViewModel : ViewModel() {
    val savedItems = MutableLiveData<List<SavedItem>>()

    fun addItem(newItem: SavedItem) {
        val currentSavedItems = savedItems.value ?: emptyList()
        val updatedSavedItems = currentSavedItems + newItem
        savedItems.value = updatedSavedItems
    }
}