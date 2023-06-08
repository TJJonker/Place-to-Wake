package com.twodots.placetowake.feature_geo_alarm.presentation.create_alarm

import android.app.Application
import android.media.RingtoneManager
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.twodots.placetowake.feature_geo_alarm.domain.model.Alarm
import com.twodots.placetowake.feature_geo_alarm.domain.use_case.AlarmUseCases
import com.twodots.placetowake.feature_geo_alarm.presentation.util.RingtonePicker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateAlarmViewModel @Inject constructor(
    private val alarmUseCases: AlarmUseCases,
    val ringtonePicker: RingtonePicker,
    private val application: Application
) : ViewModel() {

    private val ringtoneName = RingtoneManager.getRingtone(application, ringtonePicker.defaultRingtoneUri)
        .getTitle(application)
    private val _state =
        mutableStateOf(CreateAlarmState(ringtoneUri = ringtonePicker.defaultRingtoneUri, ringtoneName = ringtoneName))
    val state: State<CreateAlarmState> = _state

    @OptIn(ExperimentalMaterial3Api::class)
    fun onEvent(event: CreateAlarmEvents) {
        when (event) {
            is CreateAlarmEvents.CloseMenu -> viewModelScope.launch { event.sheetState.hide() }
            is CreateAlarmEvents.SaveAlarm -> {
                viewModelScope.launch {
                    if (alarmUseCases.addAlarm(
                            createAlarm(
                                false,
                                event.latLng.latitude,
                                event.latLng.longitude
                            )
                        )
                    )
                        event.coroutineScope.launch {
                            event.sheetState.hide()
                        }
                    //_state.value = CreateAlarmState(ringtoneUri = ringtonePicker.defaultRingtoneUri)
                }
            }

            is CreateAlarmEvents.StartAlarm -> {
                viewModelScope.launch {
                    if (alarmUseCases.addAlarm(
                            createAlarm(
                                true,
                                event.latLng.latitude,
                                event.latLng.longitude
                            )
                        )
                    )
                        event.coroutineScope.launch {
                            event.sheetState.hide()
                        }
                    //_state.value = CreateAlarmState(ringtoneUri = ringtonePicker.defaultRingtoneUri)
                }
            }

            is CreateAlarmEvents.UpdateName -> _state.value =
                _state.value.copy(alarmName = event.name)

            is CreateAlarmEvents.UpdateRadius -> _state.value =
                _state.value.copy(radius = event.radius)

            is CreateAlarmEvents.UpdateRingtone -> _state.value =
                _state.value.copy(ringtoneUri = event.ringtoneUri, ringtoneName = event.ringtoneName)
        }
    }

    private fun createAlarm(isActive: Boolean, latitude: Double, longitude: Double): Alarm {
        return Alarm(
            title = state.value.alarmName,
            timestamp = System.currentTimeMillis(),
            latitude = latitude,
            longitude = longitude,
            ringtoneUri = state.value.ringtoneUri.toString(),
            range = state.value.radius,
            isActive = isActive,
        )
    }
}