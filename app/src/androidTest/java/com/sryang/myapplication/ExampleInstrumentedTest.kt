package com.sryang.myapplication

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.sryang.myapplication", appContext.packageName)
    }

    @Test
    fun test() {
        /*val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val feedRepository = FeedRepositoryImpl(appContext)

        runBlocking {
            feedRepository.loadFeed()
        }*/
    }

    @Test
    fun login() {
        /*val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        val loginrepository = LoginRepositoryImpl(appContext)

        runBlocking {
            val loggedInUserData = LoggedInUserData(userId = 4)
            loginrepository.setLoggedInUser(loggedInUserData)
        }*/
    }
}