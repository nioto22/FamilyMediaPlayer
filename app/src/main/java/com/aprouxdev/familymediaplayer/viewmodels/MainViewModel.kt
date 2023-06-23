package com.aprouxdev.familymediaplayer.viewmodels

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.util.UnstableApi
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.aprouxdev.familymediaplayer.R
import com.aprouxdev.familymediaplayer.objects.MediaCategory
import com.aprouxdev.familymediaplayer.objects.MyMedia
import com.aprouxdev.familymediaplayer.service.VideoPreloadWorker
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStreamReader


@UnstableApi class MainViewModel: ViewModel() {

    sealed class MediaUploadState {
        object Loading : MediaUploadState()
        data class UploadComplete(val medias: List<MyMedia>): MediaUploadState()
        data class Error(val message: String): MediaUploadState()
    }

    private var _mediaUploadState: MutableSharedFlow<MediaUploadState> = MutableSharedFlow()
    val mediaUploadState: SharedFlow<MediaUploadState> get() = _mediaUploadState


    /**
     * Mock of a api service to upload medias url
     */
    fun getAllMedias(context: Context) {
        viewModelScope.launch {
            try {
                // Simulate the API loading
                _mediaUploadState.emit(MediaUploadState.Loading)

                // Simulate the delay of network call
                delay(4000)

                // Mock data
//                val medias = listOf(
//                    MyMedia("https://storage.googleapis.com/exoplayer-test-media-0/BigBuckBunny_320x180.mp4", "2023-06-08", "Syd Matters", "Hi Life"),
//                    MyMedia("https://www.youtube.com/watch?v=mvjDAtVTMUw.mp4", "2023-06-09", "Timewrap","Smoke miash"),
//                    MyMedia("https://www.youtube.com/watch?v=z2X2HaTvkl8", "2023-06-10", "Balthazar","Fever"),
//                    MyMedia("https://www.youtube.com/watch?v=CyT2wEGaSHA", "2023-06-11", "Lo-Fang", "#88 (Live at WFUV)"),
//                    MyMedia("https://www.youtube.com/watch?v=OzpXfnhysWk", "2023-06-12", "Sofiane Pamart", "DEAR (Official Videoclip)"),
//                    MyMedia("https://www.youtube.com/watch?v=OzpXfnhysWk", "2023-06-13", "Fellini Félin","Macadam"),
//                    MyMedia("https://www.youtube.com/watch?v=3pHQuCezmLE", "2023-06-14", "Shawn Lee's Ping Pong Orchestra feat. Nino Mochella", "Kiss The Sky"),
//                    MyMedia("https://www.youtube.com/watch?v=aMZ4QL0orw0", "2023-06-15", "Michael Kiwanuka", "Love & Hate (Live Session)"),
//                    MyMedia("https://www.youtube.com/watch?v=aMZ4QL0orw0", "2023-06-16", "Ibeyi", "Away Away"),
//                    )
                val medias = loadMediaFromAssetsExample(context)
                //preloadAllMedias(context, medias)

                // Emit the mock data
                medias?.let {
                    _mediaUploadState.emit(MediaUploadState.UploadComplete(it))
                } ?: kotlin.run {
                    _mediaUploadState.emit(MediaUploadState.Error(message = context.getString(R.string.home_error_no_media)))
                }
            } catch (e: Exception) {
                // In case of any error
                _mediaUploadState.emit(MediaUploadState.Error(message = context.getString(R.string.home_error_no_media)))
            }
        }
    }

    private fun preloadAllMedias(context: Context, medias: List<MyMedia>?) {
        medias?.forEach {
            schedulePreloadWork(context, it.mediaUrl)
        }
    }

    /**
     * Schedule the preloading work
     */
    private fun schedulePreloadWork(context: Context, mediaUrl: String) {
        val workManager = WorkManager.getInstance(context)
        val videoPreloadWorker = VideoPreloadWorker.buildWorkRequest(mediaUrl)
        workManager.enqueueUniqueWork(
            "VideoPreloadWorker",
            ExistingWorkPolicy.KEEP,
            videoPreloadWorker
        )
    }

    private fun loadMediaFromAssetsExample(context: Context): List<MyMedia>? {
        return try {
            val inputStream = context.assets.open("media.exolist.json")
            val reader = InputStreamReader(inputStream)
            val gson = Gson()

            // Parse JSON
            val typeToken = object : TypeToken<List<MediaCategory>>() {}.type
            val mediaCategories: List<MediaCategory> = gson.fromJson(reader, typeToken)

            // Filter and Convert to MyMedia list
            mediaCategories.firstOrNull { it.name == "Misc" }?.samples?.map {
                MyMedia(mediaUrl = it.uri, title = it.name)
            }

        } catch (e: IOException) {
            // Handle exception
            e.printStackTrace()
            null
        }
    }

}