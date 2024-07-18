package com.fjr619.calculatorhistory.domain

import org.koin.core.annotation.Single
import org.mariuszgromada.math.mxparser.Expression
import java.text.DecimalFormat

@Single
class ExpressionUtilImpl(): ExpressionUtil {
    override fun addParentheses(expression: String, cursorPosition: Int): String {
        val openParenthesesNum = expression.count { it == '(' }
        val closeParenthesesNum = expression.count { it == ')' }

        val digitNumberOrClosesParenthesesOrExpOrPi = expression
            .getOrNull(cursorPosition)?.digitToIntOrNull() != null
                || expression.getOrNull(cursorPosition) == ')'

        val newExpression = when {
            openParenthesesNum == (closeParenthesesNum + 1) && digitNumberOrClosesParenthesesOrExpOrPi -> ")"
            openParenthesesNum > closeParenthesesNum && digitNumberOrClosesParenthesesOrExpOrPi -> ")"
            else -> "("
        }

        return addItemToExpression(newExpression, expression, cursorPosition+1)
    }

    override fun calculateExpression(expression: String): String {
        return try {
            val result = Expression(expression).calculate()
            if (result.isNaN()) "" else DecimalFormat("#,###.##").format(result)
        } catch (e: Exception) {
            ""
        }
    }

    override fun removeDigit(expression: String, selectionStart: Int, selectionEnd: Int): String {
        if (expression.isBlank()) return ""

        val selectionRage = selectionStart until selectionEnd
        val removeRange = if (selectionStart == selectionEnd) {
            (selectionStart - 1) until selectionStart
        } else {
            selectionRage
        }

        return expression.removeRange(removeRange)
        
    }

    override fun addActionValueToExpression(symbol: String, expression: String, positionToAdd: Int): String {
        return addItemToExpression(symbol, expression, positionToAdd)
    }

    private fun addItemToExpression(
        item: String,
        expression: String,
        positionToAdd: Int
    ): String {
        return expression.replaceRange(positionToAdd, positionToAdd, item)
    }
}