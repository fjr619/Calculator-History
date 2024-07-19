package com.fjr619.calculatorhistory.domain.repository

import com.fjr619.calculatorhistory.domain.model.History
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun getAllHistories(): Flow<List<History>>

    suspend fun deleteAllHistories()

    suspend fun insertHistory(expression: String, result: String)
}