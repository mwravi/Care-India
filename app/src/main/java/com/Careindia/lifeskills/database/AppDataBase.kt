package com.careindia.lifeskills.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.careindia.lifeskills.dao.MstCommonDao
import com.careindia.lifeskills.entity.MstCommonEntity

@Database(
    entities = [MstCommonEntity::class],
    version = 1
)

abstract class AppDataBase : RoomDatabase() {
    abstract fun mstCommonDao(): MstCommonDao
}

