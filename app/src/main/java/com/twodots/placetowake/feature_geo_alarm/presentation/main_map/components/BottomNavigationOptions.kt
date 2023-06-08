package com.twodots.placetowake.feature_geo_alarm.presentation.main_map.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.twodots.placetowake.feature_geo_alarm.presentation.util.Screens

sealed class BottomNavigationOptions(
    val screen: Screens,
    val title: String,
    val icon: ImageVector
) {
    object Alarms : BottomNavigationOptions(
        screen = Screens.AlarmsScreen,
        title = "Alarms",
        icon = Icons.Default.List
    )

    object Settings : BottomNavigationOptions(
        screen = Screens.SettingsScreen,
        title = "Settings",
        icon = Icons.Default.Settings
    )
}
