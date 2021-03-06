package com.anirudh.finboxapp.data.dao

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anirudh.finboxapp.data.models.LocationInfo

@Dao
interface LocationInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInfo(locationInfo: LocationInfo)

    @Query("SELECT * FROM location_info")
    fun getAllInfo(): PagingSource<Int, LocationInfo>

    @Query("SELECT * FROM location_info")
    fun getAllInfoTest(): List<LocationInfo>

}