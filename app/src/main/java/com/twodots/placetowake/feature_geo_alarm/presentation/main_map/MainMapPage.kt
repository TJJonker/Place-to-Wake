package com.twodots.placetowake.feature_geo_alarm.presentation.main_map

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetValue
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.Circle
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.twodots.placetowake.feature_geo_alarm.presentation.main_map.components.BottomNavigationBar
import com.twodots.placetowake.feature_geo_alarm.presentation.create_alarm.CreateAlarmMenu
import com.twodots.placetowake.feature_geo_alarm.presentation.util.permission.GetProjectPermissions
import kotlinx.coroutines.launch
import java.lang.Math.acos
import java.lang.Math.cos
import java.lang.Math.sin

@SuppressLint("MissingPermission")
@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun MainMapPage(
    navController: NavHostController,
    viewModel: MainMapViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val sheetState = rememberStandardBottomSheetState(
        confirmValueChange = { it != SheetValue.PartiallyExpanded },
        initialValue = SheetValue.Hidden,
        skipHiddenState = false
    )
    val coroutineScope = rememberCoroutineScope()


    val permissionRequestLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = { perms ->
                GetProjectPermissions.forEach { permission ->
                    viewModel.permissionManager.onPermissionResult(
                        permission = permission,
                        isGranted = perms[permission.permission] == true
                    )
                }

                if (perms[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
                    viewModel.getDeviceLocation(
                        LocationServices.getFusedLocationProviderClient(context)
                    )
                    viewModel.startLocationUpdates()
                }
            }
        )

    LaunchedEffect(true) {
        viewModel.onEvent(MainMapEvent.RequestLocationPermission(permissionRequestLauncher = permissionRequestLauncher))
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = !viewModel.state.value.createAlarmMenuOpened,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                BottomNavigationBar(
                    navController = navController,
                    isFabShowing = viewModel.state.value.selectedLocation != null,
                    onFabClickAction = {
                        viewModel.onEvent(MainMapEvent.ToggleNewAlarmMenu(true))
                        coroutineScope.launch {
                            sheetState.expand()
                        }
                    }
                )
            }
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = !viewModel.state.value.createAlarmMenuOpened && viewModel.state.value.lastUserLocation != null,
                exit = scaleOut(),
                enter = scaleIn()
            ) {
                SmallFloatingActionButton(onClick = {
                    viewModel.onEvent(MainMapEvent.ZoomToUserLocation)
                }) {
                    Icon(imageVector = Icons.Default.LocationOn, contentDescription = "Location on")
                }
            }
        }
    ) {
        PermissionDialog(
            viewModel = viewModel,
            permissionRequestLauncher = permissionRequestLauncher
        )
        DrawMap(viewModel = viewModel, paddingValues = it)

        CreateAlarmMenu(
            sheetState = sheetState,
            onMenuCloseAction = { viewModel.onEvent(MainMapEvent.ToggleNewAlarmMenu(false)) },
            latLng = viewModel.state.value.selectedLocation ?: LatLng(0.0, 0.0),
            onPreviewChange = { latLng, radius ->
                viewModel.onEvent(
                    MainMapEvent.SetAlarmCirclePreview(
                        latLng,
                        radius
                    )
                )
            }
        )
    }

    viewModel.locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            for (lo in p0.locations) {
                viewModel.onEvent(
                    MainMapEvent.SetLastUserLocation(
                        LatLng(
                            lo.latitude,
                            lo.longitude
                        )
                    )
                )
            }
        }
    }

    AlarmLocationOverlapCheck(
        userLocation = viewModel.state.value.lastLoopedUserLocation,
        viewModel = viewModel
    )
}

@Composable
private fun AlarmLocationOverlapCheck(userLocation: LatLng, viewModel: MainMapViewModel) {
    viewModel.state.value.lisOfAlarms.forEach {
        if (viewModel.state.value.isAlarmRinging)
            return

        if (!it.isActive)
            return@forEach

        val lat1 = userLocation.latitude
        val lon1 = userLocation.longitude
        val lat2 = it.latitude
        val lon2 = it.longitude

        val dLat = (lat2 - lat1) * (Math.PI/180)
        val dLon = (lon2 - lon1) * (Math.PI/180)

        val r = 6371

        val result = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(lat1 * (Math.PI/180)) * Math.cos(lat2 * (Math.PI/180)) *
                Math.sin(dLon/2) * Math.sin(dLon/2)

        val c = 2 * Math.atan2(Math.sqrt(result), Math.sqrt(1-result))
        val distance = r * c * 1000

        if (distance > it.range)
            return@forEach

        viewModel.onEvent(MainMapEvent.SetAlarmRinging(true))
        val onDismiss = {
            viewModel.onEvent(MainMapEvent.SetAlarmRinging(false))
            viewModel.onEvent(MainMapEvent.DisableAlarm(it))
        }
        AlertDialog(
            onDismissRequest = { onDismiss() },
            confirmButton = { },
            dismissButton = {
                Text(text = "Dismiss", modifier = Modifier.clickable { onDismiss() })
            },
            title = {
                Text(text = "Alarm ringing")
            },
            text = {
                Text(text = "Alarm: ${it.title} is ringing.")
            },
        )
    }
}

@Composable
private fun PermissionDialog(
    viewModel: MainMapViewModel,
    permissionRequestLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>
) {
    viewModel.permissionManager.visiblePermissionDialogQueue.forEach { permissionInformation ->
        viewModel.permissionManager.DefaultPermissionDialog(
            permission = permissionInformation,
            permissionRequestLauncher = permissionRequestLauncher,
            LocalContext.current
        )
    }
}

@Composable
fun DrawMap(viewModel: MainMapViewModel, paddingValues: PaddingValues) {
    val mapProperties = MapProperties(
        isMyLocationEnabled = viewModel.state.value.lastUserLocation != null
    )
    val lockControls = !viewModel.state.value.createAlarmMenuOpened



    GoogleMap(
        onMapLongClick = { latlong ->
            viewModel.onEvent(
                MainMapEvent.SetMapPointer(
                    latlong.latitude,
                    latlong.longitude
                )
            )
        },
        properties = mapProperties,
        contentPadding = paddingValues,
        cameraPositionState = viewModel.state.value.cameraPositionState,
        modifier = Modifier.fillMaxSize(),
        uiSettings = MapUiSettings(
            zoomControlsEnabled = false,
            compassEnabled = false,
            myLocationButtonEnabled = false,
            rotationGesturesEnabled = lockControls,
            scrollGesturesEnabled = lockControls,
            scrollGesturesEnabledDuringRotateOrZoom = lockControls,
            tiltGesturesEnabled = lockControls,
            zoomGesturesEnabled = lockControls,


            )
    ) {
        val selectedLocation = viewModel.state.value.selectedLocation
        if (selectedLocation != null)
            Marker(state = MarkerState(selectedLocation))

        val alarmCirclePreview = viewModel.state.value.alarmCirclePreview
        if (alarmCirclePreview != null && viewModel.state.value.createAlarmMenuOpened)
            Circle(
                center = alarmCirclePreview.latLng,
                radius = alarmCirclePreview.radius,
                fillColor = Color(0x554fc3f7),
                strokeColor = Color(0xAA1cb2f5)
            )

        viewModel.state.value.lisOfAlarms.forEach { alarm ->
            if (!alarm.isActive)
                return@forEach

            Circle(
                center = LatLng(alarm.latitude, alarm.longitude),
                radius = alarm.range.toDouble(),
                fillColor = Color(0x554fc3f7),
                strokeColor = Color(0xAA1cb2f5)
            )
        }
    }
}