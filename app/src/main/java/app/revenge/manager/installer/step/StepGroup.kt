package io.apexteam.vmanager.installer.step

import androidx.annotation.StringRes
import io.apexteam.vmanager.R

/**
 * Represents a group of [Step]s
 */
enum class StepGroup(@StringRes val nameRes: Int) {
    /**
     * All steps deal with downloading files remotely
     */
    DL(R.string.group_download),

    /**
     * Steps that modify the APKs
     */
    PATCHING(R.string.group_patch),

    /**
     * Only contains the [install step][io.apexteam.vmanager.installer.step.installing.InstallStep]
     */
    INSTALLING(R.string.group_installing)
}