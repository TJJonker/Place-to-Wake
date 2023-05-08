package nl.TwoDots.placetowake.models.viewmodel

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import nl.TwoDots.placetowake.addalarm.AlarmTrigger
import nl.TwoDots.placetowake.utils.RingtonePicker

class AddAlarmViewModel(context: Context) : ViewModel() {

    /**
     * Reference to the ringtonePicker object.
     */
    private val _ringtonePicker : MutableState<RingtonePicker> = mutableStateOf(RingtonePicker(context))

    /**
     * Public getter for the [_ringtonePicker].
     */
    val ringtonePicker : RingtonePicker = _ringtonePicker.value




    //region alarm triggers
    /**
     * A list containing both [AlarmTrigger.OnEnter] and [AlarmTrigger.OnExit].
     */
    val alarmTriggers = listOf(AlarmTrigger.OnEnter, AlarmTrigger.OnExit)

    /**
     * The current alarm trigger for the newly made alarm.
     */
    private val _currentAlarmTrigger : MutableState<AlarmTrigger> = mutableStateOf(AlarmTrigger.OnEnter)

    /**
     * A public getter, returning the value of [_currentAlarmTrigger].
     */
    val currentAlarmTrigger : State<AlarmTrigger> = _currentAlarmTrigger

    /**
     * Function that sets the value of the [_currentAlarmTrigger] to the given parameter.
     */
    fun setCurrentAlarmTrigger(value: AlarmTrigger){
        _currentAlarmTrigger.value = value
    }
    //endregion

    //region Alarm Name
    /**
     * The alarm name for the newly made alarm.
     */
    private val _alarmName : MutableState<String> = mutableStateOf("")

    /**
     * A public getter, returning the value of [_alarmName]
     */
    val alarmName : State<String> = _alarmName;

    /**
     * Function that sets the value of the [_alarmName] to the given parameter.
     */
    fun SetAlarmName(value: String){
        _alarmName.value = _alarmName.value
    }
    //endregion

}