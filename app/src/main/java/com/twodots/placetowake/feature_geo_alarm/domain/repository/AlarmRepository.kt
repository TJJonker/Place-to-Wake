package com.twodots.placetowake.feature_geo_alarm.domain.repository

import com.twodots.placetowake.feature_geo_alarm.domain.model.Alarm
import kotlinx.coroutines.flow.Flow

interface AlarmRepository {

    fun getAlarms(): Flow<List<Alarm>>

    suspend fun getAlarmById(id: Int): Alarm?

    suspend fun insertAlarm(alarm: Alarm)

    suspend fun deleteNote(alarm: Alarm)

}