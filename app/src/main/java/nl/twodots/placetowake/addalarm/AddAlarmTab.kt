package nl.twodots.placetowake.addalarm

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import nl.twodots.placetowake.utils.CircularSlider
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
                .fillMaxHeight(.7f),
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

        TextField(
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

        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Alarm trigger",
                        color = Color.Gray
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectableGroup()
                    ) {
                        Text(text = "On Enter", color = Color.DarkGray)
                        Text(text = "On Exit", color = Color.DarkGray)
                    }
                }
            }
        }
    }
}

