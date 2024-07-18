package com.fjr619.calculatorhistory.ui.screens.home

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.android.annotation.KoinViewModel

data class HomeState(
    val currentExpression: TextFieldValue = TextFieldValue(text = ""),
    val result: String = ""
)

sealed interface HomeAction {
    data class OnButtonActionClick(val symbol: String): HomeAction
    data class UpdateTextFieldValue(val value: TextFieldValue): HomeAction
}

@KoinViewModel
class HomeViewModel: ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state.asStateFlow()

    fun onAction(action: HomeAction) {
        when(action) {
            is HomeAction.OnButtonActionClick -> {}
            is HomeAction.UpdateTextFieldValue -> {}
        }
    }
}