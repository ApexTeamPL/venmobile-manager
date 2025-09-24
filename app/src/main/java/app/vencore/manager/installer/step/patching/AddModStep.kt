package io.apexteam.vmanager.installer.step.patching

import io.apexteam.vmanager.BuildConfig
import io.apexteam.vmanager.R
import io.apexteam.vmanager.installer.step.Step
import io.apexteam.vmanager.installer.step.StepGroup
import io.apexteam.vmanager.installer.step.StepRunner
import io.apexteam.vmanager.installer.step.download.DownloadModStep
import java.io.File

/**
 * Uses LSPatch to inject the Vencore XPosed module into Discord
 *
 * @param signedDir The signed apks to patch
 * @param lspatchedDir Output directory for LSPatch
 */
class AddModStep(
    private val signedDir: File,
    private val lspatchedDir: File
) : Step() {

    override val group = StepGroup.PATCHING
    override val nameRes = R.string.step_add_mod

    override suspend fun run(runner: StepRunner) {
        val mod = runner.getCompletedStep<DownloadModStep>().workingCopy

        runner.logger.i("Adding ${BuildConfig.MOD_NAME}Xposed module with LSPatch")
        val files = signedDir.listFiles()
            ?.takeIf { it.isNotEmpty() }
            ?: throw Error("Missing APKs from signing step")

        io.apexteam.vmanager.installer.util.Patcher.patch(
            runner.logger,
            outputDir = lspatchedDir,
            apkPaths = files.map { it.absolutePath },
            embeddedModules = listOf(mod.absolutePath)
        )
    }

}