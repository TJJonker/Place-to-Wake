package com.twodots.placetowake.feature_geo_alarm.presentation.main_map.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.twodots.placetowake.ui.theme.Typography

@Composable
fun HeaderField(modifier: Modifier = Modifier, headerText: String, content: @Composable () -> Unit) {
    Column(modifier = modifier) {
        Text(text = headerText, style = Typography.labelSmall)
        content()
    }
}