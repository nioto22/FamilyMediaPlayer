package com.aprouxdev.familymediaplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.aprouxdev.familymediaplayer.databinding.ActivityMainBinding
import com.aprouxdev.familymediaplayer.objects.MyMedia

@UnstableApi class MainActivity : AppCompatActivity() {

    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private var mBottomVideoPlayer: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()
        mBottomVideoPlayer?.playWhenReady = true
    }

    override fun onStop() {
        super.onStop()
        mBottomVideoPlayer?.playWhenReady = false
        if (isFinishing) {
            releasePlayer()
        }
    }

    // TODO CREATE EXTENSION OR SERVICE FOR MEDIA CONTROLLER
    fun showMediaPlayer(item: MyMedia) {

        setupMediaPlayer(item.mediaUrl)

        with(binding) {
            homeItemTitle.text = item.title
            homeItemArtist.text = item.artiste


            // TODO ANIMATE BOTTOM PLAYER VISIBILITY
            mainBottomContainer.isVisible = true
        }
    }

    private fun setupMediaPlayer(mediaUrl: String) {
        // Create an ExoPlayer instance
        mBottomVideoPlayer = ExoPlayer.Builder(this)
            .build()
            .also {
                binding.homeItemPlayerView.player = it
            }


        val mediaItem = MediaItem.Builder()
            .setUri(mediaUrl)
            .setMimeType(MimeTypes.APPLICATION_MP4)
            .build()

        // Create a media source and pass the media item
        buildMediaSource(mediaItem)?.let {
            mBottomVideoPlayer?.setMediaItem(mediaItem)
            mBottomVideoPlayer?.prepare()
        }


        // Start the playback.
        mBottomVideoPlayer?.playWhenReady = true

        mBottomVideoPlayer?.addListener(object : Player.Listener {
            override fun onEvents(player: Player, events: Player.Events) {
                super.onEvents(player, events)
                // TODO
            }

            override fun onRenderedFirstFrame() {
                super.onRenderedFirstFrame()

            }
        })
    }

    private fun buildMediaSource(mediaItem: MediaItem): MediaSource? {
        return ProgressiveMediaSource.Factory(
            DefaultDataSource.Factory(this)
        ).createMediaSource(mediaItem)
    }

    private fun releasePlayer() {
        mBottomVideoPlayer?.release()
    }
}