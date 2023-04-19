package nl.twodots.placetowake

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.FloatingActionButtonDefaults.elevation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import nl.twodots.placetowake.addalarm.AddAlarmTab
import nl.twodots.placetowake.bottomnavigation.BottomBar
import nl.twodots.placetowake.bottomnavigation.BottomNavGraph
import nl.twodots.placetowake.models.MainViewModel
import nl.twodots.placetowake.searchwidget.AppSearchBar
import nl.twodots.placetowake.ui.theme.PlaceToWakeTheme

class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaceToWakeTheme {
                MainScreen(mainViewModel = mainViewModel)
            }
        }
    }
}

@Composable
fun Map() {
    val singapore = LatLng(52.488515, 4.936962)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(singapore, 10f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(zoomControlsEnabled = false)
    ) {
        Marker(
            state = MarkerState(position = singapore),
            title = "Singapore",
            snippet = "Marker in Singapore"
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun MainScreen(mainViewModel: MainViewModel) {

    val navController = rememberNavController()

    val searchWidgetState by mainViewModel.searchWidgetState
    val searchTextState by mainViewModel.searchTextState

    var newAlarmScreenOpened by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = !newAlarmScreenOpened,
                enter = slideInVertically(initialOffsetY = { it / 2 }),
                exit = slideOutVertically(targetOffsetY = { it })
            ) {
                BottomBar(navController = navController)
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        floatingActionButton = {
            AnimatedVisibility(
                visible = !newAlarmScreenOpened,
                exit = scaleOut(),
            ) {
                FloatingActionButton(
                    onClick = { newAlarmScreenOpened = true },
                    elevation = elevation(
                        pressedElevation = 0.dp,
                    )
                )
                {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add icon",
                        tint = Color.White
                    )
                }
            }
        }
    ) {
        BottomNavGraph(navController = navController, it)

        /** Main screen. */
        Map()
        AppSearchBar(mainViewModel)

        /** Add new alarm screen. */
        AddAlarmTab(newAlarmScreenOpened = newAlarmScreenOpened)
    }
}