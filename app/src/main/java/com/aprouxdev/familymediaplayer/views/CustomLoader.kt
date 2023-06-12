package com.aprouxdev.familymediaplayer.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import android.widget.RelativeLayout
import com.aprouxdev.familymediaplayer.R
import com.aprouxdev.familymediaplayer.databinding.ViewVinylLoaderBinding


class CustomLoader @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RelativeLayout(context, attrs) {

    private lateinit var binding: ViewVinylLoaderBinding

    init {
        binding = ViewVinylLoaderBinding.inflate(LayoutInflater.from(context), this, true)

        setupViews()
    }

    private fun setupViews() {
        startVinylAnimation()
    }

    private fun startVinylAnimation() {
        binding.viewLoaderImage.startAnimation(AnimationUtils.loadAnimation(context, R.anim.loader_rotation))
    }

}