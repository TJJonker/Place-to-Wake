package nl.twodots.placetowake

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun BottomNavGraph(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = BottomNavBar.Home.route
    ) {
        composable(route = BottomNavBar.Home.route) { HomeScreen() }
        composable(route = BottomNavBar.Alarms.route) { AlarmScreen() }
        composable(route = BottomNavBar.Settings.route) { SettingsScreen() }
    }
}

private fun HomeScreen() { }

private fun AlarmScreen(){ }

private fun SettingsScreen(){ }