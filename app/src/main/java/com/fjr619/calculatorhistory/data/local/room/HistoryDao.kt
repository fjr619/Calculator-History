package com.fjr619.calculatorhistory.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {

    @Query("SELECT * FROM HistoryEntity ORDER BY id DESC")
    fun getAllHistories(): Flow<List<HistoryEntity>>

    @Insert
    suspend fun insertHistory(historyEntity: HistoryEntity)

    @Query("DELETE FROM HistoryEntity")
    suspend fun deleteAllHistory()
}