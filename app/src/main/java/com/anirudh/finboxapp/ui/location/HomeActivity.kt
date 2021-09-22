package com.anirudh.finboxapp.ui.location

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.anirudh.finboxapp.databinding.ActivityHomeBinding
import com.anirudh.finboxapp.global.LocationWorker
import com.anirudh.finboxapp.utils.ConstantUtils
import com.anirudh.finboxapp.utils.PreferenceUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {


    companion object {
        const val REQUEST_CODE_PERMISSION_LOCATION = 100

    }

    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var locationInfoAdapter: LocationInfoAdapter
    private val locationInfoViewModel: LocationInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startLocationUpdates()
        setUpLocationList()
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                REQUEST_CODE_PERMISSION_LOCATION
            )
        } else {
            if (!PreferenceUtils.hasRequestCreated(this)) {
                createLocationTrackRequest()
                PreferenceUtils.setRequestCreated(this, true)
            }

        }
    }


    private fun setUpLocationList() {
        val lm = LinearLayoutManager(this)
        lm.orientation = RecyclerView.VERTICAL
        binding.recyclerView.layoutManager = lm

        locationInfoAdapter = LocationInfoAdapter()
        binding.recyclerView.adapter = locationInfoAdapter


        lifecycleScope.launch {
            locationInfoViewModel.getAllInfo.collectLatest {
                locationInfoAdapter.submitData(it)
            }

        }
        locationInfoAdapter.addLoadStateListener { loadState ->
            if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && locationInfoAdapter.itemCount < 1) {
                binding.recyclerView.isVisible = false
                binding.layoutEmpty.isVisible = true
            } else {
                binding.recyclerView.isVisible = true
                binding.layoutEmpty.isVisible = false
            }
        }
    }



    private fun createLocationTrackRequest() {
        val work = PeriodicWorkRequestBuilder<LocationWorker>(
            1, TimeUnit.HOURS
        )
            .addTag(ConstantUtils.LOCATION_WORKER_TAG)
            .build()
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                ConstantUtils.LOCATION_WORKER_TAG,
                ExistingPeriodicWorkPolicy.KEEP,
                work
            )

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_PERMISSION_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    if (!PreferenceUtils.hasRequestCreated(this)) {
                        createLocationTrackRequest()
                        PreferenceUtils.setRequestCreated(this, true)
                    }
                }
                return
            }
        }
    }
}