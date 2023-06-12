package com.aprouxdev.familymediaplayer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aprouxdev.familymediaplayer.R
import com.aprouxdev.familymediaplayer.viewmodels.MyMedia
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

        private val binding = VIewItemHomeMediaBinding.bind(itemView)

        fun bind(item: MyMedia): View {

        }
    }
}