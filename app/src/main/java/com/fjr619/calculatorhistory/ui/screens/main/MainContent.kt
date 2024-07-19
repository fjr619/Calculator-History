package com.fjr619.calculatorhistory.ui.screens.main

import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.TextFieldValue
import androidx.constraintlayout.compose.MotionLayout
import androidx.constraintlayout.compose.MotionScene
import com.fjr619.calculatorhistory.R
import com.fjr619.calculatorhistory.ui.screens.components.ButtonGrid
import com.fjr619.calculatorhistory.ui.screens.components.ExpressionContent
import com.fjr619.calculatorhistory.ui.screens.history.HistoryAction
import com.fjr619.calculatorhistory.ui.screens.home.HomeAction
import com.fjr619.calculatorhistory.ui.screens.home.HomeState

@Composable
fun MainContent(
    modifier: Modifier,
    homeState: HomeState,
    fraction: Float,
    homeAction: (action: HomeAction) -> Unit,
    historyAction: (action: HistoryAction) -> Unit
) {
    val context = LocalContext.current

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
            )
            ButtonGrid(
                modifier = Modifier
                    .layoutId("button_grid")
                    .fillMaxWidth(),
                onActionClick = { symbol ->
                    if (symbol == "=" && homeState.currentExpression.text.isEmpty()) return@ButtonGrid
                    homeAction(HomeAction.OnButtonActionClick(symbol))

                    if (symbol == "=" && !(homeState.result == "" || homeState.result == "NaN")) {
                        historyAction(HistoryAction.Insert(
                            expression = homeState.currentExpression.text,
                            result = homeState.result
                        ))

                        Toast.makeText(context, "Saved in History ${homeState.result}", Toast.LENGTH_SHORT).show()
                    }
                }
            )

        }
    }
}