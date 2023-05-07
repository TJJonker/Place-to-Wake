package nl.TwoDots.placetowake.models.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import nl.TwoDots.placetowake.addalarm.AlarmTrigger

class AddAlarmViewModel : ViewModel() {
    private val _alarmTriggers = listOf(AlarmTrigger.OnEnter, AlarmTrigger.OnExit)

    val alarmName : MutableState<String> = mutableStateOf("")
}