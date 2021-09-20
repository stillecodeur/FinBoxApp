package com.anirudh.finboxapp.data

import android.content.Context
import androidx.room.Room
import com.anirudh.finboxapp.data.dao.LocationInfoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    fun provideLocationInfoDao(appDataBase: AppDataBase): LocationInfoDao =
        appDataBase.locationInfoDao()


    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): AppDataBase =
        Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            "finbox_db"
        ).build()

}