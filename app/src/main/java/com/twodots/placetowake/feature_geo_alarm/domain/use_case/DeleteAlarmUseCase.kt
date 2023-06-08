package com.twodots.placetowake.feature_geo_alarm.domain.use_case

import com.twodots.placetowake.feature_geo_alarm.domain.model.Alarm
import com.twodots.placetowake.feature_geo_alarm.domain.repository.AlarmRepository

class DeleteAlarmUseCase(
    private val repository: AlarmRepository
) {
    suspend operator fun invoke(alarm: Alarm){
        repository.deleteNote(alarm)
    }
}