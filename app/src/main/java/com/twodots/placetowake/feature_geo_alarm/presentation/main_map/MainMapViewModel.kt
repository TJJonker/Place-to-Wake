package com.twodots.placetowake.feature_geo_alarm.presentation.main_map

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.twodots.placetowake.feature_geo_alarm.domain.model.AlarmCirclePreview
import com.twodots.placetowake.feature_geo_alarm.domain.use_case.AlarmUseCases
import com.twodots.placetowake.feature_geo_alarm.presentation.util.permission.PermissionDialogManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.max
import kotlin.math.min

@HiltViewModel
class MainMapViewModel @Inject constructor(
    val permissionManager: PermissionDialogManager,
    private val application: Application,
    private val alarmUseCases: AlarmUseCases
) : ViewModel() {

    private val _state = mutableStateOf(MainMapState())
    val state: State<MainMapState> = _state

    private var getAlarmsJob: Job? = null

    var locationCallback: LocationCallback? = null

    init {
        _state.value = _state.value.copy(
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(
                application
            )
        )

        GetAlarms()
    }

    fun onEvent(event: MainMapEvent) {
        when (event) {
            is MainMapEvent.ToggleNewAlarmMenu -> {
                if (_state.value.selectedLocation == null)
                    return

                _state.value = _state.value.copy(createAlarmMenuOpened = event.open)

                if (!event.open)
                    return

                viewModelScope.launch {
                    _state.value.cameraPositionState.centerOnLocation(
                        _state.value.selectedLocation!!,
                        -.01f,
                        zoom = 13f
                    )
                }

                GetAlarms()
            }

            is MainMapEvent.OpenAlarms -> TODO()

            is MainMapEvent.OpenSettings -> {
                // Could be added when necessary
            }

            is MainMapEvent.SetMapPointer -> {
                _state.value = _state.value.copy(
                    selectedLocation = LatLng(event.latitude, event.longitude)
                )
            }

            is MainMapEvent.ZoomToUserLocation -> {
                viewModelScope.launch {
                    _state.value.cameraPositionState.centerOnLocation(
                        LatLng(
                            state.value.lastUserLocation!!.latitude,
                            state.value.lastUserLocation!!.longitude
                        )
                    )
                }
            }

            is MainMapEvent.RequestLocationPermission -> {
                when (ContextCompat.checkSelfPermission(
                    application,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )) {
                    PackageManager.PERMISSION_GRANTED -> {
                        getDeviceLocation(_state.value.fusedLocationProviderClient!!)
                        startLocationUpdates()
                    }

                    PackageManager.PERMISSION_DENIED -> {
                        event.permissionRequestLauncher.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION))
                    }
                }
            }

            is MainMapEvent.SetAlarmCirclePreview -> {
                _state.value = _state.value.copy(
                    alarmCirclePreview = AlarmCirclePreview(
                        event.latLng,
                        event.radius
                    )
                )
            }

            is MainMapEvent.SetMapLoaded -> _state.value =
                _state.value.copy(mapLoaded = event.loaded)

            MainMapEvent.GetAlarms -> {
                GetAlarms()
            }

            is MainMapEvent.SetLastUserLocation -> _state.value =
                _state.value.copy(lastLoopedUserLocation = event.location)

            is MainMapEvent.SetAlarmRinging -> _state.value = _state.value.copy(mapLoaded = event.isRinging)
            is MainMapEvent.DisableAlarm -> {
                viewModelScope.launch {
                    alarmUseCases.addAlarm(alarm = event.alarm.copy(isActive = false))
                }
            }
        }
    }

    private fun GetAlarms() {
        getAlarmsJob?.cancel()
        getAlarmsJob = alarmUseCases.getAlarms().onEach { alarms ->
            _state.value = state.value.copy(
                lisOfAlarms = alarms
            )
        }.launchIn(viewModelScope)
    }

    @SuppressLint("MissingPermission")
    fun getDeviceLocation(
        fusedLocationProviderClient: FusedLocationProviderClient
    ) {
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _state.value = _state.value.copy(
                        lastUserLocation = task.result,
                    )
                }
            }
        } catch (e: SecurityException) {
        }
    }

    private suspend fun CameraPositionState.centerOnLocation(
        location: LatLng,
        offsetY: Float = 0f,
        offsetX: Float = 0f,
        zoom: Float = 15f
    ) {
        animate(
            update = CameraUpdateFactory.newLatLngZoom(
                LatLng(location.latitude + offsetY, location.longitude + offsetX),
                zoom
            ),
        )
    }

    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        locationCallback?.let {
            val locationRequest = LocationRequest.create().apply {
                interval = 10000
                fastestInterval = 5000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }
            state.value.fusedLocationProviderClient?.requestLocationUpdates(
                locationRequest,
                it,
                Looper.getMainLooper()
            )
        }
    }
}