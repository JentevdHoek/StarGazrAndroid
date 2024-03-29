package com.example.stargazrandroid.ui.addImage

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.stargazrandroid.databinding.FragmentAddImageBinding
import com.example.stargazrandroid.model.FavoriteModel

class AddImageFragment : Fragment() {
    private var _addImageViewModel: AddImageViewModel? = null
    private val addImageViewModel get() = _addImageViewModel!!

    private var _binding: FragmentAddImageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _addImageViewModel = ViewModelProvider(this).get(AddImageViewModel::class.java)

        _binding = FragmentAddImageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setSaveListener()
        setImage()

        return root
    }

    private fun setSaveListener() {
        binding.saveButton.setOnClickListener(){
            addImageViewModel.title = binding.editTitleText.text.toString()
            addImageViewModel.description = binding.editTitleText.text.toString()

            var model: FavoriteModel? = null

            context?.let { it1 -> model = addImageViewModel.save(it1) }

            if (model != null)
            {
                val imagePath = model!!.imagePath
                Log.i("Image Save", imagePath)

                binding.imageResView.setImageURI(Uri.parse("file://$imagePath"))
            }
        }
    }

    fun setImage() {
        val getImage = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {uri: Uri? ->
            if (uri != null) {
                addImageViewModel.imageSrc = uri
                binding.imageView.setImageURI(uri)
            }
        }

        getImage.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }
}