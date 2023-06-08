package com.twodots.placetowake.feature_geo_alarm.presentation.util.permission

import android.app.Activity
import android.content.Context
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import com.twodots.placetowake.feature_geo_alarm.presentation.util.openAppSettings

class PermissionDialogManager {
    val visiblePermissionDialogQueue = mutableStateListOf<PermissionInformation>()

    fun dismissDialog() {
        visiblePermissionDialogQueue.removeFirst()
    }

    fun onPermissionResult(permission: PermissionInformation, isGranted: Boolean) {
        if (!isGranted && !visiblePermissionDialogQueue.contains(permission))
            visiblePermissionDialogQueue.add(permission)
    }

    @Composable
    fun DefaultPermissionDialog(
        permission: PermissionInformation,
        permissionRequestLauncher: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>,
        context: Context
    ) {
        val activity = context as Activity

        PermissionDialog(
            permissionInformation = permission,
            isPermanentlyDeclined = !shouldShowRequestPermissionRationale(
                activity,
                permission.permission
            ),
            onDismiss = { dismissDialog() },
            onOkClick = {
                dismissDialog()
                permissionRequestLauncher.launch(arrayOf(permission.permission))
            },
            onGoToAppSettingsClick = activity::openAppSettings
        )
    }
}

