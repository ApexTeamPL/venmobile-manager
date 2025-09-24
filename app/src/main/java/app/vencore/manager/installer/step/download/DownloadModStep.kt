package io.apexteam.vmanager.installer.step.download

import androidx.compose.runtime.Stable
import io.apexteam.vmanager.R
import io.apexteam.vmanager.installer.step.download.base.DownloadStep
import java.io.File

/**
 * Downloads the Vencore XPosed module
 *
 * https://github.com/ApexTeamPL/venmobile-xposed
 */
@Stable
class DownloadModStep(
    workingDir: File
): DownloadStep() {

    override val nameRes = R.string.step_dl_mod

    override val downloadFullUrl: String = "https://github.com/ApexTeamPL/venmobile-xposed/releases/latest/download/app-release.apk"
    override val destination = preferenceManager.moduleLocation
    override val workingCopy = workingDir.resolve("xposed.apk")

}
