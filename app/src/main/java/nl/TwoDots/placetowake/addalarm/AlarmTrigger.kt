package nl.TwoDots.placetowake.addalarm

import nl.twodots.placetowake.R
import java.io.FileDescriptor

sealed class AlarmTrigger(
    val title: String,
    val icon: Int,
    val iconDescription: String,
    val shouldTrigger: (Float, Float) -> Boolean
) {
    object OnEnter : AlarmTrigger(
        title = "On Enter",
        icon = R.drawable.ic_enter,
        iconDescription = "Icon Enter",
        shouldTrigger = { distanceTillTarget, requiredDistance -> distanceTillTarget <= requiredDistance }
    )

    object OnExit : AlarmTrigger(
        title = "On Exit",
        icon = R.drawable.ic_exit,
        iconDescription = "Icon Exit",
        shouldTrigger = { distanceTillTarget, requiredDistance -> distanceTillTarget > requiredDistance }
    )
}