package nl.twodots.placetowake

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavBar(
    val route: String,
    val title: String,
    val icon: ImageVector?
){
    object Home : BottomNavBar(
        route = "Home",
        title = "Home",
        icon = null
    )

    object Alarms : BottomNavBar(
        route = "Alarms",
        title = "Alarms",
        icon = Icons.Default.List
    )

    object Settings : BottomNavBar(
        route = "Settings",
        title = "Settings",
        icon = Icons.Default.Settings
    )
}
