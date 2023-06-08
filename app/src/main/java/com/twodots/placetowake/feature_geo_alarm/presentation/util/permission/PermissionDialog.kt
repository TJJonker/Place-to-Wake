package com.twodots.placetowake.feature_geo_alarm.presentation.util.permission

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PermissionDialog(
    permissionInformation: PermissionInformation,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    val confirmButtonText = if (isPermanentlyDeclined) "Go to settings" else "Grant permission"
    val dismissButtonText = "Dismiss"
    val onConfirmAction = if (isPermanentlyDeclined) onGoToAppSettingsClick else onOkClick

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Text(text = confirmButtonText, modifier = Modifier.clickable { onConfirmAction() }.padding(start = 18.dp))
        },
        dismissButton = {
            Text(text = dismissButtonText, modifier = Modifier.clickable { onDismiss() })
        },
        title = {
            Text(text = "Permission Required")
        },
        text = {
            Text(text = permissionInformation.getDescription(isPermanentlyDeclined, context))
        },
        modifier = modifier
    )
}
