package com.twodots.placetowake.feature_geo_alarm.presentation.alarms.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.twodots.placetowake.feature_geo_alarm.domain.model.Alarm
import com.twodots.placetowake.ui.theme.Typography

@Composable
fun AlarmCard(
    alarm: Alarm,
    onSwitchInteraction: (Boolean) -> Unit,
    onClickAction: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClickAction() }
            .padding(horizontal = 32.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = alarm.title,
            style = Typography.titleLarge,
            modifier = Modifier.width(100.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Column() {
            Text(text = "Radius", fontWeight = FontWeight.Bold)
            Text(text = "${alarm.range}m")
        }
        Switch(checked = alarm.isActive, onCheckedChange = onSwitchInteraction)
    }
}