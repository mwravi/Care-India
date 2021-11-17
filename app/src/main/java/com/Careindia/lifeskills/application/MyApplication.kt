package com.careindia.lifeskills.application

import android.app.Application
import com.careindia.lifeskills.di.dataModules
import com.careindia.lifeskills.di.repositoryModules
import com.careindia.lifeskills.di.variableModules
import com.careindia.lifeskills.di.viewModelModules


import org.koin.android.ext.android.startKoin

/*This is Application*/
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        myApplication = this
        // get all modules
        val moduleList =
            viewModelModules + dataModules + repositoryModules + variableModules
        // set the module list
        startKoin(this, moduleList)

    }

    companion object {
        lateinit var myApplication: MyApplication
    }
}