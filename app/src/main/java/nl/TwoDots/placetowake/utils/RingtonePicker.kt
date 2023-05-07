package nl.TwoDots.placetowake.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.core.net.toUri
import nl.twodots.placetowake.R

/**
 * Class responsible for easy acces to the ringtone picker functionality.
 */
class RingtonePicker(context: Context) {

    //region private

    /**
     * Holds the [Context] variable, given in the constructor.
     */
    private var _currentContext: MutableState<Context> = mutableStateOf(context)

    /**
     * BackingField for the property [intent]. Should not be accessed.
     * Suggested use of the property [intent] instead.
     */
    private var _intent: MutableState<Intent?> = mutableStateOf(null)

    /**
     * Holds the [Intent] used to launch the RingtonePicker function.
     */
    private val intent: Intent
        get() {
            if (_intent.value == null)
                _intent.value = buildIntent(_currentContext.value)
            return _intent.value!!
        }

    /**
     * BackingField for the property [currentRingtoneUri]. Should not be accessed.
     * Suggested use of the property [currentRingtoneUri] instead.
     */
    private var _currentRingtone: MutableState<Uri> = mutableStateOf(RingtoneManager.getActualDefaultRingtoneUri(_currentContext.value, RingtoneManager.TYPE_ALARM))

    /**
     * Creates a new Intent instance, containing the RingtonePicker functionality.
     */
    private fun buildIntent(context: Context): Intent {
        val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER)
        intent.putExtra(
            RingtoneManager.EXTRA_RINGTONE_TYPE,
            RingtoneManager.TYPE_RINGTONE,
        )
        intent.putExtra(
            RingtoneManager.EXTRA_RINGTONE_TITLE,
            context.getString(R.string.select_ringtone)
        )
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, currentRingtoneUri)
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false)
        intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true)
        return intent
    }

    //endregion

    //region public

    /**
     * @Return the currently selected ringtone [Uri].
     */
    var currentRingtoneUri: Uri
        get() = _currentRingtone.value
        private set(value) {
            _currentRingtone.value = value
        }

    /**
     * Returns the currently selected [Uri].
     */
    val currentRingtone: Ringtone
        get() = RingtoneManager.getRingtone(_currentContext.value, currentRingtoneUri)


    /**
     * Launches the RingtonePicker functionality and opens the native ringtone selection screen.
     * @return The selected [Ringtone], otherwise the originally selected ringtone.
     */
    @Composable
    fun launch() {
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK)
                currentRingtoneUri =
                    it.data!!.extras!!.get(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
                        .toString().toUri()
        }.launch(intent)
    }

    //endregion
}

