package com.example.bremonthrestaurant.menuData

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MenuData::class], version = 1, exportSchema = false)
abstract class MenuDatabase:RoomDatabase() {
    abstract fun dao(): MenuDao

    companion object {
        @Volatile
        private var INSTANCE: MenuDatabase? = null
        fun getDatabase(context: Context): MenuDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MenuDatabase::class.java,
                    "dataMenu"
                ).allowMainThreadQueries().fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}