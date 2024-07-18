package com.fjr619.calculatorhistory.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fjr619.calculatorhistory.ui.screens.components.MainContent
import com.fjr619.calculatorhistory.ui.screens.components.SheetContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier) {

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(

        )
    )

    BottomSheet(
        scaffoldState = scaffoldState,
        content = {
            MainContent()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    modifier: Modifier = Modifier,
    scaffoldState: BottomSheetScaffoldState,
    content: @Composable () -> Unit,
) {
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        modifier = Modifier
            .systemBarsPadding()
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        sheetContainerColor = MaterialTheme.colorScheme.onBackground,
        sheetDragHandle = {
            BottomSheetDefaults.DragHandle(
                color = MaterialTheme.colorScheme.background
            )
        },
        sheetContent = {
            Box(
                modifier = Modifier
//                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .fillMaxWidth()
                    .fillMaxHeight(0.76f)
            ) {
                SheetContent(
                    isExpanded = scaffoldState.bottomSheetState.isVisible,
                    onBack = {
                        scope.launch {
                            scaffoldState.bottomSheetState.hide()
                        }
                    }
                )
            }
        },
    ) {
        content.invoke()
    }
}
