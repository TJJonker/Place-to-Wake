package com.twodots.placetowake.feature_geo_alarm.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.twodots.placetowake.feature_geo_alarm.domain.model.Alarm

@Database(
    entities = [Alarm::class],
    version = 1
)
abstract class AlarmDatabase : RoomDatabase() {
    abstract val alarmDao: AlarmDao

    companion object{
        const val DATABASE_NAME = "alarms_database"
    }
}