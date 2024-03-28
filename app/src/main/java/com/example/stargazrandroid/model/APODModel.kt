package com.example.stargazrandroid.model
import android.util.Log
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.FileNotFoundException

data class APODModel(
    val date: String,
    var explanation: String,
    val hdurl: String?,
    val media_type: String,
    val service_version: String,
    var title: String,
    val url: String,
    val copyright: String?
)

suspend fun fetchAPODData(date: String?): APODModel = withContext(Dispatchers.IO) {
    val url = if (date != null) {
        URL("https://api.nasa.gov/planetary/apod?api_key=ZoGElbDxAmYlXchoGyTsjep2MiAmptdcxrNg15vk&date=$date")
    } else {
        URL("https://api.nasa.gov/planetary/apod?api_key=ZoGElbDxAmYlXchoGyTsjep2MiAmptdcxrNg15vk&count=1")
    }

    val connection = url.openConnection()
    val data: String
    try {
        data = connection.getInputStream().bufferedReader().use { it.readText() }
    } catch (e: FileNotFoundException) {
        Log.e("APODModel", "File not found: ${e.message}")
        return@withContext APODModel("", "", "", "", "", "", "", "")
    }

    val jsonObject = if (data.trim().startsWith("[")) {
        // Als data begint met "[", is het array, altijd maar 1 object in array
        val jsonArray = JSONArray(data)
        jsonArray.getJSONObject(0)
    } else {
        // anders object
        JSONObject(data)
    }

    APODModel(
        date = jsonObject.getString("date"),
        explanation = jsonObject.getString("explanation"),
        hdurl = jsonObject.getString("hdurl"),
        media_type = jsonObject.getString("media_type"),
        service_version = jsonObject.getString("service_version"),
        title = jsonObject.getString("title"),
        url = jsonObject.getString("url"),
        copyright = jsonObject.optString("copyright")
    )
}