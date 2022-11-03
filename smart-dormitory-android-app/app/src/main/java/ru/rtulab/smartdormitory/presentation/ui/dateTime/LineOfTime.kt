package ru.rtulab.smartdormitory.presentation.ui.dateTime

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.rtulab.smartdormitory.ui.theme.Green
import ru.rtulab.smartdormitory.ui.theme.Red

@Preview
@Composable
fun LineOfTime(
    starttime:String = "0",
    endtime:String = "1",
    colorFree:Color = Green,
    colorBusy:Color = Red,
    arrayBusyness: List<TimeAndStatus> = mutableListOf(TimeAndStatus(false,0f to 0.3f),TimeAndStatus(true,0.3f to 0.6f),TimeAndStatus(false,0.6f to 0.9f),TimeAndStatus(true,0.9f to 1f))
){
    val density = LocalDensity.current
    val linearIndicatorHeight = 12.dp
    var linearWidth = 240.0f
    Card(
        shape = RoundedCornerShape(8.dp)
    ) {
        Canvas(
            modifier = Modifier
                .height(linearIndicatorHeight)
                .fillMaxWidth()
                .onSizeChanged {
                   linearWidth = it.width.toFloat()
                }

        ) {
            val y = with(density) {linearIndicatorHeight.toPx()/2}
            val x =  linearWidth

            for(item in arrayBusyness) {
                    if (item.active)
                    drawLine(
                        color = Green,
                        start = Offset(item.startToEnd.first * x, y),
                        end = Offset(item.startToEnd.second * x, y),
                        strokeWidth = with(density) {linearIndicatorHeight.toPx()}
                    )
                else
                    drawLine(
                        color = Red,
                        start = Offset(item.startToEnd.first * x, y),
                        end = Offset(item.startToEnd.second * x, y),
                        strokeWidth = with(density) {linearIndicatorHeight.toPx()}
                    )
            }

        }
    }

}
class TimeAndStatus(
    public val active:Boolean = false,
    public val startToEnd: Pair<Float,Float> = (0f to 1f)
){}
