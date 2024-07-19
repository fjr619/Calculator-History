package com.fjr619.calculatorhistory.ui.screens.history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.fjr619.calculatorhistory.ui.theme.CalculatorHistoryTheme

@Composable
fun ClearHistorySheetContent(
    onCancelClicked: () -> Unit,
    onClearClicked: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Clear", style = MaterialTheme.typography.titleLarge)
        Text(text = "Clear all histories now", style = MaterialTheme.typography.titleMedium)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            TextButton(onClick = onCancelClicked) {
                Text(text = "Cancel", color = MaterialTheme.colorScheme.error)
            }

            TextButton(onClick = onClearClicked ) {
                Text(text = "Clear", color = MaterialTheme.colorScheme.primary)
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Preview
@Composable
private fun ClearHistorySheetContentPreview() {
    Surface {
        ClearHistorySheetContent({},{})
    }

}