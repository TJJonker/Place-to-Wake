package nl.TwoDots.placetowake.addalarm

import nl.twodots.placetowake.R

sealed class AlarmTrigger(
    val title: String,
    val icon: Int,
    val shouldTrigger: (Float, Float) -> Boolean
) {
    object OnEnter : AlarmTrigger(
        title = "On Enter",
        icon = R.drawable.ic_enter,
        shouldTrigger = { distanceTillTarget, requiredDistance -> distanceTillTarget <= requiredDistance }
    )

    object OnExit : AlarmTrigger(
        title = "On Exit",
        icon = R.drawable.ic_exit,
        shouldTrigger = { distanceTillTarget, requiredDistance -> distanceTillTarget > requiredDistance }
    )
}