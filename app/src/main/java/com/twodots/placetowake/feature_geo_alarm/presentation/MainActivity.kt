package com.twodots.placetowake.feature_geo_alarm.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.twodots.placetowake.feature_geo_alarm.presentation.alarms.AlarmsPage
import com.twodots.placetowake.feature_geo_alarm.presentation.main_map.MainMapPage
import com.twodots.placetowake.feature_geo_alarm.presentation.util.Screens
import com.twodots.placetowake.ui.theme.PlaceToWakeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaceToWakeTheme {
                val navController = rememberNavController()
                ProjectNavigation(navController = navController)
            }
        }
    }
}

@Composable
fun ProjectNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.HomeScreen.route
    ) {
        composable(route = Screens.HomeScreen.route) { MainMapPage(navController = navController) }
        composable(route = Screens.AlarmsScreen.route) { AlarmsPage(navController = navController) }
        composable(route = Screens.SettingsScreen.route) { MainMapPage(navController = navController) }
    }
}