package com.example.stargazrandroid.ui.addImage

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.stargazrandroid.databinding.FragmentAddImageBinding
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date

class AddImageFragment : Fragment() {
    private var _addImageViewModel: AddImageViewModel? = null
    private val addImageViewModel get() = _addImageViewModel!!

    private var _binding: FragmentAddImageBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val CAMERA = Manifest.permission.CAMERA
    }

    private val cameraPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                TakePicture()
            }
        }

    private var photoFile: File? = null
    private var photoURI: Uri? = null

    private val cameraActivity = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success && photoURI != null) {
            addImageViewModel.imageSrc = photoURI
            binding.imageView.setImageURI(photoURI)
        } else {
            Toast.makeText(requireContext(), "Failed to capture image", Toast.LENGTH_SHORT).show()
        }
    }

    private val imageActivity = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        if (uri != null) {
            addImageViewModel.imageSrc = uri
            binding.imageView.setImageURI(uri)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _addImageViewModel = ViewModelProvider(this).get(AddImageViewModel::class.java)

        _binding = FragmentAddImageBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setSaveButtonListener()
        setCameraButtonListener()
        setGalleryButtonListener()

        return root
    }

    private fun TakePicture() {
        if (isPermissionGranted()) {
            try {
                photoFile = createImageFile()
                photoURI = FileProvider.getUriForFile(
                    requireContext(),
                    "com.example.stargazrandroid.fileprovider",
                    photoFile!!
                )
                cameraActivity.launch(photoURI)
            } catch (ex: Exception) {
                Log.e("Take Picture", "Error creating image file", ex)
                Toast.makeText(requireContext(), "Error creating image file", Toast.LENGTH_SHORT).show()
            }
        } else {
            cameraPermissionRequest.launch(CAMERA)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val image = File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )
        return image
    }

    private fun isPermissionGranted() = ContextCompat.checkSelfPermission(
        requireContext(), CAMERA
    ) == PackageManager.PERMISSION_GRANTED

    private fun setSaveButtonListener() {
        binding.saveButton.setOnClickListener(){
            addImageViewModel.title = binding.editTitleText.text.toString()
            addImageViewModel.description = binding.editTitleText.text.toString()

            val model = addImageViewModel.save(requireContext())

            if (model != null)
            {
                val imagePath = model.imagePath
                Log.i("Image Save", imagePath)

//                binding.imageResView.setImageURI(Uri.parse("file://$imagePath"))
            }
        }
    }

    private fun setGalleryButtonListener() {
        binding.fromGalleryButton.setOnClickListener {
            imageActivity.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
    }

    private fun setCameraButtonListener() {
        binding.fromCameraButton.setOnClickListener {
            TakePicture()
        }
    }
}