package com.miniweather.service.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.miniweather.model.Weather

@Dao
interface WeatherDao {

    @Query("SELECT * FROM weather WHERE weather.latitude = (:lat) AND weather.longitude = (:lon) AND weather.timestamp > (:maxAge) LIMIT 1")
    suspend fun getCachedData(lat: Double, lon: Double, maxAge: Long): List<Weather>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntoCache(weather: Weather)

    @Query("DELETE FROM weather WHERE weather.timestamp < (:maxAge)")
    suspend fun deleteInvalidCaches(maxAge: Long)

}
