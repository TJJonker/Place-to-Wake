package com.twodots.placetowake.feature_geo_alarm.presentation.main_map

import android.location.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.twodots.placetowake.feature_geo_alarm.domain.model.Alarm
import com.twodots.placetowake.feature_geo_alarm.domain.model.AlarmCirclePreview

data class MainMapState(
    val createAlarmMenuOpened: Boolean = false,
    val lastUserLocation: Location? = null,
    val selectedLocation: LatLng? = null,
    val fusedLocationProviderClient: FusedLocationProviderClient? = null,
    val cameraPositionState: CameraPositionState = CameraPositionState(),
    val alarmCirclePreview: AlarmCirclePreview? = null,
    val mapLoaded: Boolean = false,
    val isAlarmRinging: Boolean = false,
    val lisOfAlarms: List<Alarm> = emptyList(),
    val lastLoopedUserLocation: LatLng = LatLng(0.0, 0.0)
)
