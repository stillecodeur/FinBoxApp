package com.anirudh.finboxapp.ui.location

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.anirudh.finboxapp.data.dao.LocationInfoDao
import com.anirudh.finboxapp.data.models.LocationInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class LocationRepository @Inject constructor(private val locationInfoDao: LocationInfoDao) {

    val allLocationInfo: LiveData<List<LocationInfo>> = locationInfoDao.getAllInfo()

    @WorkerThread
    suspend fun insert(locationInfo: LocationInfo) = withContext(Dispatchers.IO) {
        locationInfoDao.insertInfo(locationInfo)
    }


}