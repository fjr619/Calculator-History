package com.fjr619.calculatorhistory.ui.screens.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalTextInputService
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fjr619.calculatorhistory.ui.util.Constant

@Composable
fun ExpressionContent(
    modifier: Modifier = Modifier,
    currentExpression: TextFieldValue,
    fraction: Float,
    result: String,
    updateTextFieldValue: (value: TextFieldValue) -> Unit
) {
    val scrollState = rememberScrollState()

    val customTextSelectionColors = TextSelectionColors(
        handleColor = Color.Transparent,
        backgroundColor = Color.LightGray
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(Constant.EXPRESSION_RESULT_HEIGHT),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = modifier.padding(end = 24.dp),
            horizontalAlignment = Alignment.End
        ) {
            CompositionLocalProvider(
                LocalTextSelectionColors provides customTextSelectionColors,
                LocalTextInputService provides null //TODO
            ) {
                //todo change to state alias BasicTextField2 refer to Runtracker
                BasicTextField(
                    value = currentExpression,
                    onValueChange = updateTextFieldValue,
                    textStyle = TextStyle(
                        letterSpacing = 2.sp,
                        fontSize = 24.sp,
                        textAlign = TextAlign.End,
                        color = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier.horizontalScroll(
                        state = scrollState,
                        reverseScrolling = true,
                    ),
                    maxLines = 1,
                    cursorBrush = SolidColor(Color.Cyan),
                    readOnly = false
                )
            }

            AnimatedVisibility(visible = fraction < 0.8f) {
                Spacer(modifier = Modifier.height(16.dp))
            }

            Text(
                text = result,
                style = TextStyle(
                    fontSize = 28.sp,
                    textAlign = TextAlign.End,
                    color = Color.Cyan
                ),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

