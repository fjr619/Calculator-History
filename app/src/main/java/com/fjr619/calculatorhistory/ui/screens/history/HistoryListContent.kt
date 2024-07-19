package com.fjr619.calculatorhistory.ui.screens.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fjr619.calculatorhistory.domain.model.Calculation
import com.fjr619.calculatorhistory.domain.model.History
import kotlinx.coroutines.launch

@Composable
fun HistoryListContent(
    historiesList: List<History>,
    showDeleteIcon: (Boolean) -> Unit,
    onItemClicked: (Calculation) -> Unit
) {
    if (historiesList.isEmpty()) {
        showDeleteIcon(false)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Nothing to show!",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.surface,
                textAlign = TextAlign.Center
            )
        }
    } else {
        showDeleteIcon(true)

        val historyItemByDate by remember(historiesList) {
            derivedStateOf {
                historiesList.groupBy(
                    keySelector = History::date,
                    valueTransform = History::calculation
                )
            }
        }

        val scope = rememberCoroutineScope()
        val scrollState = rememberLazyListState()

        LaunchedEffect(key1 = historyItemByDate) {
            scope.launch {
                scrollState.scrollToItem(0)
            }
        }


        LazyColumn(
            state = scrollState,
            contentPadding = PaddingValues(horizontal = 20.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp),
        ) {
            historyItemByDate.onEachIndexed { index, (date, datas) ->
                items(
                    items = datas,
                    key = { "$index$date${it.hashCode()}" }
                ) { data ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                            .clickable {
                                onItemClicked(data)
                            },
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(text = data.expression, style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 30.sp
                        ),color = MaterialTheme.colorScheme.surface,)
                        Text(text = data.result, style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 30.sp, fontWeight = FontWeight.Bold
                        ),color = MaterialTheme.colorScheme.primaryContainer,)
                    }
                }

                item(key = "$index$date") {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = date, style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.onPrimary,)
                    Divider(color = MaterialTheme.colorScheme.onSecondary, modifier = Modifier.padding(top = 16.dp), thickness = 2.dp)
                }
            }
        }
    }


}