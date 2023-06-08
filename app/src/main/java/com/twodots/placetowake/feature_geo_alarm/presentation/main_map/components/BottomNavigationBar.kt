package com.twodots.placetowake.feature_geo_alarm.presentation.main_map.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun BottomNavigationBar(
    navController: NavController,
    isFabShowing: Boolean,
    onFabClickAction: () -> Unit
) {
    BottomAppBar(
        floatingActionButton = { NewAlarmFab(isFabShowing, onFabClickAction) },
        actions = {
            val iconOptions =
                listOf(BottomNavigationOptions.Alarms, BottomNavigationOptions.Settings)
            iconOptions.forEach { option ->
                IconButton(onClick = { navController.navigate(option.screen.route) }) {
                    Icon(
                        imageVector = option.icon,
                        contentDescription = option.title
                    )
                }
            }
        }
    )
}

@Composable
private fun NewAlarmFab(showFab: Boolean, onFabClickAction: () -> Unit) {
    val density = LocalDensity.current

    AnimatedVisibility(
        visible = showFab,
        enter = slideInHorizontally { with(density) { 80.dp.roundToPx() } }) {
        FloatingActionButton(
            elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp),
            onClick = { onFabClickAction() }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add Icon")
        }
    }
}