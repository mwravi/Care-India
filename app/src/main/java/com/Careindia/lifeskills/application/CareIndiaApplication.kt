package com.careindia.lifeskills.application

import android.os.Build
import android.os.StrictMode
import androidx.multidex.MultiDexApplication
import androidx.room.Room
import com.careindia.lifeskills.database.AppDataBase

class CareIndiaApplication : MultiDexApplication() {

    private val DB_NAME = "CAREINDIA_DB.db"

    companion object {
        var database: AppDataBase? = null
        lateinit var myApplication: CareIndiaApplication

    }

    override fun onCreate() {
        super.onCreate()
        myApplication = this

//        database = AppDataBase.getDatabase(myApplication)
        database =
            Room.databaseBuilder(applicationContext, AppDataBase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries().build()

        if (Build.VERSION.SDK_INT >= 24) {
            val builder = StrictMode.VmPolicy.Builder()
            StrictMode.setVmPolicy(builder.build())
        }
        if (Build.VERSION.SDK_INT > 9) {
            val policy =
                StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
        }
    }

    fun getDataBaseObj(): AppDataBase? {
        return database
    }



}