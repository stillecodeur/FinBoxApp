package com.anirudh.finboxapp.global

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.anirudh.finboxapp.R
import com.anirudh.finboxapp.data.models.LocationInfo
import com.anirudh.finboxapp.location.NotificationManager
import com.anirudh.finboxapp.ui.location.LocationRepository
import com.google.android.gms.location.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LocationWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    private val context = ctx
    private val lw_params = params


    private lateinit var fusedLocationClient: FusedLocationProviderClient


    @SuppressLint("MissingPermission")
    override fun doWork(): Result {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        fusedLocationClient.lastLocation.addOnSuccessListener {
            val info=LocationInfo(it?.latitude ,it?.longitude,it?.time)
            val des = "Lat:" + it?.latitude + " - Lng:" + it?.longitude
            NotificationManager.notify(
                context, context.getString(R.string.location_tracking),
                des
            )
        }
        return Result.success()
    }


}