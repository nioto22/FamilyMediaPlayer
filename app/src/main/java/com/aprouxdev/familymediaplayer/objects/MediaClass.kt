package com.aprouxdev.familymediaplayer.objects



data class MediaCategory(val name: String, val samples: List<Sample>)
data class Sample(val name: String, val uri: String)

data class MyMedia(val mediaUrl: String, val date: String? = null, val artiste: String? = null, val title: String? = null)