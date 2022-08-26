package com.udacity.aelzohry.asteroid_radar.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.aelzohry.asteroid_radar.domain.PictureOfDay

@Entity(tableName = "picture_of_day")
data class PictureOfDayEntity constructor(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "media_type") val mediaType: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "url") val url: String,
)

fun PictureOfDayEntity.asDomainModel(): PictureOfDay {
    return PictureOfDay(
        mediaType = mediaType,
        title = title,
        url = url
    )
}