package com.udacity.aelzohry.asteroid_radar.data.api

import com.udacity.aelzohry.asteroid_radar.domain.PictureOfDay
import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidService {
    @GET("planetary/apod")
    suspend fun getPictureOfTheDay(@Query("api_key") apiKey: String): PictureOfDay

    @GET("neo/rest/v1/feed")
    suspend fun getAsteroids(
        @Query("api_key") apiKey: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String? = null,
    ): String
}