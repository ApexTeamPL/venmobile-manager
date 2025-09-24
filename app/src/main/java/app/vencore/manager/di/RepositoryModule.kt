package io.apexteam.vmanager.di

import io.apexteam.vmanager.domain.repository.RestRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val repositoryModule = module {
    singleOf(::RestRepository)
}