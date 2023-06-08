package com.twodots.placetowake.feature_geo_alarm.presentation.util.permission

import android.Manifest
import android.content.Context
import com.twodots.placetowake.R


interface PermissionInformation {
    fun getDescription(isPermanentlyDeclined: Boolean, context: Context) : String
    val permission: String
}

class FineLocationPermissionInformation: PermissionInformation {

    override fun getDescription(isPermanentlyDeclined: Boolean, context: Context) : String{
        return if(isPermanentlyDeclined)
            context.getString(R.string.PermanentlyDeclinedText_Location)
        else
            context.getString(R.string.PermissionRequest_Location)
    }

    override val permission: String
        get() = Manifest.permission.ACCESS_FINE_LOCATION
}

