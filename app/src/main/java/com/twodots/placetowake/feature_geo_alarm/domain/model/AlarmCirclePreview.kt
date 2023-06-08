package com.twodots.placetowake.feature_geo_alarm.domain.model

import com.google.android.gms.maps.model.LatLng

data class AlarmCirclePreview(val latLng: LatLng, val radius: Double)
