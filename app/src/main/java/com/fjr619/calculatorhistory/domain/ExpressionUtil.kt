package com.fjr619.calculatorhistory.domain

interface ExpressionUtil {
    fun addParentheses(
        expression: String,
        cursorPosition: Int,
    ): String

    fun calculateExpression(expression: String): String
    fun removeDigit(expression: String, selectionStart: Int, selectionEnd: Int): String
    fun addActionValueToExpression(symbol: String, expression: String, positionToAdd: Int): String
}