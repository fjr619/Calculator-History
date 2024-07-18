package com.fjr619.calculatorhistory.ui.screens.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SheetContent(
    isExpanded: Boolean,
    onBack: () -> Unit
) {
    BackHandler(isExpanded) {
        onBack()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(BottomSheetScaffoldDefaults.SheetPeekHeight),
        ) {
            Box(modifier = Modifier
                .clip(RoundedCornerShape(4.dp))
                .fillMaxWidth(0.1f)
                .height(4.dp)
                .background(Color.Gray))
        }

        LazyColumn(
            modifier = Modifier
        ) {

        }
    }
}