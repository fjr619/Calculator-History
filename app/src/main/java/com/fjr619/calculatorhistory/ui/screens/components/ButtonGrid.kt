package com.fjr619.calculatorhistory.ui.screens.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ButtonGrid(
    modifier: Modifier = Modifier,
    onActionClick: (symbol: String) -> Unit
) {
    val hapticFeedback = LocalHapticFeedback.current

    val buttons = listOf(
        listOf("AC", "⌫", "( )", "÷"),
        listOf("7", "8", "9", "×"),
        listOf("4", "5", "6", "-"),
        listOf("1", "2", "3", "+"),
        listOf("%", "0", ".", "="),
    )

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        buttons.forEach { rowButtons ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                rowButtons.forEach {
                    ButtonComponent(
                        symbol = it,
                        modifier = Modifier
                            .aspectRatio(1f)
                            .weight(1f)
                    ) { symbol ->
                        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
                        onActionClick(symbol)
                    }
                }
            }

        }
    }
}

@Composable
private fun ButtonComponent(
    symbol: String,
    modifier: Modifier = Modifier,
    onClick: (symbol: String) -> Unit
) {
    FilledIconButton(modifier = modifier, onClick = {onClick(symbol) }, colors = IconButtonDefaults.iconButtonColors(
        containerColor = buttonBackgroundColor(symbol)
    )) {
        Text(
            text = symbol,
            fontSize = buttonFontSize(symbol),
            color = MaterialTheme.colorScheme.surface
        )
    }
}

@Composable
private fun buttonBackgroundColor(symbol: String): Color {
    return when (symbol) {
        "AC" -> MaterialTheme.colorScheme.error
        "⌫" -> MaterialTheme.colorScheme.secondary
        "=" -> MaterialTheme.colorScheme.primary
        "( )", "+", "-", "×", "÷" -> MaterialTheme.colorScheme.tertiary
        else -> MaterialTheme.colorScheme.onSurface
    }
}

private fun buttonFontSize(symbol: String): TextUnit {
    return when (symbol) {
        "+", "-", "×", "÷", "=" -> 38.sp
        else -> 24.sp
    }
}