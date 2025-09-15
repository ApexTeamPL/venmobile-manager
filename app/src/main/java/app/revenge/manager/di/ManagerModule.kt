package io.apexteam.vmanager.di

import io.apexteam.vmanager.domain.manager.DownloadManager
import io.apexteam.vmanager.domain.manager.InstallManager
import io.apexteam.vmanager.domain.manager.PreferenceManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val managerModule = module {
    singleOf(::DownloadManager)
    singleOf(::PreferenceManager)
    singleOf(::InstallManager)
}