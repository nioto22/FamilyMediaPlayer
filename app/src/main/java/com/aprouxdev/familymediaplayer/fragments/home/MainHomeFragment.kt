package com.aprouxdev.familymediaplayer.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.aprouxdev.familymediaplayer.databinding.FragmentMainHomeBinding
import com.aprouxdev.familymediaplayer.viewmodels.MainViewModel
import com.aprouxdev.familymediaplayer.viewmodels.MainViewModel.MediaUploadState
import com.aprouxdev.familymediaplayer.viewmodels.MainViewModel.MediaUploadState.Error
import com.aprouxdev.familymediaplayer.viewmodels.MainViewModel.MediaUploadState.Loading
import com.aprouxdev.familymediaplayer.viewmodels.MainViewModel.MediaUploadState.UploadComplete
import com.aprouxdev.familymediaplayer.viewmodels.MyMedia
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class MainHomeFragment : Fragment() {

    companion object {
        fun newInstance(): MainHomeFragment {
            val args = Bundle()

            val fragment = MainHomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private var _binding: FragmentMainHomeBinding? = null
    private val binding get() = requireNotNull(_binding)

    private lateinit var mViewModel: MainViewModel

    private var mMediaList: List<MyMedia> = emptyList()
    private lateinit var mAdapter: HomeMediaAdapter

    //region LIFECYCLE
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainHomeBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDataObservers()
        setupUiViews()
        setupUiListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    //endregion

    //region DATA OBSERVERS
    private fun setupDataObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // MediaState
                launch {
                    mViewModel.mediaUploadState.collect { mediaState ->
                        setupLoader(mediaState == Loading)
                        when(mediaState) {
                            is Error -> showError(mediaState.message)
                            Loading -> Unit
                            is UploadComplete -> {
                                val mMediaList = mediaState.medias
                                setupRecyclerView()
                            }
                        }

                    }
                }
            }
        }
    }



    //endregion


    //region UI METHODS
    private fun setupUiViews() {}

    private fun showError(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupLoader(isLoading: Boolean) {
        binding.mainHomeLoader.isVisible = isLoading
    }

    private fun setupRecyclerView() {

    }
    //endregion

    //region UI LISTENERS AND CLICKS
    private fun setupUiListeners() {


    }
    //endregion
}

