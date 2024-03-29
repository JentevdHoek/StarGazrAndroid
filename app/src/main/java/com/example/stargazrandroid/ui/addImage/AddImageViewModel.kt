package com.example.stargazrandroid.ui.addImage

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.ViewModel
import com.example.stargazrandroid.model.FavoriteModel
import com.example.stargazrandroid.model.SavedItem
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddImageViewModel: ViewModel() {
    var imageSrc: Uri? = null
    var title: String? = null
    var description: String? = null

    fun save(context: Context): SavedItem? {
        if (imageSrc == null || title == null || description == null)
            return null

        val imageBitmap = imageToBitmap(context)
        return SavedItem(title!!, imageBitmap, description!!)
    }

    private fun imageToBitmap(context: Context): Bitmap {
        val inputStream = imageSrc?.let { context.contentResolver.openInputStream(it) }
        return BitmapFactory.decodeStream(inputStream)
    }

    private fun saveImage(context: Context): String {
        val input: InputStream? = context.contentResolver.openInputStream(imageSrc!!)

        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        val imageFile = File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
        )

        val out: OutputStream = FileOutputStream(imageFile)
        val buf = ByteArray(1024)
        var len: Int
        while (input!!.read(buf).also { len = it } > 0) {
            out.write(buf, 0, len)
        }
        out.close()
        input.close()

        return imageFile.absolutePath
    }
}