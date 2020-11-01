package com.miniweather.testutil

import com.miniweather.BuildConfig
import com.miniweather.model.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val fakeWeather = Weather(
    "Sunny",
    22,
    17,
    "East",
    "London, UK",
    BuildConfig.IMAGE_BASE_URL + "01d" + ".png",
    1000,
    1.11,
    2.22,
)

val fakeWeatherResponse = WeatherResponse(
    weatherList = listOf(
        Condition(
            "Sunny",
            "01d"
        )
    ),
    temp = Temperature(22.toDouble()),
    wind = Wind(17.toDouble(), 70.0),
    location = "London, UK"
)

val fakeLocation = Location(1.111, 2.222)

val fakeLocationRounded = Location(1.11, 2.22)

val fakeCardinalDirections = arrayOf(
    "North",
    "North East",
    "East",
    "South East",
    "South",
    "South West",
    "West",
    "North West",
    "North"
)

const val fakeTimestamp: Long = 1000L

const val fakeError = "Something went wrong"

val fakeWeatherResponseJson by lazy { Json.encodeToString(fakeWeatherResponse) }
