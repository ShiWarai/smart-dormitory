package ru.rtulab.smartdormitory.presentation.ui.notafication

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun NotaficationItem(){
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f, false)) {
                Text(
                    text = "Notafication Name",
                    fontSize = 16.sp
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.5f, false),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "time",
                    fontSize = 14.sp
                )
            }
        }
        Row(
            modifier = Modifier.padding(top = 4.dp)
        ) {
            Text(
                text = "Description",
                fontSize = 14.sp
            )
        }
        Divider (
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}