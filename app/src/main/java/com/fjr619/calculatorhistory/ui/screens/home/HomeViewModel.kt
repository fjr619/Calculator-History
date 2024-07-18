package com.fjr619.calculatorhistory.ui.screens.home

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fjr619.calculatorhistory.domain.ExpressionUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

data class HomeState(
    val currentExpression: TextFieldValue = TextFieldValue(text = ""),
    val result: String = ""
)

sealed interface HomeAction {
    data class OnButtonActionClick(val symbol: String) : HomeAction
    data class UpdateTextFieldValue(val value: TextFieldValue) : HomeAction
}

@KoinViewModel
class HomeViewModel(
    private val expressionUtil: ExpressionUtil
) : ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnButtonActionClick -> processExpression(action.symbol)
            is HomeAction.UpdateTextFieldValue -> updateTextFieldValue(action.value)
        }
    }

    private fun updateTextFieldValue(value: TextFieldValue) {
        val result = expressionUtil.calculateExpression(
            getConvertedExpression(value.text)
        )
        updateState(value, result)
    }

    private fun processExpression(symbol: String) {
        viewModelScope.launch {
            when (symbol) {
                "=" -> addResultToExpression()
                "AC" -> clearExpression()
                "( )" -> addParentheses()
                "⌫" -> removeDigit()
                else -> addActionValueToExpression(symbol)
            }
        }
    }



    private fun addParentheses() {
        val currentExpression = state.value.currentExpression
        val newCursorPosition = currentExpression.selection.start + 1

        val newExpression = expressionUtil.addParentheses(
            expression = currentExpression.text,
            cursorPosition = currentExpression.selection.start - 1,
        )
        calculateExpression(
            currentExpression.copy(
                text = newExpression,
                selection = TextRange(newCursorPosition, newCursorPosition)
            )
        )
    }

    private fun removeDigit() {
        val currentExpression = state.value.currentExpression
        val newExpression = expressionUtil.removeDigit(currentExpression.text, currentExpression.selection.start, currentExpression.selection.end)
        val newCursorExpression = currentExpression.selection.start - 1

        calculateExpression(
            currentExpression.copy(
                text = newExpression,
                selection = if (newCursorExpression < 0) TextRange.Zero
                            else TextRange(newCursorExpression, newCursorExpression)
            )
        )
    }

    private fun TextRange.toIntRange() = (this.start until this.end)

    private fun addResultToExpression() {
        val currentState = state.value
        val result = if (currentState.result == "") "NaN" else currentState.result
        updateState(currentExpression = currentState.currentExpression, result)
    }

    private fun addActionValueToExpression(symbol: String) {
        println("addActionValueToExpression")
        val currentExpression = state.value.currentExpression
        val newCursorPosition = currentExpression.selection.start + symbol.length
        val newExpression = expressionUtil.addActionValueToExpression(
            symbol,
            currentExpression.text,
            currentExpression.selection.start
        )

        calculateExpression(
            currentExpression.copy(
                text = newExpression,
                selection = TextRange(newCursorPosition, newCursorPosition)
            )
        )
    }

    private fun calculateExpression(expression: TextFieldValue) {
        println("calculateExpression")
        val result = expressionUtil.calculateExpression(
            getConvertedExpression(expression.text)
        )
        updateState(expression, result)
    }

    private fun getConvertedExpression(expression: String): String {
        return expression.replace(Regex("÷"), "/")
            .replace(Regex("×"), "*")
    }

    private fun clearExpression() {
        updateState(
            currentExpression = TextFieldValue(""),
            result = ""
        )
    }

    private fun updateState(
        currentExpression: TextFieldValue,
        result: String
    ) {
        println("updateState")
        _state.update {
            it.copy(
                currentExpression = currentExpression,
                result = result
            )
        }
    }
}