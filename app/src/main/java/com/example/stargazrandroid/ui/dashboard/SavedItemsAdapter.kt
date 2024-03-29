package com.example.stargazrandroid.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.stargazrandroid.databinding.ItemSavedBinding
import com.example.stargazrandroid.model.SavedItem

class SavedItemsAdapter(
    private val savedItems: List<SavedItem>
) : RecyclerView.Adapter<SavedItemsAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemSavedBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSavedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val savedItem = savedItems[position]
        holder.binding.apply {
            imageView.setImageBitmap(savedItem.image)
            textViewTitle.text = savedItem.title
        }
    }

    override fun getItemCount() = savedItems.size
}