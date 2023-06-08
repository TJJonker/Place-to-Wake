package com.twodots.placetowake.feature_geo_alarm.presentation.alarms

import com.twodots.placetowake.feature_geo_alarm.domain.model.Alarm

sealed class AlarmsEvent {
    data class DeleteAlarm(val alarm: Alarm) : AlarmsEvent()
    data class AddAlarm(val alarm: Alarm) : AlarmsEvent()
    object RestoreNote : AlarmsEvent()
}