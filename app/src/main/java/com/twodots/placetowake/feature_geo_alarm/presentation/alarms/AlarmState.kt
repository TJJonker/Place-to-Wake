package com.twodots.placetowake.feature_geo_alarm.presentation.alarms

import com.twodots.placetowake.feature_geo_alarm.domain.model.Alarm

data class AlarmState(
    val alarms: List<Alarm> = emptyList()

    // Order logic could be added in here.
)
