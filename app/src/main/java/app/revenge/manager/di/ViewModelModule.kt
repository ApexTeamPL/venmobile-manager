package io.apexteam.vmanager.di

import io.apexteam.vmanager.ui.viewmodel.home.HomeViewModel
import io.apexteam.vmanager.ui.viewmodel.installer.InstallerViewModel
import io.apexteam.vmanager.ui.viewmodel.installer.LogViewerViewModel
import io.apexteam.vmanager.ui.viewmodel.libraries.LibrariesViewModel
import io.apexteam.vmanager.ui.viewmodel.settings.AdvancedSettingsViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val viewModelModule = module {
    factoryOf(::InstallerViewModel)
    factoryOf(::AdvancedSettingsViewModel)
    factoryOf(::HomeViewModel)
    factoryOf(::LogViewerViewModel)
    factoryOf(::LibrariesViewModel)
}