package com.twodots.placetowake.feature_geo_alarm.presentation.alarms

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.twodots.placetowake.feature_geo_alarm.domain.model.Alarm
import com.twodots.placetowake.feature_geo_alarm.presentation.alarms.components.AlarmCard
import com.twodots.placetowake.feature_geo_alarm.presentation.util.PaddingValuesUsage
import com.twodots.placetowake.ui.theme.Typography
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlarmsPage(
    navController: NavController,
    viewModel: AlarmsViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Alarms"
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Arrow back"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { }) {
                Icon(imageVector = Icons.Default.Edit, contentDescription = "Edit")
            }
        }
    )
    {

        LazyColumn(
            Modifier.padding(it),
            content = {
                items(viewModel.state.value.alarms.size) { alarmIndex ->
                    val currentAlarm = viewModel.state.value.alarms[alarmIndex]
                    AlarmCard(
                        alarm = currentAlarm,
                        onSwitchInteraction = { isEnabled ->
                            viewModel.onEvent(
                                AlarmsEvent.AddAlarm(
                                    currentAlarm.copy(isActive = isEnabled)
                                )
                            )
                        },
                        onClickAction = {}
                    )
                }
            }
        )
    }
}


