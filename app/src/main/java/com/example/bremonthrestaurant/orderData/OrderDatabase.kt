package com.example.bremonthrestaurant.orderData

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Order::class], version = 1, exportSchema = false)
abstract class OrderDatabase:RoomDatabase() {
    abstract fun dao(): OrderDao

    companion object {
        @Volatile
        private var INSTANCE: OrderDatabase? = null
        fun getDatabase(context: Context): OrderDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OrderDatabase::class.java,
                    "Orders"
                ).allowMainThreadQueries().fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}