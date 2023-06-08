package com.twodots.placetowake.feature_geo_alarm.domain.use_case

import com.twodots.placetowake.feature_geo_alarm.domain.model.Alarm
import com.twodots.placetowake.feature_geo_alarm.domain.repository.AlarmRepository
import kotlinx.coroutines.flow.Flow

class GetAlarmsUseCase(private val repository: AlarmRepository) {
    operator fun invoke() : Flow<List<Alarm>> {
        return repository.getAlarms()
    }
}