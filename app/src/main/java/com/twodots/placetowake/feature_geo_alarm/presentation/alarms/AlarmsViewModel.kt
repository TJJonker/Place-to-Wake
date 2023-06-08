package com.twodots.placetowake.feature_geo_alarm.presentation.alarms

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.twodots.placetowake.feature_geo_alarm.domain.model.Alarm
import com.twodots.placetowake.feature_geo_alarm.domain.use_case.AlarmUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlarmsViewModel @Inject constructor(
    private val alarmUseCases: AlarmUseCases
) : ViewModel() {

    private val _state = mutableStateOf(AlarmState())
    val state: State<AlarmState> = _state

    private var getAlarmsJob: Job? = null

    private var recentlyDeletedAlarm: Alarm? = null

    init {
        GetAlarms()
    }

    private fun GetAlarms() {
        getAlarmsJob?.cancel()
        getAlarmsJob = alarmUseCases.getAlarms().onEach { alarms ->
            _state.value = state.value.copy(
                alarms = alarms
            )
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: AlarmsEvent) {
        when (event) {
            is AlarmsEvent.DeleteAlarm -> {
                viewModelScope.launch {
                    alarmUseCases.deleteAlarm(event.alarm)
                    recentlyDeletedAlarm = event.alarm
                }
            }

            is AlarmsEvent.RestoreNote -> {
                viewModelScope.launch {
                    alarmUseCases.addAlarm(recentlyDeletedAlarm ?: return@launch)
                    recentlyDeletedAlarm = null
                }
            }

            is AlarmsEvent.AddAlarm -> {
                viewModelScope.launch {
                    alarmUseCases.addAlarm(alarm = event.alarm)
                }
            }
        }
    }
}