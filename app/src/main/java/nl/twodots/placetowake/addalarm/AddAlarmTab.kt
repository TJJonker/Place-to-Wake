package nl.TwoDots.placetowake.addalarm

import android.app.Activity
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import nl.TwoDots.placetowake.models.viewmodel.AddAlarmViewModel
import nl.TwoDots.placetowake.utils.RingtonePicker
import nl.twodots.placetowake.R
import nl.twodots.placetowake.utils.clearFocusOnKeyboardDismiss

@Composable
fun AddAlarmTab(newAlarmScreenOpened: Boolean) {
    val viewModel by remember { mutableStateOf(AddAlarmViewModel()) }

    AnimatedVisibility(
        visible = newAlarmScreenOpened,
        enter = slideInVertically(initialOffsetY = { it / 2 })
    ) {
        Container(viewModel = viewModel)
    }
}

/**
 * Draws the background of the alarm tab and places all the content inside it's borders.
 */
@Composable
private fun Container(viewModel: AddAlarmViewModel) {
    val modifier = Modifier

    Row(
        modifier = modifier.fillMaxSize(),
        verticalAlignment = Alignment.Bottom
    ) {
        Surface(
            elevation = 5.dp,
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(.6f),
            color = Color.White,
            shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
        ) {
            Content(modifier = Modifier, viewModel = viewModel)
        }
    }
}

@Composable
private fun Content(modifier: Modifier, viewModel: AddAlarmViewModel) {
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
                text = stringResource(id = R.string.new_alarm_title),
                color = Color.DarkGray
            )
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.close_icon_description),
                    tint = Color.DarkGray
                )
            }
        }

        val context = LocalContext.current



        FieldWithHeader(
            modifier = Modifier
                .padding(vertical = 8.dp)
                .fillMaxWidth()
                .clickable { launcher.launch(intent) },
            headerTitle = "Ringtone"
        ) {
            Text(
                text = result,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
        }
    }
}


@Composable
private fun FieldWithHeader(
    modifier: Modifier,
    headerTitle: String,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = headerTitle,
            fontSize = 12.sp,
            color = Color.Black
        )
        content()
    }
}

@Composable
private fun AlarmNameField(modifier: Modifier){


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
}

@Composable
private fun AlarmTriggerField() {

    var currentTriggerOption by remember { mutableStateOf(0) }
    val triggerOptions = listOf("On Enter", "On Exit")
    val triggerIcons = listOf(R.drawable.ic_enter, R.drawable.ic_exit)

    FieldWithHeader(modifier = Modifier.padding(vertical = 8.dp), headerTitle = "Alarm trigger") {
        Column(modifier = Modifier.selectableGroup()) {
            triggerOptions.forEachIndexed { index, item ->
                val selected = index == currentTriggerOption
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
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
    }
}