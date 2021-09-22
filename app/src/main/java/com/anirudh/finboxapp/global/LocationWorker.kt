package com.anirudh.finboxapp.global

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.hilt.work.HiltWorker
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.anirudh.finboxapp.R
import com.anirudh.finboxapp.data.models.LocationInfo
import com.anirudh.finboxapp.location.NotificationManager
import com.anirudh.finboxapp.ui.location.LocationRepository
import com.google.android.gms.location.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Provider

@HiltWorker
class LocationWorker @AssistedInject constructor(
    @Assisted ctx: Context,
    @Assisted params: WorkerParameters,
) : Worker(ctx, params) {

    private val context = ctx
    private val lw_params = params


    private lateinit var fusedLocationClient: FusedLocationProviderClient

    @Inject
    lateinit var repository: LocationRepository
    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest

    @SuppressLint("MissingPermission")
    override fun doWork(): Result {

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        setUpLocationRequest()
        setUpLocationUpdatesCallback()
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
        return Result.success()
    }

    private fun setUpLocationRequest() {
        locationRequest = LocationRequest.create().apply {
            interval = TimeUnit.SECONDS.toMillis(60)
            fastestInterval = TimeUnit.SECONDS.toMillis(30)
            maxWaitTime = TimeUnit.MINUTES.toMillis(2)
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    @SuppressLint("MissingPermission")
    private fun setUpLocationUpdatesCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null) {
                    val lastLocation = locationResult.lastLocation
                    val info = LocationInfo(
                        lastLocation.latitude,
                        lastLocation.longitude,
                        lastLocation.time
                    )
                    repository.insert(info)
                    val des = "Lat:" + lastLocation.latitude + " - Lng:" + lastLocation.longitude
                    NotificationManager.notify(
                        context, context.getString(R.string.location_tracked_success_msg),
                        des
                    )
                } else {
                    Log.e("LocationTrackerService", "Location not found")
                }
            }
        }
    }

}