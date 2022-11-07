package ru.rtulab.smartdormitory.presentation.ui.common.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ru.rtulab.smartdormitory.ui.theme.Accent
import ru.rtulab.smartdormitory.ui.theme.White

@Composable
fun Drawer(
    backgroundColor: Color = Accent,
    contentColor: Color = White,
    content: @Composable () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(backgroundColor),
        verticalArrangement = Arrangement.Center
    ) {
        // Header
        /*Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = R.drawable.logo.toString(),
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .padding(10.dp)
        )*/
        // Space between
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(5.dp)
        )
        content()

    }
}