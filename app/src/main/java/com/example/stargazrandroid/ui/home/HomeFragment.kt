package com.example.stargazrandroid.ui.home

import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.stargazrandroid.R
import com.example.stargazrandroid.databinding.FragmentHomeBinding
import com.example.stargazrandroid.databinding.SearchViewBinding
import com.example.stargazrandroid.model.SavedItem
import com.example.stargazrandroid.ui.dashboard.SharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var searchViewBinding: SearchViewBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val relativeLayout = binding.root.findViewById<RelativeLayout>(R.id.relative_layout)
        searchViewBinding = SearchViewBinding.bind(relativeLayout)

        val currentDate = Calendar.getInstance()
        searchViewBinding.datePicker.maxDate = currentDate.timeInMillis

        val minDate = Calendar.getInstance()
        minDate.set(1995, 5, 20)
        searchViewBinding.datePicker.minDate = minDate.timeInMillis

        binding.buttonViewDetails.visibility = View.GONE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchViewBinding.toggleDatePicker.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                searchViewBinding.datePicker.visibility = View.VISIBLE
            } else {
                searchViewBinding.datePicker.visibility = View.GONE
            }
        }

        searchViewBinding.buttonFetchData.setOnClickListener {
            val formattedDate = if (searchViewBinding.datePicker.visibility == View.VISIBLE) {
                val year = searchViewBinding.datePicker.year
                val month = searchViewBinding.datePicker.month
                val day = searchViewBinding.datePicker.dayOfMonth

                val calendar = Calendar.getInstance()
                calendar.set(year, month, day)

                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.US)
                sdf.format(calendar.time)
            } else {
                null
            }

            homeViewModel.fetchData(formattedDate)
        }

        homeViewModel.apodData.observe(viewLifecycleOwner) { data ->
            binding.textViewTitle.text = data.title
            Thread {
                val url = URL(data.url)
                val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                activity?.runOnUiThread {
                    if (_binding == null)
                        return@runOnUiThread
                    binding.imageViewApod.setImageBitmap(bmp)
                    //toon save knop
                    binding.buttonViewDetails.visibility = View.VISIBLE
                }
            }.start()
            binding.textViewDescription.text = data.explanation


            binding.buttonViewDetails.setOnClickListener {
                lifecycleScope.launch(Dispatchers.IO) {
                    val url = URL(data.url)
                    val bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream())

                    val savedItem = SavedItem(
                        title = data.title,
                        image = bmp,
                        description = data.explanation
                    )

                    val currentSavedItems = sharedViewModel.savedItems.value ?: emptyList()

                    val updatedSavedItems = currentSavedItems + savedItem

                    withContext(Dispatchers.Main) {
                        sharedViewModel.savedItems.value = updatedSavedItems
                    }
                }
            }
        }

        binding.buttonViewDetails.setOnClickListener {
            val title = binding.textViewTitle.text.toString()
            val image = (binding.imageViewApod.drawable as BitmapDrawable).bitmap
            val description = binding.textViewDescription.text.toString()

            val item = SavedItem(title, image, description)
            homeViewModel.saveItem(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}