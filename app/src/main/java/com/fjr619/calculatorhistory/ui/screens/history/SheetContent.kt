package com.fjr619.calculatorhistory.ui.screens.history

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffoldDefaults
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteSweep
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fjr619.calculatorhistory.domain.model.Calculation
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SheetContent(
    historyState: HistoryState,
    isExpanded: Boolean,
    onClearHistoryClicked: () -> Unit,
    onHistoryItemClicked: (Calculation) -> Unit,
    onBack: () -> Unit
) {


    val scope = rememberCoroutineScope()
    val clearHistoriesSheetState = rememberModalBottomSheetState()

    var showDeleteIcon by rememberSaveable {
        mutableStateOf(false)
    }

    var showClearHistoriesSheet by remember {
        mutableStateOf(false)
    }

    BackHandler(isExpanded) {
        if (clearHistoriesSheetState.isVisible) {
            scope.launch {
                clearHistoriesSheetState.hide()
            }
        } else {
            onBack()
        }

    }

    if (showClearHistoriesSheet) {
        ModalBottomSheet(
            sheetState = clearHistoriesSheetState,
            onDismissRequest = {
                showClearHistoriesSheet = false
            },
            content = {
                ClearHistorySheetContent(
                    onClearClicked = {
                        scope.launch {
                            clearHistoriesSheetState.hide()
                            showClearHistoriesSheet = false
                            onClearHistoryClicked()
                        }
                    },
                    onCancelClicked = {
                        scope.launch {
                            clearHistoriesSheetState.hide()
                            showClearHistoriesSheet = false
                        }
                    }
                )
            })
    }


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(BottomSheetScaffoldDefaults.SheetPeekHeight),
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .fillMaxWidth(0.1f)
                    .height(4.dp)
                    .background(Color.Gray)
            )
        }

        AnimatedVisibility(visible = showDeleteIcon) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp)
            ) {
                FilledIconButton(modifier = Modifier.size(60.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    onClick = {
                        showClearHistoriesSheet = true
                    }) {
                    Icon(
                        modifier = Modifier.fillMaxSize(0.8f),
                        imageVector = Icons.Outlined.DeleteSweep,
                        contentDescription = "",
                        tint = MaterialTheme.colorScheme.surface
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
            }
        }

        HistoryListContent(
            historiesList = historyState.historiesList,
            showDeleteIcon = { showDeleteIcon = it },
            onItemClicked = onHistoryItemClicked
        )

    }
}