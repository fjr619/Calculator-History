package com.fjr619.calculatorhistory.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.concurrent.Volatile

@Database(entities = [HistoryEntity::class], version = 1, exportSchema = true)
abstract class HistoryDB : RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile // For Singleton instantiation
        private var instance: HistoryDB? = null

        fun getInstance(context: Context): HistoryDB {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context): HistoryDB {
            return Room.databaseBuilder(
                context,
                HistoryDB::class.java,
                "calculator_history_db"
            ).allowMainThreadQueries()
                .build()
        }
    }
}