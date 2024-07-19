package com.fjr619.calculatorhistory.ui.screens.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fjr619.calculatorhistory.domain.model.History
import com.fjr619.calculatorhistory.domain.repository.HistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel

data class HistoryState(
    val historiesList: List<History> = emptyList()
)

sealed interface HistoryAction {
    data object DeleteAll: HistoryAction
    data class Insert(val expression: String, val result: String): HistoryAction
}

@KoinViewModel
class HistoryViewModel(
    private val repository: HistoryRepository
): ViewModel()
{
    private val _state = MutableStateFlow(HistoryState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllHistories().collect { list ->
                _state.update {
                    it.copy(
                        historiesList = list
                    )
                }
            }
        }
    }

    fun onAction(action: HistoryAction) {
        when(action) {
            is HistoryAction.DeleteAll -> deleteHistories()
            is HistoryAction.Insert -> insertHistory(action.expression, action.result)
        }
    }

    private fun insertHistory(expression: String, resul: String) {
        viewModelScope.launch {
            repository.insertHistory(expression, resul)
        }
    }

    private fun deleteHistories() {
        viewModelScope.launch {
            repository.deleteAllHistories()
        }
    }
}