package com.fjr619.calculatorhistory.domain.model

data class History(
    val id: Int,
    val date: String,
    val calculation: Calculation
)

data class Calculation(
    val expression: String = "",
    val result: String = "0"
)