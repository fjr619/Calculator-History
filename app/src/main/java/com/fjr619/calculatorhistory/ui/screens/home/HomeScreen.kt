package com.fjr619.calculatorhistory.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fjr619.calculatorhistory.core.extension.currentFraction
import com.fjr619.calculatorhistory.ui.screens.history.HistoryAction
import com.fjr619.calculatorhistory.ui.screens.history.HistoryState
import com.fjr619.calculatorhistory.ui.screens.history.HistoryViewModel
import com.fjr619.calculatorhistory.ui.screens.main.MainContent
import com.fjr619.calculatorhistory.ui.screens.history.SheetContent
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    val viewModel: HomeViewModel = koinViewModel()
    val historyViewModel: HistoryViewModel = koinViewModel()
    val homeState by viewModel.state.collectAsStateWithLifecycle()
    val historyState by historyViewModel.state.collectAsStateWithLifecycle()

    val scaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )

    BottomSheet(
        scaffoldState = scaffoldState,
        historyState = historyState,
        content = {
            MainContent(
                modifier = Modifier,
                fraction = scaffoldState.currentFraction,
                homeState = homeState,
                homeAction = viewModel::onAction,
                historyAction = historyViewModel::onAction
            )
        },
        onHistoryAction = historyViewModel::onAction,
        homeAction = viewModel::onAction,
    )

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(
    modifier: Modifier = Modifier,
    scaffoldState: BottomSheetScaffoldState,
    historyState: HistoryState,
    homeAction: (HomeAction) -> Unit,
    onHistoryAction: (HistoryAction) -> Unit,
    content: @Composable (PaddingValues) -> Unit,
) {
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        modifier = modifier
            .systemBarsPadding()
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        sheetBackgroundColor = MaterialTheme.colorScheme.background,
        backgroundColor = MaterialTheme.colorScheme.background,
        sheetContent = {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .fillMaxWidth()
                    .fillMaxHeight(0.76f)
                    .background(MaterialTheme.colorScheme.onBackground)
            ) {
                SheetContent(
                    historyState = historyState,
                    isExpanded = scaffoldState.bottomSheetState.isExpanded,
                    onClearHistoryClicked = {
                        onHistoryAction(HistoryAction.DeleteAll)
                    },
                    onBack = {
                        scope.launch {
                            scaffoldState.bottomSheetState.collapse()
                        }
                    },
                    onHistoryItemClicked = { calculation ->
                        homeAction(HomeAction.UpdateTextFieldValue(calculation.expression))
                    }
                )
            }
        },
    ) { paddingValues ->
        content(paddingValues)
    }
}
