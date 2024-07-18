package com.fjr619.calculatorhistory.ui.screens.components

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.fjr619.calculatorhistory.R
import com.fjr619.calculatorhistory.ui.screens.home.HomeAction
import com.fjr619.calculatorhistory.ui.screens.home.HomeState
import kotlinx.coroutines.delay

@Composable
fun MainContent(
    modifier: Modifier,
    homeState: HomeState,
    fraction: Float,
    onAction: (action: HomeAction) -> Unit
) {
    val context = LocalContext.current

    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            focusManager.clearFocus()
        }
    }

    val motionScene = remember {
        context.resources
            .openRawResource(R.raw.motion_scene)
            .readBytes()
            .decodeToString()
    }

    Row(
        modifier = modifier
            .fillMaxSize()

    ) {
        MotionLayout(
            motionScene = MotionScene(content = motionScene),
            progress = fraction,
            modifier = Modifier.fillMaxWidth()
        ) {
            ExpressionContent(
                modifier = Modifier
                    .layoutId("expression_result"),
                fraction = fraction,
                currentExpression = homeState.currentExpression,
                result = homeState.result,
                updateTextFieldValue = { value: TextFieldValue ->
                    onAction(HomeAction.UpdateTextFieldValue(value))
                },
            )
            ButtonGrid(
                modifier = Modifier
                    .layoutId("button_grid")
                    .fillMaxWidth(),
                onActionClick = { symbol ->
                    if (symbol == "=" && homeState.currentExpression.text.isEmpty()) return@ButtonGrid
                    onAction(HomeAction.OnButtonActionClick(symbol))

                    if (symbol == "=" && !(homeState.result == "" || homeState.result == "NaN")) {
                        Toast.makeText(context, "Saved in History", Toast.LENGTH_SHORT).show()
                    }
                }
            )

        }
    }
}