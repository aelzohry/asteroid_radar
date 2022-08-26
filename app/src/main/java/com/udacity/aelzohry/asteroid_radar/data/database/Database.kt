package com.udacity.aelzohry.asteroid_radar.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.udacity.aelzohry.asteroid_radar.data.database.entities.AsteroidEntity
import com.udacity.aelzohry.asteroid_radar.data.database.entities.PictureOfDayEntity

@Database(
    entities = [AsteroidEntity::class, PictureOfDayEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val dao: AsteroidDao

    companion object {
        private lateinit var INSTANCE: AsteroidDatabase

        fun getDatabase(context: Context): AsteroidDatabase {
            synchronized(AsteroidDatabase::class.java) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AsteroidDatabase::class.java, "asteroids")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return INSTANCE
            }
        }
    }
}