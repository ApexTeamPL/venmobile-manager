package io.apexteam.vmanager.installer.step.installing

import android.content.Context
import io.apexteam.vmanager.R
import io.apexteam.vmanager.domain.manager.InstallMethod
import io.apexteam.vmanager.domain.manager.PreferenceManager
import io.apexteam.vmanager.installer.Installer
import io.apexteam.vmanager.installer.session.SessionInstaller
import io.apexteam.vmanager.installer.shizuku.ShizukuInstaller
import io.apexteam.vmanager.installer.shizuku.ShizukuPermissions
import io.apexteam.vmanager.installer.step.Step
import io.apexteam.vmanager.installer.step.StepGroup
import io.apexteam.vmanager.installer.step.StepRunner
import io.apexteam.vmanager.utils.isMiui
import io.apexteam.vmanager.utils.showToast
import org.koin.core.component.inject
import java.io.File

/**
 * Installs all the modified splits with the users desired [Installer]
 *
 * @see SessionInstaller
 * @see ShizukuInstaller
 *
 * @param lspatchedDir Where all the patched APKs are
 */
class InstallStep(
    private val lspatchedDir: File
): Step() {

    private val preferences: PreferenceManager by inject()
    private val context: Context by inject()

    override val group = StepGroup.INSTALLING
    override val nameRes = R.string.step_installing

    override suspend fun run(runner: StepRunner) {
        runner.logger.i("Installing apks")
        val files = lspatchedDir.listFiles()
            ?.takeIf { it.isNotEmpty() }
            ?: throw Error("Missing APKs from LSPatch step; failure likely")

        val installMethod = if (preferences.installMethod == InstallMethod.SHIZUKU && !ShizukuPermissions.waitShizukuPermissions()) {
            // Temporarily use DEFAULT if SHIZUKU permissions are not granted
            context.showToast(R.string.msg_shizuku_denied)
            InstallMethod.DEFAULT
        } else {
            preferences.installMethod
        }

        val installer: Installer = when (installMethod) {
            InstallMethod.DEFAULT -> SessionInstaller(context)
            InstallMethod.SHIZUKU -> ShizukuInstaller(context)
        }

        installer.installApks(silent = !isMiui, *files)
    }

}