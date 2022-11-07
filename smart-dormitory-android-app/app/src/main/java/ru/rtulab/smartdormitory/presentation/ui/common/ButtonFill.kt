package ru.rtulab.smartdormitory.presentation.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.rtulab.smartdormitory.ui.theme.Accent
import ru.rtulab.smartdormitory.ui.theme.White

@OptIn(ExperimentalMaterialApi::class)
@Preview
@Composable
fun ButtonFill(
    modifier: Modifier=Modifier,
    text:String ="Button",
    colorFill:Color = MaterialTheme.colors.primary,
    onClick:() -> Unit = {},

    ){

    Card(
        backgroundColor = colorFill,
        modifier = modifier.fillMaxWidth(),
        onClick =  onClick ,
        shape = RoundedCornerShape(8.dp),
        elevation = 0.dp
    )
    {
        Row(
            horizontalArrangement = Arrangement.Center
        ) {

            Text(
                modifier = Modifier
                    .padding(0.dp, 12.dp),
                text = text,
                color = if(colorFill== Accent) White else Accent,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
        }
    }
}