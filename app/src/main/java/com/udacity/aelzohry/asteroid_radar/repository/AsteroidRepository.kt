package com.udacity.aelzohry.asteroid_radar.repository

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.udacity.aelzohry.asteroid_radar.data.api.WebService
import com.udacity.aelzohry.asteroid_radar.data.api.parseAsteroidsJsonResult
import com.udacity.aelzohry.asteroid_radar.data.database.AsteroidDatabase
import com.udacity.aelzohry.asteroid_radar.data.database.entities.AsteroidEntity
import com.udacity.aelzohry.asteroid_radar.data.database.entities.PictureOfDayEntity
import com.udacity.aelzohry.asteroid_radar.data.database.entities.asDomainModel
import com.udacity.aelzohry.asteroid_radar.util.Constants
import com.udacity.aelzohry.asteroid_radar.util.DateUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository(database: AsteroidDatabase) {

    private val dao = database.dao
    private val webService = WebService.asteroidService

    val pictureOfDay = Transformations.map(dao.getPictureOfDay()) {
        it?.asDomainModel()
    }

    private var _asteroids = MutableLiveData<List<AsteroidEntity>>()
    val asteroids = Transformations.map(_asteroids) {
        it?.asDomainModel()
    }

    suspend fun fetchPictureOfDay() {
        withContext(Dispatchers.IO) {
            val pictureOfDay = webService.getPictureOfTheDay(Constants.API_KEY)
            dao.insertPictureOfDay(PictureOfDayEntity(
                id = 0,
                mediaType = pictureOfDay.mediaType,
                title = pictureOfDay.title,
                url = pictureOfDay.url
            ))
        }
    }

    suspend fun fetchAsteroids() {
        withContext(Dispatchers.IO) {
            val date = DateUtil.getTodayFormatted()
            val asteroidsString =
                webService.getAsteroids(Constants.API_KEY, date)
            val asteroidsJson = JSONObject(asteroidsString)
            val asteroids = parseAsteroidsJsonResult(asteroidsJson).toTypedArray()
            dao.insertAllAsteroids(*asteroids)
        }
    }

    suspend fun loadAllAsteroids() {
        _asteroids.value = dao.getAllAsteroids()
    }

    suspend fun loadTodayAsteroids() {
        val date = DateUtil.getTodayFormatted()
        _asteroids.value = dao.getAsteroidsForDay(date)
    }

    suspend fun loadWeekAsteroids() {
        val fromDate = DateUtil.getTodayFormatted()
        val toDate = DateUtil.getSevenDaysLaterFormatted()
        _asteroids.value = dao.getAsteroidsFromDay(fromDate, toDate)
    }

}