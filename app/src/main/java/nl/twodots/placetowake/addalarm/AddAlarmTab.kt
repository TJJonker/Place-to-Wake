package nl.TwoDots.placetowake.addalarm

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.core.view.ContentInfoCompat.Flags
import nl.twodots.placetowake.R
import nl.twodots.placetowake.utils.clearFocusOnKeyboardDismiss

@Composable
fun AddAlarmTab(newAlarmScreenOpened: Boolean) {
    AnimatedVisibility(
        visible = newAlarmScreenOpened,
        enter = slideInVertically(initialOffsetY = { it / 2 })
    ) {
        AlarmTab()
    }
}


@Preview
@Composable
fun Preview() {
    AlarmTab()
}

@Composable
private fun AlarmTab() {
    val modifier = Modifier

    Row(modifier = modifier.fillMaxSize(), verticalAlignment = Alignment.Bottom) {
        Surface(
            elevation = 5.dp,
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(.6f),
            color = Color.White,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            AlarmTabContent(modifier = Modifier)
        }
    }
}

@Composable
private fun AlarmTabContent(modifier: Modifier) {
    var alarmName by remember { mutableStateOf("") }
    Column(
        modifier = modifier
            .padding(start = 24.dp, end = 24.dp, top = 12.dp)
            .clearFocusOnKeyboardDismiss()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(
                text = "New alarm",
                color = Color.DarkGray
            )
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close icon",
                    tint = Color.DarkGray
                )
            }
        }

        OutlinedTextField(
            modifier = modifier
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                unfocusedIndicatorColor = Color.Gray,
                textColor = Color.DarkGray
            ),
            singleLine = true,
            value = alarmName,
            onValueChange = { text -> alarmName = text },
            placeholder = {
                Text(
                    text = "Set a name for the alarm",
                    color = Color.LightGray
                )
            },
            label = {
                Text(
                    text = "Alarm name",
                    color = Color.Gray
                )
            }
        )

        Column(modifier = Modifier.padding(vertical = 12.dp)) {
            Text(
                text = "Alarm trigger",
                modifier = Modifier.padding(vertical = 8.dp),
                fontSize = 12.sp,
                color = Color.Black
            )

            var currentTriggerOption by remember { mutableStateOf(0) }
            val triggerOptions = listOf("On Enter", "On Exit")
            val triggerIcons = listOf(R.drawable.ic_enter, R.drawable.ic_exit)
            Column(modifier = Modifier.selectableGroup()) {
                triggerOptions.forEachIndexed { index, item ->
                    var selected = index == currentTriggerOption
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.selectable(
                            selected = selected,
                            onClick = { currentTriggerOption = index },
                            role = Role.RadioButton
                        )
                    ) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (selected)
                                colorResource(id = R.color.primary)
                            else
                                Color.Transparent,
                        ) {
                            Row(
                                modifier = Modifier.padding(8.dp, 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Icon(
                                    painter = painterResource(id = triggerIcons[index]),
                                    contentDescription = "",
                                    tint = if (selected)
                                        Color.White
                                    else
                                        Color.Black,
                                    modifier = Modifier.padding(end = 8.dp)
                                )
                                Text(
                                    fontWeight = FontWeight.SemiBold,
                                    text = item,
                                    color = if (selected)
                                        Color.White
                                    else
                                        Color.Black,
                                )
                            }
                        }
                    }
                }
            }

            val context = LocalContext.current
            val currentRingtone: Uri = RingtoneManager.getActualDefaultRingtoneUri(
                context, RingtoneManager.TYPE_ALARM
            )
            val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER)
            intent.putExtra(
                RingtoneManager.EXTRA_RINGTONE_TYPE,
                RingtoneManager.TYPE_RINGTONE,
            )
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Selecteer dingetje Tone")
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, currentRingtone)
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, false)
            intent.putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true)
            var result by remember {
                mutableStateOf("No result")
            }
            val launcher =
                rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
                    if (it.resultCode == Activity.RESULT_OK)
                        result = RingtoneManager.getRingtone(context, it.data!!.extras!!.get(RingtoneManager.EXTRA_RINGTONE_PICKED_URI).toString().toUri()).getTitle(context)
                }

            Button(
                onClick = {
                    launcher.launch(intent)
                },
            ) {
                Text(text = "Click me for a ringtone menu")
            }

            Text(text = result, color = Color.Black)
        }
    }
}

@Composable
private fun DoThingy(intent: Intent, context: Context) {

}

