package com.aprouxdev.familymediaplayer.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

data class MyMedia(val mediaUrl: String, val date: String, val artiste: String, val title: String)

class MainViewModel: ViewModel() {

    sealed class MediaUploadState {
        object Loading : MediaUploadState()
        data class UploadComplete(val medias: List<MyMedia>): MediaUploadState()
        data class Error(val message: String): MediaUploadState()
    }

    private var _mediaUploadState: MutableSharedFlow<MediaUploadState> = MutableSharedFlow()
    val mediaUploadState: SharedFlow<MediaUploadState> get() = _mediaUploadState


    init {
        getAllMedias()
    }

    /**
     * Mock of a api service to upload medias url
     */
    private fun getAllMedias() {
        viewModelScope.launch {
            try {
                // Simulate the API loading
                _mediaUploadState.emit(MediaUploadState.Loading)

                // Simulate the delay of network call
                delay(4000)

                // Mock data
                val medias = listOf(
                    MyMedia("https://www.youtube.com/watch?v=A4N_zbegHro", "2023-06-08", "Syd Matters", "Hi Life"),
                    MyMedia("https://www.youtube.com/watch?v=mvjDAtVTMUw", "2023-06-09", "Timewrap","Smoke miash"),
                    MyMedia("https://www.youtube.com/watch?v=z2X2HaTvkl8", "2023-06-10", "Balthazar","Fever"),
                    MyMedia("https://www.youtube.com/watch?v=CyT2wEGaSHA", "2023-06-11", "Lo-Fang", "#88 (Live at WFUV)"),
                    MyMedia("https://www.youtube.com/watch?v=OzpXfnhysWk", "2023-06-12", "Sofiane Pamart", "DEAR (Official Videoclip)"),
                    MyMedia("https://www.youtube.com/watch?v=OzpXfnhysWk", "2023-06-13", "Fellini Félin","Macadam"),
                    MyMedia("https://www.youtube.com/watch?v=3pHQuCezmLE", "2023-06-14", "Shawn Lee's Ping Pong Orchestra feat. Nino Mochella", "Kiss The Sky"),
                    )

                // Emit the mock data
                _mediaUploadState.emit(MediaUploadState.UploadComplete(medias))

            } catch (e: Exception) {
                // In case of any error
                _mediaUploadState.emit(MediaUploadState.Error("An error occurred while uploading media"))
            }
        }
    }

}