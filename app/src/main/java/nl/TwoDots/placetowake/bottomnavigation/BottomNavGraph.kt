package nl.twodots.placetowake.bottomnavigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun BottomNavGraph(navController: NavHostController, paddingValues: PaddingValues) {
    NavHost(
        navController = navController,
        startDestination = BottomNavBarOptions.Home.route
    ) {
        composable(route = BottomNavBarOptions.Home.route) { HomeScreen() }
        composable(route = BottomNavBarOptions.Alarms.route) { AlarmScreen() }
        composable(route = BottomNavBarOptions.Settings.route) { SettingsScreen() }
    }
}

private fun HomeScreen() { }

private fun AlarmScreen(){ }

private fun SettingsScreen(){ }