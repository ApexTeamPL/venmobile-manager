package io.apexteam.vmanager.installer.step.patching

import android.content.Context
import androidx.compose.ui.graphics.Color
import com.github.diamondminer88.zip.ZipWriter
import io.apexteam.vmanager.BuildConfig
import io.apexteam.vmanager.R
import io.apexteam.vmanager.domain.manager.PreferenceManager
import io.apexteam.vmanager.installer.step.Step
import io.apexteam.vmanager.installer.step.StepGroup
import io.apexteam.vmanager.installer.step.StepRunner
import io.apexteam.vmanager.installer.step.download.DownloadBaseStep
import io.apexteam.vmanager.installer.utils.ArscUtil
import io.apexteam.vmanager.installer.utils.ArscUtil.addColorResource
import io.apexteam.vmanager.installer.utils.ArscUtil.getMainArscChunk
import io.apexteam.vmanager.installer.utils.ArscUtil.getPackageChunk
import io.apexteam.vmanager.installer.utils.ArscUtil.getResourceFileName
import io.apexteam.vmanager.installer.utils.AxmlUtil
import io.apexteam.vmanager.utils.DiscordVersion
import org.koin.core.component.inject

/**
 * Replaces the existing app icons with Vencore tinted ones
 */
class ReplaceIconStep : Step() {

    private val preferences: PreferenceManager by inject()

    val context: Context by inject()

    override val group = StepGroup.PATCHING
    override val nameRes = R.string.step_change_icon

    override suspend fun run(runner: StepRunner) {
        val baseApk = runner.getCompletedStep<DownloadBaseStep>().workingCopy

        runner.logger.i("Reading resources.arsc")
        val arsc = ArscUtil.readArsc(baseApk)
        
        val iconRscIds = AxmlUtil.readManifestIconInfo(baseApk)
        val squareIconFile = arsc.getMainArscChunk().getResourceFileName(iconRscIds.squareIcon, "anydpi-v26")
        val roundIconFile = arsc.getMainArscChunk().getResourceFileName(iconRscIds.roundIcon, "anydpi-v26")

        runner.logger.i("Patching icon assets (squareIcon=$squareIconFile, roundIcon=$roundIconFile)")

        val backgroundColor = arsc.getPackageChunk().addColorResource("brand", Color(BuildConfig.MODDED_APP_ICON))

        val postfix = when (preferences.channel) {
            DiscordVersion.Type.BETA -> "beta"
            DiscordVersion.Type.ALPHA -> "canary"
            else -> null
        }
        
        for (rscFile in setOf(squareIconFile, roundIconFile)) { // setOf to not possibly patch same file twice
            val referencePath = if (postfix == null) rscFile else {
                rscFile.replace("_$postfix.xml", ".xml")
            }

            runner.logger.i("Patching adaptive icon ($rscFile <- $referencePath)")

            AxmlUtil.patchAdaptiveIcon(
                apk = baseApk,
                resourcePath = rscFile,
                referencePath = referencePath,
                backgroundColor = backgroundColor,
            )
        }

        runner.logger.i("Writing and compiling resources.arsc")
        ZipWriter(baseApk, /* append = */ true).use {
            it.deleteEntry("resources.arsc")
            it.writeEntry("resources.arsc", arsc.toByteArray())
        }
    }

}
