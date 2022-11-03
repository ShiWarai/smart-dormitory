package ru.rtulab.smartdormitory.presentation.ui.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.rtulab.smartdormitory.R
import ru.rtulab.smartdormitory.ui.theme.White

@Preview
@Composable
fun H1(
    modifier: Modifier = Modifier,
    text:String = "H1",

){
    Text(
        modifier = modifier,
        text = text,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        fontWeight = FontWeight.Bold,
        color = White
    )
}