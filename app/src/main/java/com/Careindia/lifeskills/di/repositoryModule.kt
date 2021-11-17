package com.careindia.lifeskills.di


import org.koin.dsl.module.module

/**
 * single: It creates a singleton that can be used across the app as a singular instance.
 * In a single have to bind all repository
 */

val repository = module {
//    single<RegisterRepository>()
//    single<LoginRepository>()



}

val repositoryModules = listOf(
    repository
)