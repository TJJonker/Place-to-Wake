package com.twodots.placetowake.feature_geo_alarm.presentation.util

sealed class Screens(
    val route: String
){
    object HomeScreen: Screens("HomeScreen")
    object AlarmsScreen: Screens("AlarmsScreen")
    object SettingsScreen: Screens("SettingsScreen")
}