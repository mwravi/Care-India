package com.careindia.lifeskills.di


import com.careindia.lifeskills.views.loginscreen.LoginViewModel
import org.koin.android.viewmodel.experimental.builder.viewModel
import org.koin.dsl.module.module

/**
 * It is used to bind all ViewModel in viewModel angular
 */
val activityViewModel = module {
//    viewModel<RegisterViewModel>()
    viewModel<LoginViewModel>()


}
val viewModelModules = listOf(
    activityViewModel
)