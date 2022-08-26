package com.udacity.aelzohry.asteroid_radar

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.aelzohry.asteroid_radar.data.database.AsteroidDatabase
import com.udacity.aelzohry.asteroid_radar.repository.AsteroidRepository
import com.udacity.aelzohry.asteroid_radar.util.DateUtil

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    private val database = AsteroidDatabase.getDatabase(applicationContext)
    private val repository = AsteroidRepository(database)

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        return try {
            fetchAsteroids()
            deleteOldAsteroids()
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private suspend fun fetchAsteroids() {
        repository.fetchAsteroids()
    }

    private suspend fun deleteOldAsteroids() {
        val date = DateUtil.getTodayFormatted()
        database.dao.deleteAsteroidsBefore(date)
    }

}
