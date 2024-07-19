package com.fjr619.calculatorhistory.data.repository

import com.fjr619.calculatorhistory.data.local.room.HistoryDao
import com.fjr619.calculatorhistory.data.local.room.HistoryEntity
import com.fjr619.calculatorhistory.data.mapper.toDomain
import com.fjr619.calculatorhistory.domain.model.History
import com.fjr619.calculatorhistory.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.core.annotation.Single

@Single
class HistoryRepositoryImpl(
    private val historyDao: HistoryDao
) : HistoryRepository {
    override fun getAllHistories(): Flow<List<History>> = historyDao.getAllHistories()
        .map { list ->
            list.map { entity ->
                entity.toDomain()
            }
        }

    override suspend fun deleteAllHistories() = historyDao.deleteAllHistory()

    override suspend fun insertHistory(expression: String, result: String) {
        val entity = HistoryEntity(
            expression = expression,
            result = result,
            epochDay = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.toEpochDays()
        )

        historyDao.insertHistory(entity)
    }
}