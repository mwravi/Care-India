package com.careindia.lifeskills.di

import androidx.lifecycle.MutableLiveData
import com.careindia.lifeskills.model.BundleDataModel
import org.koin.dsl.module.module
import org.koin.experimental.builder.factory

/**
 * It is used for all Bundle data
 */
val mutableLiveDataString = module {
    factory<MutableLiveData<String>>()
    factory<String>()
 }

val bundleDataModel = module {
    factory<BundleDataModel>()
}


val variableModules = listOf(
    mutableLiveDataString,
    bundleDataModel
 )