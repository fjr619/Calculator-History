package com.fjr619.calculatorhistory.data.mapper

import com.fjr619.calculatorhistory.data.local.room.HistoryEntity
import com.fjr619.calculatorhistory.domain.model.Calculation
import com.fjr619.calculatorhistory.domain.model.History
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime


fun HistoryEntity.toDomain() = History(
    id = id,
    date = LocalDate.fromEpochDays(epochDay).format(),
    calculation = Calculation(
        expression = expression,
        result = result
    ),
)

private fun getToday(): Int =
    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.toEpochDays()

@OptIn(FormatStringsInDatetimeFormats::class)
private fun LocalDate.format(pattern: String = "MMM d"): String {
    return when {
        this.toEpochDays() == getToday() -> "Today"
        (getToday() - this.toEpochDays()) == 1 -> "Yesterday"
        else -> {
            val formatter = LocalDate.Format {
                byUnicodePattern(pattern)
            }
            formatter.format(this)
        }
    }
}