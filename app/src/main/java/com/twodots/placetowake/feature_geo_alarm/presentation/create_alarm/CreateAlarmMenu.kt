package com.twodots.placetowake.feature_geo_alarm.presentation.create_alarm

import android.app.Activity
import android.content.Intent
import android.media.RingtoneManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.model.LatLng
import com.twodots.placetowake.feature_geo_alarm.presentation.main_map.components.HeaderField
import kotlin.math.max

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateAlarmMenu(
    sheetState: SheetState,
    onMenuCloseAction: () -> Unit,
    latLng: LatLng,
    onPreviewChange: (LatLng, Double) -> Unit
) {
    val viewModel: CreateAlarmViewModel = hiltViewModel()
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val context = LocalContext.current

    val ringtonePickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val ringtoneUri =
                    it.data!!.extras!!.get(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
                        .toString().toUri()
                val ringtoneName =
                    RingtoneManager.getRingtone(context, ringtoneUri).getTitle(context)
                viewModel.onEvent(
                    CreateAlarmEvents.UpdateRingtone(
                        ringtoneUri = ringtoneUri,
                        ringtoneName = ringtoneName
                    )
                )
            }
        }

    BottomSheetScaffold(
        sheetContent = {
            Column(modifier = Modifier.padding(horizontal = 12.dp)) {
                Content(
                    viewModel,
                    ringtonePickerLauncher = ringtonePickerLauncher,
                    sheetState = sheetState,
                    latLng = latLng
                )
            }
        },
        scaffoldState = scaffoldState
    ) {
    }
    if (sheetState.currentValue == SheetValue.Hidden && sheetState.targetValue == SheetValue.Hidden)
        onMenuCloseAction()

    onPreviewChange(latLng, viewModel.state.value.radius.toDouble())
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    viewModel: CreateAlarmViewModel,
    ringtonePickerLauncher: ManagedActivityResultLauncher<Intent, ActivityResult>,
    sheetState: SheetState,
    latLng: LatLng
) {
    HeaderField(headerText = "Alarm name") {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = viewModel.state.value.alarmName,
            onValueChange = { viewModel.onEvent(CreateAlarmEvents.UpdateName(it)) },
            placeholder = { Text(text = "e.g. Home") }
        )
    }
    Spacer(Modifier.height(12.dp))
    HeaderField(headerText = "Radius") {
        Row(modifier = Modifier.fillMaxWidth()) {
            Slider(
                valueRange = 50f..1500f,
                value = viewModel.state.value.radius.toFloat(),
                onValueChange = { viewModel.onEvent(CreateAlarmEvents.UpdateRadius(it.toInt())) },
                modifier = Modifier.fillMaxWidth(.6f)
            )
            Spacer(Modifier.width(12.dp))
            OutlinedTextField(
                readOnly = true,
                value = viewModel.state.value.radius.toString(),
                onValueChange = {
                    var finalValue = 0
                    if (it.isNotEmpty())
                        finalValue = it.toInt()
                    viewModel.onEvent(CreateAlarmEvents.UpdateRadius(max(finalValue, 50)))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
    Spacer(Modifier.height(12.dp))

    HeaderField(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                ringtonePickerLauncher.launch(
                    viewModel.ringtonePicker.getRingtonePickerIntent(
                        viewModel.state.value.ringtoneUri
                    )
                )
            },
        headerText = "Ringtone"
    ) {
        Text(text = viewModel.state.value.ringtoneName)
    }

    Spacer(Modifier.height(24.dp))

    val coroutineScope = rememberCoroutineScope()

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Button(
            content = { Text(text = "Save") },
            onClick = {
                viewModel.onEvent(
                    CreateAlarmEvents.SaveAlarm(
                        sheetState,
                        latLng,
                        coroutineScope
                    )
                )
            },
            modifier = Modifier.width(140.dp)
        )

        Button(
            content = { Text(text = "Start") },
            onClick = {
                viewModel.onEvent(
                    CreateAlarmEvents.StartAlarm(
                        sheetState,
                        latLng,
                        coroutineScope
                    )
                )
            },
            modifier = Modifier.width(140.dp)
        )
    }

    Spacer(Modifier.height(12.dp))
}