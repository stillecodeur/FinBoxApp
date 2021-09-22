package com.anirudh.finboxapp

import android.content.Context
import android.util.Log
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.*
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.TestWorkerBuilder
import androidx.work.testing.WorkManagerTestInitHelper
import com.anirudh.finboxapp.global.LocationWorker
import com.anirudh.finboxapp.utils.ConstantUtils
import org.hamcrest.CoreMatchers.`is`
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.jvm.Throws

@RunWith(AndroidJUnit4::class)
class LocationWorkerTest {

    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val config = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()

        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)
    }

    @Test
    @Throws(Exception::class)
    fun testPeriodicWork() {

        val request = PeriodicWorkRequestBuilder<LocationWorker>(15, TimeUnit.MINUTES)
            .build()
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val workManager = WorkManager.getInstance(context)
        val testDriver = WorkManagerTestInitHelper.getTestDriver()
        workManager.enqueueUniquePeriodicWork(ConstantUtils.LOCATION_WORKER_TAG,ExistingPeriodicWorkPolicy.KEEP,request).result.get()
        testDriver?.setPeriodDelayMet(request.id)
        val workInfo = workManager.getWorkInfoById(request.id).get()
        assertThat(workInfo.state, `is`(WorkInfo.State.ENQUEUED))
    }

}