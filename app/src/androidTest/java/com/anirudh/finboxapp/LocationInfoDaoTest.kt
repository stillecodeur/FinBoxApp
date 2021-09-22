package com.anirudh.finboxapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anirudh.finboxapp.data.models.LocationInfo
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocationInfoDaoTest : DatabaseTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun insertLocationInfoTest() {
        val location = LocationInfo(latitude = 23.66, longitude = 75.12, time = 1632299681696)
        GlobalScope.launch {
            appDataBase.locationInfoDao().insertInfo(location)
        }
        val infoSize = appDataBase.locationInfoDao().getAllInfoTest().size
        assertEquals(infoSize, 1)
    }

}