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
import androidx.work.*
import com.anirudh.finboxapp.R
import com.anirudh.finboxapp.data.models.LocationInfo
import com.anirudh.finboxapp.location.NotificationManager
import com.anirudh.finboxapp.ui.location.LocationRepository
import com.anirudh.finboxapp.utils.ConstantUtils
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
        fusedLocationClient.lastLocation.addOnSuccessListener {
            if (it != null) {
                val lastLocation = it
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

        return Result.success()
    }


    override fun onStopped() {
        super.onStopped()
        NotificationManager.cancel(context)
    }
}