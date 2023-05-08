package nl.TwoDots.placetowake.addalarm

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
import nl.TwoDots.placetowake.models.viewmodel.AddAlarmViewModel
import nl.twodots.placetowake.R
import nl.twodots.placetowake.utils.clearFocusOnKeyboardDismiss

@Composable
fun AddAlarmTab(newAlarmScreenOpened: Boolean) {
    val currentContext = LocalContext.current
    val viewModel by remember { mutableStateOf(AddAlarmViewModel(currentContext)) }

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
        Header()
        AlarmNameField(modifier = modifier, viewModel = viewModel)
        AlarmTriggerField(viewModel = viewModel)
        RingtoneField(viewModel = viewModel)
    }
}

@Composable
private fun FieldWithHeader(
    modifier: Modifier,
    headerTitle: String,
    content: @Composable () -> Unit
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
private fun Header() {
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
}

@Composable
private fun AlarmNameField(modifier: Modifier, viewModel: AddAlarmViewModel) {

    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        colors = TextFieldDefaults.textFieldColors(
            unfocusedIndicatorColor = Color.Gray,
            textColor = Color.DarkGray
        ),
        singleLine = true,
        value = viewModel.alarmName.value,
        onValueChange = { text -> viewModel.SetAlarmName(text) },
        placeholder = {
            Text(
                text = stringResource(R.string.Alarm_name_field_placeholder),
                color = Color.LightGray
            )
        },
        label = {
            Text(
                text = stringResource(R.string.alarm_name_field_label),
                color = Color.Gray
            )
        }
    )
}

@Composable
private fun AlarmTriggerField(viewModel: AddAlarmViewModel) {
    FieldWithHeader(modifier = Modifier.padding(vertical = 8.dp), headerTitle = "Alarm trigger") {
        Column(modifier = Modifier.selectableGroup()) {
            viewModel.alarmTriggers.forEach { item ->
                val selected = item == viewModel.currentAlarmTrigger.value
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = selected,
                            onClick = { viewModel.setCurrentAlarmTrigger(item) },
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
                                painter = painterResource(id = item.icon),
                                contentDescription = item.iconDescription,
                                tint = if (selected)
                                    Color.White
                                else
                                    Color.Black,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text(
                                fontWeight = FontWeight.SemiBold,
                                text = item.title,
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

@Composable
private fun RingtoneField(viewModel: AddAlarmViewModel) {
    var launchRingtonePicker by remember { mutableStateOf(false) }
    val currentContext = LocalContext.current

    FieldWithHeader(
        modifier = Modifier
            .padding(vertical = 8.dp)
            .fillMaxWidth()
            .clickable { launchRingtonePicker = true },
        headerTitle = "Ringtone"
    ) {
        Text(
            text = viewModel.ringtonePicker.currentRingtone.getTitle(currentContext),
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
    }

    if(launchRingtonePicker)
        viewModel.ringtonePicker.launch()
}