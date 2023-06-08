package com.twodots.placetowake.feature_geo_alarm.presentation.util

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri

class RingtonePicker(
    private val application: Application
) {

    val defaultRingtoneUri = RingtoneManager.getActualDefaultRingtoneUri(
        application,
        RingtoneManager.TYPE_ALARM
    )

    fun getRingtonePickerIntent(currentRingtoneUri: Uri = defaultRingtoneUri): Intent {
        val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER)
        intent.putExtra(
            RingtoneManager.EXTRA_RINGTONE_TYPE,
            RingtoneManager.TYPE_RINGTONE,
        )
        intent.putExtra(
            RingtoneManager.EXTRA_RINGTONE_TITLE,
            "Select a ringtone"
        )
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, defaultRingtoneUri)
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false)
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true)
        return intent
    }
}

