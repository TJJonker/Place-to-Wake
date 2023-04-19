package nl.twodots.placetowake.addalarm

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AddAlarmTab(newAlarmScreenOpened: Boolean) {
    AnimatedVisibility(visible = newAlarmScreenOpened, enter = slideInVertically(initialOffsetY = {it/2}) ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.Bottom) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.6f),
                color = Color.White,
                shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
            ) {
            }
        }
    }
}