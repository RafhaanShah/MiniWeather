package com.miniweather.service.database

import com.miniweather.testutil.FakeDataProvider
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DatabaseServiceTest {

    @Mock
    private lateinit var mockWeatherDao: WeatherDao

    private lateinit var databaseService: DatabaseService

    private val testDispatcher = TestCoroutineDispatcher()

    private val fakeLat = 1.111
    private val fakeLon = 2.222
    private val fakeTimestamp = 1000L
    private val fakeWeather = FakeDataProvider.provideFakeWeather()

    @Before
    fun setup() {
        databaseService = DatabaseService(mockWeatherDao, testDispatcher)
    }

    @After
    fun tearDown() {
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun whenGetCachedData_callsDao() = runBlockingTest {
        whenever(mockWeatherDao.getCachedData(any(), any(), any())).thenReturn(listOf(fakeWeather))

        val actual = databaseService.getCachedData(fakeLat, fakeLon, fakeTimestamp)

        verify(mockWeatherDao).getCachedData(fakeLat, fakeLon, fakeTimestamp)
        assertEquals(listOf(fakeWeather), actual)
    }

    @Test
    fun whenInsertIntoCache_callsDao() = runBlockingTest {
        databaseService.insertIntoCache(fakeWeather)

        verify(mockWeatherDao).insertIntoCache(fakeWeather)
    }

    @Test
    fun whenDeleteInvalidCaches_callsDao() = runBlockingTest {
        databaseService.deleteInvalidCaches(fakeTimestamp)

        verify(mockWeatherDao).deleteInvalidCaches(fakeTimestamp)
    }

}