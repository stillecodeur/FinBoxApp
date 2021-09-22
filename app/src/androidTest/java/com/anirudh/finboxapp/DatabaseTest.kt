package com.anirudh.finboxapp

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.anirudh.finboxapp.data.AppDataBase
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)

abstract class DatabaseTest {

    protected lateinit var appDataBase: AppDataBase

    @Before
    fun initDatabase(){
        appDataBase= Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDataBase::class.java).build()
    }

    @After
    @Throws(IOException::class)
    fun closeDatabase(){
        appDataBase.close()
    }
}