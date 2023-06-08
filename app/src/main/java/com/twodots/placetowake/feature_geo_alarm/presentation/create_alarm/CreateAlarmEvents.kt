@file:OptIn(ExperimentalMaterial3Api::class)

package com.twodots.placetowake.feature_geo_alarm.presentation.create_alarm

import android.net.Uri
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart

sealed class CreateAlarmEvents {
    data class UpdateName(val name: String) : CreateAlarmEvents()
    data class UpdateRadius(val radius: Int) : CreateAlarmEvents()
    data class UpdateRingtone(val ringtoneUri: Uri, val ringtoneName: String) : CreateAlarmEvents()
    data class CloseMenu(val sheetState: SheetState) : CreateAlarmEvents()
    data class SaveAlarm(
        val sheetState: SheetState,
        val latLng: LatLng,
        val coroutineScope: CoroutineScope
    ) : CreateAlarmEvents()

    data class StartAlarm(
        val sheetState: SheetState,
        val latLng: LatLng,
        val coroutineScope: CoroutineScope
    ) : CreateAlarmEvents()
}