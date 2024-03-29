package com.twodots.placetowake.feature_geo_alarm.data.data_source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.twodots.placetowake.feature_geo_alarm.domain.model.Alarm
import kotlinx.coroutines.flow.Flow

@Dao
interface AlarmDao {

    @Query("SELECT * FROM alarm")
    fun getAlarms() : Flow<List<Alarm>>

    @Query("SELECT * FROM alarm WHERE id = :id")
    suspend fun getAlarmById(id: Int) : Alarm?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarm(alarm: Alarm)

    @Delete
    suspend fun deleteAlarm(alarm: Alarm)

}