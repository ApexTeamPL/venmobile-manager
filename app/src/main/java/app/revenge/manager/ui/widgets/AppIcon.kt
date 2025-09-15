package io.apexteam.vmanager.ui.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import io.apexteam.vmanager.BuildConfig
import io.apexteam.vmanager.R
import io.apexteam.vmanager.utils.DiscordVersion

@Composable
fun AppIcon(
    customIcon: Boolean,
    releaseChannel: DiscordVersion.Type,
    modifier: Modifier = Modifier
) {
    val iconColor = remember(customIcon, releaseChannel) {
        when {
            customIcon -> Color(BuildConfig.MODDED_APP_ICON)
            releaseChannel == DiscordVersion.Type.ALPHA -> Color(BuildConfig.MODDED_APP_ICON_ALPHA)
            else -> Color(BuildConfig.MODDED_APP_ICON_OTHER)
        }
    }

    Image(
        painter = painterResource(id = R.drawable.ic_discord_icon),
        contentDescription = null,
        modifier = modifier
            .clip(CircleShape)
            .background(iconColor)
    )
}