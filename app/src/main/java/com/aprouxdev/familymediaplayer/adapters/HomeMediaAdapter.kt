package com.aprouxdev.familymediaplayer.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Player.Listener
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.LoadControl
import androidx.recyclerview.widget.RecyclerView
import com.aprouxdev.familymediaplayer.R
import com.aprouxdev.familymediaplayer.databinding.ViewItemHomeMediaBinding
import com.aprouxdev.familymediaplayer.objects.MyMedia
import java.util.*


interface HomeMediaListener {
    fun onMyMediaClicked(item: MyMedia)
}


class HomeMediaAdapter(
    var data: List<MyMedia>,
    val listener: HomeMediaListener
) : RecyclerView.Adapter<HomeMediaAdapter.HomeMediaViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMediaViewHolder {
        return HomeMediaViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_item_home_media, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: HomeMediaViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            listener.onMyMediaClicked(item)
        }
    }

    fun updateData(data: List<MyMedia>) {
        this.data = data
        notifyDataSetChanged()
    }

    class HomeMediaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val binding = ViewItemHomeMediaBinding.bind(itemView)

        fun bind(item: MyMedia): View {
            val context = binding.root.context
            setupMediaPlayer(context, item.mediaUrl)

            with(binding) {
                homeItemTitle.text = item.title
                homeItemArtist.text = item.artiste
            }

            return binding.root
        }

        private fun setupMediaPlayer(context: Context, mediaUrl: String) {
            val player = ExoPlayer.Builder(context)
                .build()
            binding.homeItemPlayerView.player = player

            val mediaItem = MediaItem.fromUri(mediaUrl)
            player.setMediaItem(mediaItem)
            player.prepare()

            // Start the playback.
            //player.play()

            player.addListener(object: Listener {
                override fun onEvents(player: Player, events: Player.Events) {
                    super.onEvents(player, events)

                }

                override fun onRenderedFirstFrame() {
                    super.onRenderedFirstFrame()
                }
            })
        }
    }
}