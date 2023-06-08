package com.twodots.placetowake.feature_geo_alarm.presentation.main_map

import android.location.Location
import androidx.activity.compose.ManagedActivityResultLauncher
import com.google.android.gms.maps.model.LatLng
import com.twodots.placetowake.feature_geo_alarm.domain.model.Alarm

sealed class MainMapEvent {
    object OpenSettings : MainMapEvent()
    object OpenAlarms : MainMapEvent()
    data class ToggleNewAlarmMenu(val open: Boolean) : MainMapEvent()
    data class SetMapPointer(val latitude: Double, val longitude: Double) : MainMapEvent()
    data class SetAlarmCirclePreview(val latLng: LatLng, val radius: Double) : MainMapEvent()
    data class SetMapLoaded(val loaded: Boolean) : MainMapEvent()
    object ZoomToUserLocation : MainMapEvent()
    data class SetAlarmRinging(val isRinging: Boolean) : MainMapEvent()
    object GetAlarms : MainMapEvent()
    data class SetLastUserLocation(val location: LatLng) : MainMapEvent()
    data class RequestLocationPermission(
        val permissionRequestLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>
    ) : MainMapEvent()
    data class DisableAlarm(val alarm: Alarm) : MainMapEvent()
}