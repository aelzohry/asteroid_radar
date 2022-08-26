package com.udacity.aelzohry.asteroid_radar.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.aelzohry.asteroid_radar.data.database.entities.AsteroidEntity
import com.udacity.aelzohry.asteroid_radar.data.database.entities.PictureOfDayEntity

@Dao
interface AsteroidDao {
    @Query("SELECT * FROM picture_of_day WHERE id=0")
    fun getPictureOfDay(): LiveData<PictureOfDayEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPictureOfDay(picture: PictureOfDayEntity)

    @Query("SELECT * FROM asteroid ORDER BY close_approach_date")
    suspend fun getAllAsteroids(): List<AsteroidEntity>

    @Query("SELECT * FROM asteroid WHERE close_approach_date = :date ORDER BY close_approach_date")
    suspend fun getAsteroidsForDay(date: String): List<AsteroidEntity>

    @Query("SELECT * FROM asteroid WHERE close_approach_date BETWEEN :fromDate AND :toDate ORDER BY close_approach_date")
    suspend fun getAsteroidsFromDay(fromDate: String, toDate: String): List<AsteroidEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAsteroids(vararg asteroid: AsteroidEntity)

    @Query("DELETE FROM asteroid WHERE close_approach_date < :date")
    suspend fun deleteAsteroidsBefore(date: String)
}