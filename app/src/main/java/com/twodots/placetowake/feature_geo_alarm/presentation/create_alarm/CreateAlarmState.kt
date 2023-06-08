package com.twodots.placetowake.feature_geo_alarm.presentation.create_alarm

import android.net.Uri

data class CreateAlarmState(
    val alarmName: String = "",
    val radius: Int = 50,
    val ringtoneUri: Uri,
    val ringtoneName: String
)