package io.apexteam.vmanager.utils

import android.os.Environment
import io.apexteam.vmanager.BuildConfig

object Constants {
    val TEAM_MEMBERS = listOf(
        TeamMember("Win81VMUser", "Developer", "Win81VMUser"),
        TeamMember("slara6804", "Team", "slara6804"),
    )

    // NOTE: This is no longer used
    val MOD_DIR = Environment.getExternalStorageDirectory().resolve(BuildConfig.MOD_NAME)

    val DUMMY_VERSION = DiscordVersion(1, 0, DiscordVersion.Type.STABLE)
}

object Intents {

    object Actions {
        const val INSTALL = "${BuildConfig.APPLICATION_ID}.intents.actions.INSTALL"
    }

    object Extras {
        const val VERSION = "${BuildConfig.APPLICATION_ID}.intents.extras.VERSION"
    }

}

object Channels {
    const val UPDATE = "${BuildConfig.APPLICATION_ID}.notifications.UPDATE"
}

data class TeamMember(
    val name: String,
    val role: String,
    val username: String = name
)