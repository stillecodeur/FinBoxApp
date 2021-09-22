package com.anirudh.finboxapp

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import com.anirudh.finboxapp.databinding.ActivityHomeBinding
import com.anirudh.finboxapp.global.LocationWorker
import com.anirudh.finboxapp.ui.location.LocationInfoAdapter
import com.anirudh.finboxapp.ui.location.LocationInfoViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {


    companion object {
        const val REQUEST_CODE_PERMISSION_LOCATION = 100
    }

    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!
    private var locationInfoAdapter: LocationInfoAdapter?=null
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
            createLocationTrackRequest()
        }
    }



    private fun setUpLocationList() {
        val lm = LinearLayoutManager(this)
        lm.orientation = RecyclerView.VERTICAL
        binding.recyclerView.layoutManager = lm
        locationInfoViewModel.getAllInfo.observe(this, Observer {
            if (it != null) {
                if (locationInfoAdapter == null) {
                    locationInfoAdapter = LocationInfoAdapter(it)
                    binding.recyclerView.adapter=locationInfoAdapter
                }

                locationInfoAdapter?.notifyDataSetChanged()
            }
        })
    }

    private fun createLocationTrackRequest() {
//        val work = PeriodicWorkRequestBuilder<LocationWorker>(15, TimeUnit.MINUTES)
//            .build()
//        WorkManager.getInstance(this)
//            .enqueueUniquePeriodicWork("location_job", ExistingPeriodicWorkPolicy.KEEP, work);

        val uploadWorkRequest: WorkRequest =
            OneTimeWorkRequestBuilder<LocationWorker>()
                .build()

        WorkManager.getInstance(this).enqueue(uploadWorkRequest)

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
                    createLocationTrackRequest()
                }
                return
            }
        }
    }
}