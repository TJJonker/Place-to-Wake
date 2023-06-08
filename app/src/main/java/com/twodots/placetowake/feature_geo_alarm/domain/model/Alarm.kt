package com.twodots.placetowake.feature_geo_alarm.domain.model

import android.media.Ringtone
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import java.sql.Timestamp

@Entity
data class Alarm(
    val title: String,
    val timestamp: Long,
    val latitude: Double,
    val longitude: Double,
    val ringtoneUri: String,
    val range: Int,
    val isActive: Boolean,
    @PrimaryKey val id: Int? = null
)

class InvalidAlarmException(message: String): Exception(message)
