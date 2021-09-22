package com.anirudh.finboxapp.ui.location

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.paging.*
import com.anirudh.finboxapp.data.dao.LocationInfoDao
import com.anirudh.finboxapp.data.models.LocationInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class LocationRepository @Inject constructor(private val locationInfoDao: LocationInfoDao) {

    val allLocationInfo: Flow<PagingData<LocationInfo>> =
        Pager(
            PagingConfig(pageSize = 20, enablePlaceholders = false)
        ) {
            locationInfoDao.getAllInfo()
        }.flow


    fun insert(locationInfo: LocationInfo) =
        GlobalScope.launch { locationInfoDao.insertInfo(locationInfo) }


}