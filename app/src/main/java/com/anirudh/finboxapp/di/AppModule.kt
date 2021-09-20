package com.anirudh.finboxapp.di

import com.anirudh.finboxapp.data.dao.LocationInfoDao
import com.anirudh.finboxapp.ui.location.LocationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun providesUserRepository(locationInfoDao: LocationInfoDao) : LocationRepository
            = LocationRepository(locationInfoDao)
}