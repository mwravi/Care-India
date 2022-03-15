package com.careindia.lifeskills.application

import android.os.Build
import android.os.StrictMode
import androidx.multidex.MultiDexApplication
import androidx.room.Room
import com.careindia.lifeskills.database.AppDataBase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SQLiteDatabaseHook
import net.sqlcipher.database.SupportFactory

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
      /*  database =
            Room.databaseBuilder(applicationContext, AppDataBase::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries().build()*/
        val builder = Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java, DB_NAME).fallbackToDestructiveMigrationFrom().allowMainThreadQueries()
        val passphrase: ByteArray = SQLiteDatabase.getBytes("1111".toCharArray())

        val factory = SupportFactory(passphrase, object : SQLiteDatabaseHook {
            override fun preKey(database: SQLiteDatabase?) = Unit

            override fun postKey(database: SQLiteDatabase?) {
                database?.rawExecSQL(
                    "PRAGMA cipher_memory_security = ON"
                )

            }
        })

        builder.openHelperFactory(factory)
        database = builder.build()

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