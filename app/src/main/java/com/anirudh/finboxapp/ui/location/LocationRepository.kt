package com.anirudh.finboxapp.ui.location

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.anirudh.finboxapp.data.dao.LocationInfoDao
import com.anirudh.finboxapp.data.models.LocationInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class LocationRepository @Inject constructor(private val locationInfoDao: LocationInfoDao) {

    val allLocationInfo: LiveData<List<LocationInfo>> = locationInfoDao.getAllInfo()


    fun insert(locationInfo: LocationInfo) =
        GlobalScope.launch { locationInfoDao.insertInfo(locationInfo) }

}