package com.anirudh.finboxapp

import com.anirudh.finboxapp.utils.AppUtils
import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class DateFormatTest {

    @Test
    fun isDateFormatCorrect(){
        val date = AppUtils.convertLongToTime(1632299681696)
        assertEquals(date,"22-Sep-2021 02:04:41")
    }
}