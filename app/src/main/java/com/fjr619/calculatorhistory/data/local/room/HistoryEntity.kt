package com.fjr619.calculatorhistory.data.local.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HistoryEntity(
    val expression: String,
    val result: String,
    val epochDay: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
)