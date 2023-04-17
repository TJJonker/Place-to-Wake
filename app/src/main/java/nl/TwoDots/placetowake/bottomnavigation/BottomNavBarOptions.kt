package nl.twodots.placetowake.bottomnavigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavBarOptions(
    val route: String,
    val title: String,
    val icon: ImageVector?
){
    object Home : BottomNavBarOptions(
        route = "Home",
        title = "Home",
        icon = null
    )

    object Alarms : BottomNavBarOptions(
        route = "Alarms",
        title = "Alarms",
        icon = Icons.Default.List
    )

    object Settings : BottomNavBarOptions(
        route = "Settings",
        title = "Settings",
        icon = Icons.Default.Settings
    )
}
