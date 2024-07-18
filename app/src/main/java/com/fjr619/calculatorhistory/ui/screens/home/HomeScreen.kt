package com.fjr619.calculatorhistory.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.fjr619.calculatorhistory.ui.screens.components.MainContent
import com.fjr619.calculatorhistory.ui.screens.components.SheetContent
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    val viewModel: HomeViewModel = koinViewModel()
    val homeState by viewModel.state.collectAsStateWithLifecycle()

    val scaffoldState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )

    BottomSheet(
        scaffoldState = scaffoldState,
        content = {
            MainContent(
                homeState = homeState,
                onAction = viewModel::onAction
            )
        }
    )

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(
    modifier: Modifier = Modifier,
    scaffoldState: BottomSheetScaffoldState,
    content: @Composable () -> Unit,
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
                    isExpanded = scaffoldState.bottomSheetState.isExpanded,
                    onBack = {
                        scope.launch {
                            scaffoldState.bottomSheetState.collapse()
                        }
                    }
                )
            }
        },
    ) {
        content.invoke()
    }
}
