package com.anirudh.finboxapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anirudh.finboxapp.data.dao.LocationInfoDao
import com.anirudh.finboxapp.data.models.LocationInfo

@Database(entities = arrayOf(LocationInfo::class), version = 1)
abstract class AppDataBase:RoomDatabase() {

    abstract fun locationInfoDao():LocationInfoDao

}