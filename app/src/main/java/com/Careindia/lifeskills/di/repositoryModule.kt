package com.careindia.lifeskills.di


import com.careindia.lifeskills.views.loginscreen.LoginRepository
import org.koin.dsl.module.module
import org.koin.experimental.builder.single

/**
 * single: It creates a singleton that can be used across the app as a singular instance.
 * In a single have to bind all repository
 */

val repository = module {
    single<LoginRepository>()
//    single<RegisterRepository>()


}

val repositoryModules = listOf(
    repository
)