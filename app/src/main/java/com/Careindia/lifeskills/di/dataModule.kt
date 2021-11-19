package com.careindia.lifeskills.di

import com.careindia.lifeskills.views.improfile.IMProfileRequest
import com.careindia.lifeskills.views.loginscreen.LoginRequest
import org.koin.dsl.module.module
import org.koin.experimental.builder.factory

/**
 * module : It creates a module in a koin which would be used by koin to provide all dependencies
 * factory : It creates a bean defination which will create a new instance each time it is injected.
 *            In a factory have to put all request in angular bracket which have to send on server
 */

val apiRequest= module {
    factory <LoginRequest>()
    factory <IMProfileRequest>()
//    factory<RegistrationRequest>()

}
val dataModules = listOf(
    apiRequest
)