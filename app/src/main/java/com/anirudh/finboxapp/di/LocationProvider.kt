package com.anirudh.finboxapp.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocationProvider {

    @Provides
    @Singleton
    fun provideFusedLocationProvider(context: Context): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
}