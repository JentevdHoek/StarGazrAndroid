package com.example.stargazrandroid.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.stargazrandroid.databinding.FragmentSavedItemsBinding
import com.example.stargazrandroid.model.SavedItem
import com.example.stargazrandroid.ui.home.HomeFragment

class SavedItemsFragment : Fragment() {

    private lateinit var binding: FragmentSavedItemsBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedItemsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Observe the savedItems in the shared ViewModel
        sharedViewModel.savedItems.observe(viewLifecycleOwner) { savedItems ->
            // Set up the RecyclerView
            binding.recyclerView.layoutManager = LinearLayoutManager(context)
            binding.recyclerView.adapter = SavedItemsAdapter(savedItems)
        }
    }
}