package com.careindia.lifeskills.di


import com.careindia.lifeskills.views.IMProfile.IMProfileViewModel
import org.koin.android.viewmodel.experimental.builder.viewModel
import org.koin.dsl.module.module

/**
 * It is used to bind all ViewModel in viewModel angular
 */
val activityViewModel = module {
//    viewModel<RegisterViewModel>()
    viewModel<IMProfileViewModel>()

}
val viewModelModules = listOf(
    activityViewModel
)