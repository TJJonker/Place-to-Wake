package com.twodots.placetowake.feature_geo_alarm.domain.use_case

import com.twodots.placetowake.feature_geo_alarm.domain.model.Alarm
import com.twodots.placetowake.feature_geo_alarm.domain.model.InvalidAlarmException
import com.twodots.placetowake.feature_geo_alarm.domain.repository.AlarmRepository

class AddAlarmUseCase(private val repository: AlarmRepository) {

    @Throws(InvalidAlarmException::class)
    suspend operator fun invoke(alarm: Alarm) : Boolean {

        if(alarm.title.isBlank()){
            return false
            //throw InvalidAlarmException("The title of the alarm cannot be empty.")
        }

        repository.insertAlarm(alarm)
        return true
    }
}