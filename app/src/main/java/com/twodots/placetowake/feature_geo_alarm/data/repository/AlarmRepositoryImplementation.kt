package com.twodots.placetowake.feature_geo_alarm.data.repository

import com.twodots.placetowake.feature_geo_alarm.data.data_source.AlarmDao
import com.twodots.placetowake.feature_geo_alarm.domain.model.Alarm
import com.twodots.placetowake.feature_geo_alarm.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow

class AlarmRepositoryImplementation(private val dao: AlarmDao) : AlarmRepository {
    override fun getAlarms(): Flow<List<Alarm>> {
        return dao.getAlarms()
    }

    override suspend fun getAlarmById(id: Int): Alarm? {
        return dao.getAlarmById(id)
    }

    override suspend fun insertAlarm(alarm: Alarm) {
        dao.insertAlarm(alarm)
    }

    override suspend fun deleteNote(alarm: Alarm) {
        dao.deleteAlarm(alarm)
    }

}