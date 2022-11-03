package ru.rtulab.smartdormitory.presentation.ui.dateTime

import android.graphics.Paint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.rtulab.smartdormitory.ui.theme.Green
import ru.rtulab.smartdormitory.ui.theme.Red
import ru.rtulab.smartdormitory.ui.theme.White
import ru.rtulab.smartdormitory.ui.theme.White50

@Preview
@Composable
fun LineOfTimeWithSelect(
    starttime:String = "0",
    endtime:String = "1",
    arrayBusyness: List<TimeAndStatus> = mutableListOf(TimeAndStatus(false,0f to 0.3f),TimeAndStatus(true,0.3f to 0.6f),TimeAndStatus(false,0.6f to 0.9f),TimeAndStatus(true,0.9f to 1f)),
    startSelect:Float = 0.5f,
    endSelect:Float = 0.6f,
    colorSelect: Color = Color.Red,
    ){
    val density = LocalDensity.current
    val linearIndicatorHeight = 14.dp
    var linearWidth = 240.0f
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = Color.Transparent,
        elevation = 0.dp
    ) {
        Canvas(
            modifier = Modifier
                .height(linearIndicatorHeight)
                .fillMaxWidth()
                .onSizeChanged {
                    linearWidth = it.width.toFloat()
                }
                .background(Color.Transparent),


        ) {
            val y = with(density) {(linearIndicatorHeight).toPx()/2}
            val x =  linearWidth

            for(item in arrayBusyness) {
                if (item.active)
                    drawLine(
                        color = Color.Blue,
                        start = Offset(item.startToEnd.first * x, y),
                        end = Offset(item.startToEnd.second * x, y),
                        strokeWidth = with(density) {(linearIndicatorHeight-2.dp).toPx()}
                    )
                else
                    drawLine(
                        color = White,
                        start = Offset(item.startToEnd.first * x, y+with(density) { 1.dp.toPx()}),
                        end = Offset(item.startToEnd.second * x, y+with(density) { 1.dp.toPx()}),
                        strokeWidth = with(density) {(linearIndicatorHeight-2.dp).toPx()}
                    )
            }
            var stroke = Stroke(width = with(density) {3.dp.toPx()})

            drawRect(
                color = colorSelect,
                topLeft = Offset(startSelect * x ,with(density) { 0.dp.toPx()}),
                style = stroke,
                size = Size((endSelect-startSelect) * x,with(density) {(linearIndicatorHeight).toPx()})
            )

        }
    }
}

