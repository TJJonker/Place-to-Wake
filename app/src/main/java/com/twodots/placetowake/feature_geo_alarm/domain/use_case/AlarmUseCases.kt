package com.twodots.placetowake.feature_geo_alarm.domain.use_case

data class AlarmUseCases(
    val getAlarms : GetAlarmsUseCase,
    val addAlarm : AddAlarmUseCase,
    val deleteAlarm: DeleteAlarmUseCase
)
