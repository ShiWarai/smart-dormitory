package ru.rtulab.smartdormitory.presentation.ui.dateTime

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.rtulab.smartdormitory.R

@Preview
@Composable
fun Clock(
    modifier: Modifier = Modifier,
    twoDig: Int,
    twoDigfun: (Int) -> Unit,
    twoDigM: Int,
    twoDigMfun: (Int) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    Modifier.clickable {
                        twoDigfun(((twoDig + 1) % 24))
                    }
                ) {
                    Image(painter = painterResource(R.drawable.up), contentDescription = "Up")
                }
                Row(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                ) {
                    BigTimeItem(
                        twoDigits = if (twoDig < 10) "0$twoDig" else twoDig.toString()
                    )
                }
                Row(
                    Modifier.clickable {
                        twoDigfun(((twoDig + 23) % 24))
                    }
                ) {
                    Image(painter = painterResource(R.drawable.down), contentDescription = "Up")
                }
            }
            Column(
                modifier = Modifier
                    .padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = ":",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,

                    )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    Modifier.clickable {
                        twoDigMfun(((twoDigM + 1) % 60))
                    }
                ) {
                    Image(painter = painterResource(R.drawable.up), contentDescription = "Up")
                }
                Row(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                ) {
                    BigTimeItem(
                        twoDigits = if (twoDigM < 10) "0$twoDigM" else twoDigM.toString()
                    )
                }
                Row(
                    Modifier.clickable {
                        twoDigMfun(((twoDigM + 59) % 60))
                    }
                ) {
                    Image(painter = painterResource(R.drawable.down), contentDescription = "Up")
                }
            }
        }
    }
}